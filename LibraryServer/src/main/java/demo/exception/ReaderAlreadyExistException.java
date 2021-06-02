package demo.exception;

/**
 * Класс ошибки, когда читатель уже существует
 */
public class ReaderAlreadyExistException extends Exception {
    /**
     * инициаоизатор класса, использующий наследуемый метод
     * @param message сообщение, которое необходимо отобразить в ошибке
     */
    public ReaderAlreadyExistException(String message) {
        super(message);
    }
}
