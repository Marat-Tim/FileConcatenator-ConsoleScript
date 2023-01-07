/**
 * Ошибка в зависимостях.
 */
public class RequireException extends Exception {
    /**
     * @param message Сообщение.
     */
    public RequireException(String message) {
        super(message);
    }
}
