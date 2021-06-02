package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import JavaFx.utils.DateUtil;
import JavaFx.entity.AuthorEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.time.LocalDate;

/**
 * контроллер сцены JavaFX
 */
public class AuthorManagingController {
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField birthday;
    @FXML
    private TextField description;

    private Stage stage;
    private AuthorEntity authorEntity;
    private App main;
    private boolean creation;

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

    /**
     * Устанавливает значение параметру main
     * @param main App
     */
    public void setMain(App main) {
        this.main = main;
    }

    /**
     * Устанавливает значение параметру authorEntity
     * @param authorEntity AuthorEntity
     */
    public void setAuthor(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
        name.setText(authorEntity.getFirstName());
        surname.setText(authorEntity.getLastName());
        birthday.setText(DateUtil.format(authorEntity.getBirthday()));
        description.setText(authorEntity.getDescription());
        birthday.setPromptText("дд.мм.гггг");
    }

    @FXML
    private void handleOk(){
        if (isInputValid()) {
            authorEntity.setFirstName(name.getText());
            authorEntity.setLastName(surname.getText());
            authorEntity.setBirthday(DateUtil.parse(birthday.getText()));
            authorEntity.setDescription(description.getText());
            JSONObject jsonObject = new JSONObject();
            if (!creation) {
                jsonObject.put("id", authorEntity.getId());
            }
            jsonObject.put("firstName", authorEntity.getFirstName());
            jsonObject.put("lastName", authorEntity.getLastName());
            jsonObject.put("birthday", authorEntity.getBirthday());
            jsonObject.put("description", authorEntity.getDescription());
            System.out.println(jsonObject);
            if (creation){
                try {
                    API.addAuthor(jsonObject);
                    main.handleAuthors();
                }catch (NoConnectionException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(stage);
                    alert.setTitle("Нет соединения");
                    alert.setHeaderText("Не установлено соединение с сервером");
                    alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
                    alert.showAndWait();
                }
            }else{
                try {
                    API.updateAuthor(jsonObject);
                    main.handleAuthors();
                }catch (NoConnectionException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(stage);
                    alert.setTitle("Нет соединения");
                    alert.setHeaderText("Не установлено соединение с сервером");
                    alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
                    alert.showAndWait();
                }
            }
            stage.close();
        }
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "Введите имя автора!\n";
        }else{
            if(!name.getText().matches("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]*")){
                errorMessage += "Имя автора содержит недопустимые символы!\n";
            }
        }
        if (surname.getText() == null || surname.getText().length() == 0) {
            errorMessage += "Введите фамилию автора!\n";
        }else{
            if(!surname.getText().matches("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]*")){
                errorMessage += "Фамилия автора содержит недопустимые символы!\n";
            }
        }
        if (birthday.getText() == null || birthday.getText().length() == 0) {
            errorMessage += "Введите дату рождения автора!\n";
        }else{
            if(!DateUtil.validDate(birthday.getText())){
                errorMessage += "Дата рождения автора введена неверно!\n";
            }else{
                if(DateUtil.parse(birthday.getText()).isAfter(LocalDate.now().minusYears(14))){
                    errorMessage += "Дата рождения автора введена неверно!\nАвтор должен быть старше 14 лет!";
                }
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Некорректные данные");
            alert.setHeaderText("Пожалуйста, исправьте некорректные поля!");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Устанавливает значение параметру creation
     * @param creation boolean
     */
    public void setCreation(boolean creation) {
        this.creation = creation;
    }
}
