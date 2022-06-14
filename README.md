# Проект "Тестирование туристического приложения aqa-shop.jar (дебетовая карта)" 

Приложение aqa-shop.jar представляет собой веб-проект, позволяющий купить тур пользователю при помощи дебетовой или кредитной карты. 
В рамках задания необходимо было выбрать тестирование одной формы. В проекте тестируется форма на странице "Купить", предполагающая покупку по дебетовой карте.

**В данном проекте автоматизировано тестирование:** 
- формы на странице "Купить";
- подключённой базы данных (используется PostgreSQL); 

### Документация
К проекту прилагается документация, в которой описаны тестовые сценарии, а также issues и отчёт о тестировании. 
1. [План работы](https://github.com/taniakku/Autotest-module-project/blob/master/Plan.md).
2. [Issues проекта](https://github.com/taniakku/Autotest-module-project/issues).
3. [Отчёт о тестировании]().

### Как запустить проект 
1. Склонировать удалённый репозиторий на текущей странице на локальную машину. Это можно сделать при помощи консоли git командой `git clone <ссылка на репозиторий>`. Ссылку для клонирования можно найти на странице репозитория в выпадающем окне по кнопке **Code**. 
2. Удостовериться, что на локальном компьютере установлен Docker. Установить можно **[по ссылке](https://www.docker.com/)**. Эта программа необходима для развёртывания среды БД и функционирования самого приложения. 
3. Запускать Docker удобно в терминале IDEA, использовав команду `docker-compose up`. После удачного запуска в интерфейсе Docker отобразится зелёный статус.
4. После этого нужно запустить само приложение aqa-shop.jar при помощи команды `java -jar aqa-shop.jar`.

### Как запустить тесты
Запустить тесты необходимо в терминале IDEA командой `./gradlew clean test`.
Сгенерировать отчёт командой `./gradlew allureServe`. **Может не сгенерироваться из-за несовместимости с Gradle 8.0**.
