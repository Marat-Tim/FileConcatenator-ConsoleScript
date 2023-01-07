import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Этот класс выполняет 2 действия:
 * <br>1. Сортирует файлы в данной папке и во всех ее подпапках так,
 * что если файл B зависит от A, то файл B будет идти после файла A
 * <br>2. Конкатенирует содержимое отсортированного списка файлов.
 */
public class Concatenator {

    /**
     * Путь к рабочей папке.
     */
    private final String path;

    /**
     * @param path Путь к папке,
     */
    public Concatenator(String path) {
        this.path = path;
    }

    /**
     * Возвращает по данной папке строку, составленную из текстов файлов, отсортированных по правилу,
     * что если файл B зависит от файла A, то файл A стоит раньше файла B.
     *
     * @return Строка, составленная из текстов файлов, отсортированных по правилу, что если файл B
     * зависит от файла A, то файл A стоит раньше файла B.
     * @throws IOException      Если есть проблема в работе с файлами.
     * @throws RequireException Если есть проблема с зависимостями.
     */
    public String concatenate() throws IOException, RequireException {
        return concatenateTextOfFiles(getSortedFileList());
    }

    /**
     * Соединяет тексты всех файлов.
     *
     * @param files Файлы, содержимые которых нужно объединить.
     * @return Объединенное содержимое всех файлов.
     * @throws IOException Если есть проблемы при работе с каким-либо файлом.
     */
    private String concatenateTextOfFiles(List<File> files) throws IOException {
        StringBuilder text = new StringBuilder();
        for (var file : files) {
            text.append(file.getText());
        }
        return text.toString();
    }

    /**
     * Сортирует все файлы так, что если файл B зависит от файла A, то файл A будет раньше файла B.
     *
     * @return Лист с отсортированными файлами.
     * @throws IOException      Если есть проблема в работе с файлами.
     * @throws RequireException Если есть проблема с зависимостями.
     */
    public List<File> getSortedFileList() throws IOException, RequireException {
        Directory root = new Directory(path);
        Set<File> allFiles = root.getAllFiles();
        Set<File> processedFiles = new HashSet<>();
        List<File> files = new ArrayList<>();
        while (!allFiles.isEmpty()) {
            boolean didAdd = false;
            for (File file : allFiles) {
                if (processedFiles.containsAll(file.getDependencies())) {
                    processedFiles.add(file);
                    files.add(file);
                    allFiles.remove(file);
                    didAdd = true;
                    break;
                }
            }
            if (!didAdd) {
                throw new RequireException("Циклическая зависимость");
            }
        }
        return files;
    }
}
