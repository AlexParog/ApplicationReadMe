package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import JavaFx.utils.PhoneNumber;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

/**
 * контроллер сцены JavaFX
 */
public class SignUpWindowController {

    @FXML
    private TextField loginRegistrationTextField;

    @FXML
    private TextField CityRegistrationTextField;

    @FXML
    private TextField FirstNameRegistrationTextField;

    @FXML
    private TextField LastNameRegistrationTextField;

    @FXML
    private TextField TelephoneRegistrationTextField;

    @FXML
    private TextField PasswordRegistrationTextField;

    private Stage stage;
    private App main;

    /**
     * Пустой инициализатор класса
     */
    public SignUpWindowController(){}

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
    private void signUpAction(){
        if (isInputValid()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", loginRegistrationTextField.getText());
            jsonObject.put("firstName", FirstNameRegistrationTextField.getText());
            jsonObject.put("lastName", LastNameRegistrationTextField.getText());
            jsonObject.put("city", CityRegistrationTextField.getText());
            jsonObject.put("telephone", TelephoneRegistrationTextField.getText());
            jsonObject.put("password", PasswordRegistrationTextField.getText());

            try {
                boolean result = API.sighUp(jsonObject);
                if (result) {
                    main.setWorker(API.findWorker(loginRegistrationTextField.getText()));
                    main.initIssuePage();

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Ошибка регистрации");
                    alert.setHeaderText("Неверный логин или телефон");
                    alert.setContentText("Пользователь с данным логином или телефоном уже зарегистрирован.\n" +
                            "Пожалуйста, введите уникальный логин или телефон!");
                    alert.showAndWait();
                }
            } catch (NoConnectionException e) {
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
    private void cancelAction() throws IOException {
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

        if (loginRegistrationTextField.getText() == null || loginRegistrationTextField.getText().length() == 0) {
            errorMessage += "No valid login!\n";
        }
        if (FirstNameRegistrationTextField.getText() == null || FirstNameRegistrationTextField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (LastNameRegistrationTextField.getText() == null || LastNameRegistrationTextField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }

        if (CityRegistrationTextField.getText() == null || CityRegistrationTextField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (PasswordRegistrationTextField.getText() == null || PasswordRegistrationTextField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }

        if (TelephoneRegistrationTextField.getText() == null || TelephoneRegistrationTextField.getText().length() == 0) {
            errorMessage += "No valid telephone!\n";
        } else {
            if (!PhoneNumber.isValidatePhoneNumber(TelephoneRegistrationTextField.getText())) {
                errorMessage += "No valid telephone number!\n";
            }
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

