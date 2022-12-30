import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Concatenator {
    private final String path;

    public Concatenator(String path) {
        this.path = path;
    }

    /**
     * Возвращает по данной папке строку, составленную из текстов файлов, отсортированных по правилу,
     * что если файл B зависит от файла A, то файл A стоит раньше файла B.
     * @return Строка, составленная из текстов файлов, отсортированных по правилу, что если файл B
     * зависит от файла A, то файл A стоит раньше файла B.
     */
    public String concatenate() throws IOException, RequireException {
        return concatenateFiles(getSortedFileList());
    }

    private String concatenateFiles(List<File> files) throws IOException {
        StringBuilder text = new StringBuilder();
        for (var file : files) {
            text.append(file.getText());
        }
        return text.toString();
    }

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
