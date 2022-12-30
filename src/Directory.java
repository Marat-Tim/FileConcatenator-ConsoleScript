import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;

class Directory {
    private final String path;

    Directory(String path) {
        this.path = path;
    }

    /**
     * Возвращает все файлы из данной папки.
     *
     * @return Все файлы из данной папки.
     */
    Collection<File> getFiles() {
        List<File> files = new ArrayList<>();
        for (var
                file :
                Optional.ofNullable(new java.io.File(path).listFiles()).
                        orElse(new java.io.File[]{})) {
            if (!file.isDirectory()) {
                files.add(new File(file.getPath()));
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
        addAllFilesToList(files);
        return files;
    }

    /**
     * Добавляет в список все файлы из текущей папки и из всех ее подпапок.
     *
     * @param files Список, куда надо добавить файлы.
     */
    private void addAllFilesToList(Set<File> files) {
        files.addAll(getFiles());
        for (var directory : getDirectories()) {
            files.addAll(directory.getAllFiles());
        }
    }
}
