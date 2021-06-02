package JavaFx.controller;

import JavaFx.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * контроллер сцены JavaFX
 */
public class LoginController {

    private App main;
    private Stage stage;

    /**
     * Пустой инициализатор класса
     */
    public LoginController(){}

    /**
     * Устанавливает значение параметру main
     * @param main App
     */
    public void setMain(App main) {
        this.main = main;
    }

    /**
     * Устанавливает значение параметру stage
     * @param stage Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void signInAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(main.getClass().getResource("views/signInWindow.fxml"));
        AnchorPane layout = loader.load();
        SignInWindowController signInWindowController = loader.getController();
        signInWindowController.setMain(this.main);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        signInWindowController.setStage(this.stage);
        stage.show();
    }

    @FXML
    private void signUpAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(main.getClass().getResource("views/signUpWindow.fxml"));
        AnchorPane layout = loader.load();
        SignUpWindowController signUpWindowController = loader.getController();
        signUpWindowController.setMain(this.main);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        signUpWindowController.setStage(this.stage);
        stage.show();
    }
}

