package JavaFx.exception;

/**
 * Класс исклчения, когда отсутствует соединение с сервером
 */
public class NoConnectionException extends  Exception{
    /**
     * инициаоизатор класса, использующий наследуемый метод
     * @param message сообщение, которое необходимо отобразить в ошибке
     */
    public NoConnectionException(String message) {
        super(message);
    }
}
