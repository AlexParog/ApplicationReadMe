package demo.exception;

/**
 * Класс ошибки, когда работника не существует
 */
public class NoSuchCoWorkerException extends Exception {
    /**
     * инициаоизатор класса, использующий наследуемый метод
     * @param message сообщение, которое необходимо отобразить в ошибке
     */
    public NoSuchCoWorkerException(String message) {
        super(message);
    }
}

