package ru.job4j.pooh;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class TopicSchema implements Schema {
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<Receiver>> receivers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, BlockingQueue<String>> messages = new ConcurrentHashMap<>();
    private final Condition condition = new Condition();

    @Override
    public void addReceiver(Receiver receiver) {
        receivers.putIfAbsent(receiver.name(), new CopyOnWriteArrayList<>());
        receivers.get(receiver.name()).add(receiver);
        condition.on();
    }

    @Override
    public void publish(Message message) {
        messages.putIfAbsent(message.name(), new LinkedBlockingQueue<>());
        messages.get(message.name()).add(message.text());
        condition.on();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            for (String topic : messages.keySet()) {

                var msgQueue = messages.get(topic);
                if (msgQueue == null || msgQueue.isEmpty()) {
                    continue;
                }

                var receiversByTopic = receivers.get(topic);
                if (receiversByTopic == null) {
                    continue;
                }

                List<String> batch = new ArrayList<>();
                msgQueue.drainTo(batch);

                for (String message : batch) {
                    for (Receiver receiver : receiversByTopic) {
                        receiver.receive(message);
                    }
                }
            }
            condition.off();
            try {
                condition.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
