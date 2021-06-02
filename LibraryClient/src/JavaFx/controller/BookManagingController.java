package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import JavaFx.utils.DateUtil;
import JavaFx.entity.AuthorEntity;
import JavaFx.entity.BookEntity;
import JavaFx.entity.EditionTypeEntity;
import JavaFx.entity.PublishingHouseEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * контроллер сцены JavaFX
 */
public class BookManagingController {
    @FXML
    private TextField bookName;
    @FXML
    private TextField issueYear;
    @FXML
    private ComboBox<String> author;
    @FXML
    private ComboBox<String> house;
    @FXML
    private ComboBox<String> edition;

    private Stage stage;
    private BookEntity bookEntity;
    private App main;
    private boolean creation;
    private final HashMap<String, AuthorEntity> authorsStorage = new HashMap<>();
    private final HashMap<String, PublishingHouseEntity> housesStorage = new HashMap<>();
    private final HashMap<String, EditionTypeEntity> editionsStorage =  new HashMap<>();

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
     * устанавливает значение параметру main
     * @param main App
     */
    public void setMain(App main) {
        this.main = main;
    }

    /**
     * Утанавливает значение параметру bookEntity
     * @param bookEntity BookEntity
     */
    public void setBook(BookEntity bookEntity) {
        fillAuthors();
        fillEditions();
        fillHouses();
        this.bookEntity = bookEntity;
        bookName.setText(bookEntity.getNameOfTheBook());
        issueYear.setText(DateUtil.format(bookEntity.getYearOfIssue()));
        issueYear.setPromptText("дд.мм.гггг");
        try {
            author.setValue(bookEntity.getAuthor().getFirstName() + ";" + bookEntity.getAuthor().getLastName() + ";"
                    + bookEntity.getAuthor().getId());
            edition.setValue(bookEntity.getEditionType().getTypeName());
            house.setValue(bookEntity.getPublishingHouse().getName() + ";" + bookEntity.getPublishingHouse().getCity());
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    @FXML
    private void handleOk(){
        if (isInputValid()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nameOfTheBook", bookName.getText());
            jsonObject.put("yearOfIssue", DateUtil.parse(issueYear.getText()));
            jsonObject.put("author", new JSONObject(authorsStorage.get(author.getValue()).toString().replace("=", ":")));
            jsonObject.put("publishingHouse", new JSONObject(housesStorage.get(house.getValue()).toString().replace("=", ":")));
            jsonObject.put("editionType", new JSONObject(editionsStorage.get(edition.getValue()).toString().replace("=", ":")));
            if(creation){
                try {
                    API.addBook(jsonObject);
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
                    jsonObject.put("id", bookEntity.getId());
                    API.updateBook(jsonObject);
                }catch (NoConnectionException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(stage);
                    alert.setTitle("Нет соединения");
                    alert.setHeaderText("Не установлено соединение с сервером");
                    alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
                    alert.showAndWait();
                }
            }
            main.handleBooks();
            stage.close();
        }
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (bookName.getText() == null || bookName.getText().length() == 0) {
            errorMessage += "Введите название книги!\n";
        }
        if (issueYear.getText() == null || issueYear.getText().length() == 0) {
            errorMessage += "Введите дату выпуска книги!\n";
        }else{
            if(!DateUtil.validDate(issueYear.getText()) || DateUtil.parse(issueYear.getText()).isAfter(LocalDate.now())){
                errorMessage += "Дата выпуска книги введена неверно!\n";
            }
        }
        if (house.getSelectionModel().getSelectedItem() == null ||
                house.getSelectionModel().getSelectedItem().length()==0){
            errorMessage += "выберите издательство книги!\n";
        }
        if (author.getSelectionModel().getSelectedItem() == null ||
                author.getSelectionModel().getSelectedItem().length()==0){
            errorMessage += "выберите автора книги!\n";
        }
        if (edition.getSelectionModel().getSelectedItem() == null ||
                edition.getSelectionModel().getSelectedItem().length()==0){
            errorMessage += "выберите тип публикации книги!\n";
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

    private void fillAuthors(){
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            for (AuthorEntity entity : API.getAllAuthors()) {
                authorsStorage.put(entity.getFirstName() + ";" + entity.getLastName() + ";" + entity.getId(), entity);
                data.add(entity.getFirstName() + ";" + entity.getLastName() + ";" + entity.getId());
            }
            author.setItems(data.sorted());
        }catch (NoConnectionException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Нет соединения");
            alert.setHeaderText("Не установлено соединение с сервером");
            alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
            alert.showAndWait();
        }
    }

    private void fillHouses(){
        try {
            ObservableList<String> data = FXCollections.observableArrayList();
            for (PublishingHouseEntity entity : API.getAllHouses()) {
                housesStorage.put(entity.getName() + ";" + entity.getCity(), entity);
                data.add(entity.getName() + ";" + entity.getCity());
            }
            house.setItems(data.sorted());
        }catch (NoConnectionException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Нет соединения");
            alert.setHeaderText("Не установлено соединение с сервером");
            alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
            alert.showAndWait();
        }
    }

    private void fillEditions(){
        try {
            ObservableList<String> data = FXCollections.observableArrayList();
            for (EditionTypeEntity entity : API.getAllEditions()) {
                editionsStorage.put(entity.getTypeName(), entity);
                data.add(entity.getTypeName());
            }
            edition.setItems(data.sorted());
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
