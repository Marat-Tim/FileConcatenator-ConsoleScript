import java.io.IOException;
import java.util.Scanner;

public class UI {
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Показывает пользователю интерфейс приложения в консоли.
     */
    static void start() {
        System.out.print("Введите путь к папке:\n> ");
        String path = SCANNER.nextLine();
        Concatenator concatenator = new Concatenator(path);
        try {
            System.out.println(concatenator.getSortedFileList());
            System.out.println(concatenator.concatenate());
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлами!\n" + e.getMessage());
        } catch (RequireException e) {
            System.out.println("Найдена циклическая зависимость!\n");
        }
    }
}
