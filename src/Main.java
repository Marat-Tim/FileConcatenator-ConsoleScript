import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println(Files.exists(Path.of("src")));
    }
}