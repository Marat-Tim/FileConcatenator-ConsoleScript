Здесь я буду писать какие решения я принимал в процессе написания кода

Я придумал следующий алгоритм:
1. Создаем множество A, состоящее из всех файлов в данной папке и во всех ее подпапках
2. Создаем другое пустое множество B, в котором будут храниться файлы, добавленные в список
3. Пока множество A не пустое:

    Ищем в нем файл, у которого все зависимости есть в множестве B. Удаляем его из множества A, добавляем в множество B и в конечный список

    Если в какой-то момент мы не смогли удалить файл из множества A - значит существует циклическая зависимость

В классах File и Directory я решил делать методы и поля не ```public```, а ```package``` потому что при подключении данного проекта как модуля разработчику вряд ли нужен будет доступ к моей реализации этих классов

Я понял, что неправильно обрабатывал пути к файлам. Судя по заданию в зависимостях должен указываться путь относительно рабочей папки, без указания самой рабочей папки

Задумался как взаимодействовать с путями к файлам/папкам, а именно стоит ли принимать и возвращать String, Path или File. В итоге решил использовать String, чтобы при передаче параметров не надо было писать ```Path.of(путь)```. Если это неправильный подход, то напишите, пожалуйста, как лучше

Переместил весь код, отвечающий непосредственно за решение задачи в отдельный модуль

Понял, что получить все файлы из папки и всех ее подпапок можно с помощью одной функции(теперь класс Directory стал намного меньше)