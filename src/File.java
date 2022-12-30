import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

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
    Set<String> getDependencies() throws IOException {
        var lines = Files.readAllLines(Path.of(path));
        Set<String> dependencies = new HashSet<>();
        for (var line : lines) {
            if (line.startsWith(Constants.REQUIRE_COMMAND)) {
                dependencies.add(
                        line.substring(Constants.REQUIRE_COMMAND.length() + 1, line.indexOf('\n')));
            }
        }
        return dependencies;
    }


}
