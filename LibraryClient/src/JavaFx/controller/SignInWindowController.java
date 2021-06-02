package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * контроллер сцены JavaFX
 */
public class SignInWindowController {

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    private Stage stage;
    private App main;

    /**
     * Пустой инициализатор класса
     */
    public SignInWindowController() {}

    /**
     * Устанавливает значение параметру stage
     * @param stage Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Устанавливает значение параметру main
     * @param main App
     */
    public void setMain(App main) {
        this.main = main;
    }

    @FXML
    void initialize() { }

    @FXML
    private void signInAction(){
        if (isInputValid()) {
            try {
                boolean result = API.signIn(loginTextField.getText(), passwordTextField.getText());
                if (result) {
                    main.setWorker(API.findWorker(loginTextField.getText()));
                    main.initIssuePage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Ошибка входа");
                    alert.setHeaderText("Неверный логин или пароль");
                    alert.setContentText("Введены неверный логин или пароль. Введите данные заново!");
                    alert.showAndWait();
                }
            }catch (NoConnectionException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Нет соединения");
                alert.setHeaderText("Не установлено соединение с сервером");
                alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void cancelSignInAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(main.getClass().getResource("views/login.fxml"));
        AnchorPane layout = loader.load();
        LoginController loginController = loader.getController();
        loginController.setMain(this.main);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        loginController.setStage(this.stage);
        stage.show();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (loginTextField.getText() == null || loginTextField.getText().length() == 0) {
            errorMessage += "No valid login!\n";
        }

        if (passwordTextField.getText() == null || passwordTextField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Неверные поля");
            alert.setHeaderText("Пожалуйста, исправьте недопустимые поля");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}


