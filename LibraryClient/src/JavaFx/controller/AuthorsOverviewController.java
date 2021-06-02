package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import JavaFx.utils.DateUtil;
import JavaFx.entity.AuthorEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * контроллер сцены JavaFX главного окна с авторами
 */
public class AuthorsOverviewController {
    @FXML
    private TableView<AuthorEntity> authorsTable;
    @FXML
    private TableColumn<AuthorEntity, String> nameColumn;
    @FXML
    private TableColumn<AuthorEntity, String> surnameColumn;
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label birthday;
    @FXML
    private Label description;

    private App main;
    private Stage stage;

    /**
     * инициализатор класса
     */
    public AuthorsOverviewController(){}

    @FXML
    private void initialize(){
        try {
            authorsTable.setItems(API.getAllAuthors());
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
            surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getSurnameProperty());

            showAuthorOverviewDetails(null);
            authorsTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showAuthorOverviewDetails(newValue)
            );
        }catch (NoConnectionException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Нет соединения");
            alert.setHeaderText("Не установлено соединение с сервером");
            alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
            alert.showAndWait();
        }

    }

    /**
     * устанавливает значение параметру main
     * @param main App
     */
    public void setMain(App main){
        this.main = main;
    }

    /**
     * устанавливает значение параметру stage
     * @param stage Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Обрабатывает нажатие на кнопку "Создать автора"
     */
    @FXML
    public void handleNewAuthor(){
        AuthorEntity authorEntity = new AuthorEntity();
        showAuthorEdit(authorEntity, true);
    }

    /**
     * Обрабатывает нажатие на кнопку "Изменить данные"
     */
    @FXML
    public void handleEditAuthor(){
        AuthorEntity authorEntity = authorsTable.getSelectionModel().getSelectedItem();
        if (authorEntity != null) {
            showAuthorEdit(authorEntity, false);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Нет выделения");
            alert.setHeaderText("Автор не выбран");
            alert.setContentText("Необходимо выбрать автора в таблице перед его изменением!");

            alert.showAndWait();
        }
    }

    /**
     * Обрабатывает нажатие на кнопку "Удалить автора"
     */
    @FXML
    public void handleDeleteAuthor(){
        int selectedIndex = authorsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Подтвердите удаление");
            alert.setHeaderText("Удалить этого автора?");
            alert.setContentText("Вы действительно хотите удалить автора?\n" +
                    "Удаление приведет к удалению всех его книг, их экземпляров и истории их выдачи.");
            ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
            if (answer.equals(ButtonType.OK)){
                try {
                    API.deleteAuthor(authorsTable.getItems().get(selectedIndex).getId());
                    main.handleAuthors();
                }catch (NoConnectionException e){
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.initOwner(stage);
                    alert1.setTitle("Нет соединения");
                    alert1.setHeaderText("Не установлено соединение с сервером");
                    alert1.setContentText("Связь с сервером не установлена. Попробуйте позже.");
                    alert1.showAndWait();
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Нет выделения");
            alert.setHeaderText("Автор не выбран");
            alert.setContentText("Необходимо выбрать автора в таблице перед его удалением!");
            alert.showAndWait();
        }
    }

    private void showAuthorOverviewDetails(AuthorEntity author){
        if(author != null){
            System.out.println(author.getBirthday());
            name.setText(author.getFirstName());
            surname.setText(author.getLastName());
            birthday.setText(DateUtil.format(author.getBirthday()));
            description.setText(author.getDescription());
        }
        else{
            name.setText("");
            surname.setText("");
            birthday.setText("");
            description.setText("");
        }
    }

    private void showAuthorEdit(AuthorEntity authorEntity, boolean creation){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(main.getClass().getResource("views/authorManaging.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Автор");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);
            AuthorManagingController controller = loader.getController();
            controller.setStage(stage);
            controller.setAuthor(authorEntity);
            controller.setMain(this.main);
            controller.setCreation(creation);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
