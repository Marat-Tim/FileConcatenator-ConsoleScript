# FileConcatenator-ConsoleScript

Этот скрипт принимает на вход путь к папке в которой могут находиться другие папки и файлы.
В файлах могут быть использованы особые команды: ```require ‘путь к другому файлу, относительно начальной папки’```.
Это означает, что файл зависит от другого файла.
Программа конкатенирует все файлы из данной папки и всех ее подпапок следующим образом:

1. Сначала строится список из путей к файлам, такой что если файл B зависит от файла A, то путь к файлу A находится в списке раньше чем путь к файлу B
2. Далее на экран выводится содержимое всех файлов в порядке в котором они находятся в списке

Если построить список нельзя(например, если существует циклическая зависимость или есть зависимость от несуществующего файла), то на экран будет выведено сообщение об ошибке

Заметки, которые я оставлял при написании данного проекта можно посмотреть в этом [файле](src/README.md)

P.S. после команды ```require``` должен идти путь к файлу относительно рабочей папки без указания рабочей папки, то есть если у нас есть папка ```Root``` в которой лежит папка ```Dir``` в которой лежит файл ```file.txt```, тогда для указания зависимости от файла ```file.txt``` нужно написать команду ```require ‘Dir/file.txt’```