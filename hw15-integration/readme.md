### Реализовать обработку доменной сущности через каналы Spring Integration

#### Цель:
- Участники смогут осуществлять "интеграцию" частей приложения с помощью EIP
- Результат: приложение c применением EIP на Spring Integration

#### Описание/Пошаговая инструкция выполнения домашнего задания:
- Выберите другую доменную область и сущности. Пример: превращение гусеницы в бабочку.
- Опишите/сконфигурируйте процесс (IntegrationFlow) с помощью инструментария предоставляемого Spring Integration.
- Желательно использование MessagingGateway и subfolw (при необходимости).
- Задание выполняется в другом репозитории/в другой папке.
- Данное задание НЕ засчитывает предыдущие!

#### Описание приложения:
- Приложение имитирует работы фильтра для воды.
- Сначала происходит генерация водопроводной воды с примесями.
- Затем эта вода постепенно проходит через несколько ступеней очистки.
- Каждая ступень очистки очищает воду от конкретного загрязнения.
- На выходе мы получаем чистую питьевую воду