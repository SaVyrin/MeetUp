Получает сервер:
* LOG_IN(Клиент) - запрос клиента: залогинить клиента
* LOG_OUT(Клиент) - запрос клиента: разлогинить клиента
* FRIEND_REQ(Клиент) - запрос клиента: познакомить клиента
* SHOW(Клиент) - запрос клиента: получить человека для знакомства

Получает клиент:
* LOG_IN(Сервер) - команда для клиента: залогиненный пользователь
* ONLINE(Сервер) - команда для клиента: список текущего онлайн
* FRIEND_REQ(Сервер) - команда для клиента: список знакомств
* FRIENDS(Сервер) - команда для клиента: список друзей
* SHOW(Сервер) - запрос клиента: человек для знакомства
