package demo.exception;

/**
 * Класс исклчения, когда работник уже существует
 */
public class CoWorkerAlreadyExistException extends Exception{
    /**
     * инициаоизатор класса, использующий наследуемый метод
     * @param message сообщение, которое необходимо отобразить в ошибке
     */
    public CoWorkerAlreadyExistException(String message) {
        super(message);
    }
}
