import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Обертка над путем к папке.
 */
class Directory {

    /**
     * Путь к папке
     */
    private final Path path;

    /**
     * @param path Путь к папке.
     */
    Directory(String path) {
        this.path = Path.of(path);
    }

    /**
     * Возвращает файлы из данной папки и всех ее подпапок.
     * Пути к файлам идут относительно данной папки.
     *
     * @return Файлы из данной папки и всех ее подпапок.
     */
    Set<File> getAllFiles() throws IOException {
        Set<Path> allPaths = Files.walk(path).
                filter(filePath -> !Files.isDirectory(filePath)).
                collect(Collectors.toSet());
        Set<File> allFiles = new HashSet<>();
        for (Path filePath : allPaths) {
            allFiles.add(new File(path.toString(), path.relativize(filePath).toString()));
        }
        return allFiles;
    }
}
