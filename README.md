**Брокер сообщений** — это отдельное приложение, которое пересылает сообщения между другими приложениями.

### Зачем они нужны?
Брокер сообщений позволяет убрать жесткую связь и стандартизировать механизмы общения.

### Клиенты брокера:

- поставщики сообщений (Producers)
- потребители сообщений (Consumers)

В большинстве случаев одно и то же приложение сразу осуществляет роль поставщика и потребителя.

> Отличительной чертой общения через брокер является _асинхронность_ событий, то есть сообщение отправляется без ожидания подтверждения его доставки потребителю.

### Пример из жизни:

Вы звоните другу по телефону и ждете его ответа. Это _синхронное_ действие. Вам и другу необходимо быть у аппарата в одно и то же время. Если у друга установлен автоответчик, то вы можете оставить сообщение и положить трубку. Друг придет и прослушает сообщение, когда у него будет возможность. Это _асинхронное_ действие. Вы и друг выполняете отправку и прием сообщения в разное время.