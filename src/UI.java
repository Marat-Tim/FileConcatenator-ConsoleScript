import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public final class UI {
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Показывает пользователю интерфейс приложения в консоли.
     */
    static void start() {
        String path = inputDirectoryPath();
        Concatenator concatenator = new Concatenator(path);
        try {
            List<File> sortedFileList = concatenator.getSortedFileList();
            for (int i = 1; i <= sortedFileList.size(); i++) {
                System.out.printf("%d. %s\n", i, sortedFileList.get(i - 1));
            }
            System.out.println();
            System.out.println(concatenator.concatenate());
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлами!\n" + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (RequireException e) {
            System.out.println("Ошибка в зависимостях!\n" + e.getLocalizedMessage());
        }
    }

    /**
     * Получает от пользователя путь к папке.
     *
     * @return Путь к папке.
     */
    private static String inputDirectoryPath() {
        Path path;
        System.out.print("Введите путь к папке:\n> ");
        path = Path.of(SCANNER.nextLine());
        while (!Files.exists(path) || !Files.isDirectory(path)) {
            System.out.println("Вы ввели неправильный путь!");
            System.out.print("Введите путь к папке:\n> ");
            path = Path.of(SCANNER.nextLine());
        }
        return path.toString();
    }
}
