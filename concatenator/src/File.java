import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Обертка над путем к файлу.
 */
class File {

    /**
     * Путь к рабочей папке.
     */
    private final Path workingDirectory;

    /**
     * Путь к файлу относительно рабочей папки.
     */
    private final Path relativePath;

    /**
     * @param workingDirectory Рабочая папка.
     * @param relativePath     Путь к файлу относительно рабочей папки.
     */
    File(String workingDirectory, String relativePath) {
        this.workingDirectory = Path.of(workingDirectory);
        this.relativePath = Path.of(relativePath);
    }

    /**
     * Возвращает полный путь к файлу.
     *
     * @return Полный путь к файлу.
     */
    private Path getFullPath() {
        return new java.io.File(workingDirectory.toFile(), relativePath.toString()).toPath();
    }

    /**
     * Возвращает относительный путь к данному файлу.
     *
     * @return Относительный путь к данному файлу.
     */
    Path getRelativePath() {
        return relativePath;
    }

    /**
     * Возвращает весь текст из файла.
     *
     * @return Весь текст из файла.
     * @throws IOException Если есть проблема в работе с файлами.
     */
    String getText() throws IOException {
        return Files.readString(getFullPath());
    }

    /**
     * Возвращает все зависимости данного файла в виде множества.
     *
     * @return Все зависимости данного файла.
     * @throws IOException      Если есть проблема в работе с файлами.
     * @throws RequireException Если есть зависимость от несуществующего файла.
     */
    Set<File> getDependencies() throws IOException, RequireException {
        List<String> lines = Files.readAllLines(getFullPath());
        Set<File> dependencies = new HashSet<>();
        for (String line : lines) {
            if (line.startsWith(Constants.REQUIRE_COMMAND)) {
                String path = line.substring(
                        line.indexOf(Constants.REQUIRE_START_PATH_SYMBOL) + 1,
                        line.lastIndexOf(Constants.REQUIRE_END_PATH_SYMBOL));
                File dependency = new File(workingDirectory.toString(), path);
                if (!Files.exists(dependency.getFullPath())) {
                    throw new RequireException("Зависимость от несуществующего файла");
                }
                dependencies.add(dependency);
            }
        }
        return dependencies;
    }

    /**
     * Сравнивает путь относительно рабочей папки.
     *
     * @param other Объект с которым сравниваем.
     * @return Истина, если пути относительно рабочей папки равны, иначе - ложь.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        if (hashCode() != other.hashCode()) {
            return false;
        }
        return relativePath.equals(((File) other).relativePath);
    }

    /**
     * Возвращает хэш пути к файлу относительно рабочей папки.
     *
     * @return Хэш пути к файлу относительно рабочей папки.
     */
    @Override
    public int hashCode() {
        return relativePath.hashCode();
    }

    /**
     * Возвращает путь относительно рабочей папки.
     *
     * @return Путь относительно рабочей папки.
     */
    @Override
    public String toString() {
        return relativePath.toString();
    }
}
