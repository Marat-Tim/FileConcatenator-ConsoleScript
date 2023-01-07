import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class File {
    private Path path;

    private final Path relativePath;

    File(String path) {
        this.relativePath = Path.of(path);
    }

    File(String path, String relativeWhat) {
        this.path = Path.of(path);
        this.relativePath = Path.of(relativeWhat).relativize(this.path);
    }

    /**
     * Возвращает весь текст из файла.
     *
     * @return Весь текст из файла.
     */
    String getText() throws IOException {
        return Files.readString(path);
    }

    /**
     * Возвращает все зависимости данного файла в виде множества.
     *
     * @return Все зависимости данного файла.
     */
    Set<File> getDependencies() throws IOException {
        List<String> lines = Files.readAllLines(path);
        Set<File> dependencies = new HashSet<>();
        for (String line : lines) {
            if (line.startsWith(Constants.REQUIRE_COMMAND)) {
                String path = line.substring(
                        line.indexOf(Constants.REQUIRE_START_PATH_SYMBOL) + 1,
                        line.lastIndexOf(Constants.REQUIRE_END_PATH_SYMBOL));
                dependencies.add(new File(path));
            }
        }
        return dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (hashCode() != o.hashCode()) {
            return false;
        }
        return relativePath.equals(((File) o).relativePath);
    }

    @Override
    public int hashCode() {
        return relativePath.hashCode();
    }

    @Override
    public String toString() {
        return relativePath.toString();
    }
}
