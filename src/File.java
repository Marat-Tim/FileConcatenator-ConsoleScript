import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class File {
    private final String path;

    File(String path) {
        this.path = path;
    }

    /**
     * Возвращает весь текст из файла.
     *
     * @return Весь текст из файла.
     */
    String getText() throws IOException {
        return Files.readString(Path.of(path));
    }

    /**
     * Возвращает все зависимости данного файла в виде множества.
     *
     * @return Все зависимости данного файла.
     */
    Set<File> getDependencies() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));
        Set<File> dependencies = new HashSet<>();
        for (String line : lines) {
            if (line.startsWith(Constants.REQUIRE_COMMAND)) {
                dependencies.add(
                        new File(line.substring(line.indexOf('‘'), line.lastIndexOf('’'))));
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
        return Objects.equals(path, ((File) o).path);
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
