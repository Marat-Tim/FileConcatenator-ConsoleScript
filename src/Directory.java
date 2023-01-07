import java.nio.file.Path;
import java.util.*;

/**
 * Обертка над путем к папке.
 */
class Directory {

    /**
     * Путь к папке
     */
    private final String path;

    /**
     * @param path Путь к папке.
     */
    Directory(String path) {
        this.path = path;
    }

    /**
     * Возвращает все файлы из данной папки.
     *
     * @param relativeWhat Относительно чего хранить пути до файлов.
     * @return Все файлы из данной папки.
     */
    Collection<File> getFiles(String relativeWhat) {
        List<File> files = new ArrayList<>();
        for (var
                file :
                Optional.ofNullable(new java.io.File(path).listFiles()).
                        orElse(new java.io.File[]{})) {
            if (!file.isDirectory()) {
                files.add(new File(file.getPath(), relativeWhat));
            }
        }
        return files;
    }

    /**
     * Возвращает все папки, находящиеся в данной папке.
     *
     * @return Все папки, находящиеся в данной папке.
     */
    Collection<Directory> getDirectories() {
        List<Directory> directories = new ArrayList<>();
        for (var
                file :
                Optional.ofNullable(new java.io.File(path).listFiles()).
                        orElse(new java.io.File[]{})) {
            if (file.isDirectory()) {
                directories.add(new Directory(file.getPath()));
            }
        }
        return directories;
    }

    /**
     * Возвращает файлы из данной папки и всех ее подпапок.
     *
     * @return Файлы из данной папки и всех ее подпапок.
     */
    Set<File> getAllFiles() {
        Set<File> files = new HashSet<>();
        addAllFilesToList(files, path);
        return files;
    }

    /**
     * Добавляет в список все файлы из текущей папки и из всех ее подпапок.
     *
     * @param files        Список, куда надо добавить файлы.
     * @param relativeWhat Относительно чего хранить пути до файлов.
     */
    private void addAllFilesToList(Set<File> files, String relativeWhat) {
        files.addAll(getFiles(relativeWhat));
        for (var directory : getDirectories()) {
            directory.addAllFilesToList(files, relativeWhat);
        }
    }
}
