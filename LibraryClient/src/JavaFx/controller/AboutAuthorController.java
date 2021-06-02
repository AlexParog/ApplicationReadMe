package JavaFx.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * контроллер сцены JavaFX
 */
public class AboutAuthorController {
    private Stage stage;

    /**
     * Пустой инициализатор класса
     */
    public AboutAuthorController(){ }

    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает значение параметру stage
     * @param stage Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleClose(){
        this.stage.close();
    }
}