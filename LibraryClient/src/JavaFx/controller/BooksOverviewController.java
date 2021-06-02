package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import JavaFx.utils.DateUtil;
import JavaFx.entity.BookEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * контроллер сцены JavaFX главного окна с книгами
 */
public class BooksOverviewController {
    @FXML
    private TableView<BookEntity> bookTable;
    @FXML
    private TableColumn<BookEntity, String> bookColumn;
    @FXML
    private TableColumn<BookEntity, String> authorColumn;
    @FXML
    private Label bookName;
    @FXML
    private Label bookAuthor;
    @FXML
    private Label issueYear;
    @FXML
    private Label publishingHouse;
    @FXML
    private Label edition;

    private App main;
    private Stage stage;

    /**
     * Пустой инициализатор класса
     */
    public BooksOverviewController(){}

    @FXML
    private void initialize(){
        try {
            bookTable.setItems(API.getAllBooks());
            bookColumn.setCellValueFactory(cellData -> cellData.getValue().getBookNameProperty());
            authorColumn.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());

            showBookOverviewDetails(null);
            bookTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showBookOverviewDetails(newValue)
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

    private void showBookOverviewDetails(BookEntity book){
        if(book != null){
            bookName.setText(book.getNameOfTheBook());
            issueYear.setText(DateUtil.format(book.getYearOfIssue()));
            bookAuthor.setText(book.getAuthor().getFirstName()+" "+book.getAuthor().getLastName());
            publishingHouse.setText(book.getPublishingHouse().getName()+", "+book.getPublishingHouse().getCity());
            edition.setText(book.getEditionType().getTypeName());
        }
        else{
            bookName.setText("");
            issueYear.setText("");
            bookAuthor.setText("");
            publishingHouse.setText("");
            edition.setText("");
        }
    }

    /**
     * Устанавливает значение параметру main
     * @param main App
     */
    public void setMain(App main){
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
    private void handleNewBook(){
        BookEntity bookEntity = new BookEntity();
        showBookEdit(bookEntity, true);
    }

    @FXML
    private void handleEditBook(){
        BookEntity bookEntity = bookTable.getSelectionModel().getSelectedItem();
        if (bookEntity != null) {
            showBookEdit(bookEntity, false);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Нет выделения");
            alert.setHeaderText("Книга не выбрана");
            alert.setContentText("Необходимо выбрать книгу в таблице перед изменением!");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteBook(){
        int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Подтвердите удаление");
            alert.setHeaderText("Удалить эту книгу?");
            alert.setContentText("Вы действительно хотите удалить книгу?\n" +
                    "Удаление приведет к удалению всех экземпляров и истории их выдачи.");
            ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
            if (answer.equals(ButtonType.OK)){
                try {
                    API.deleteBook(bookTable.getItems().get(selectedIndex).getId());
                    main.handleBooks();
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
            alert.setHeaderText("Книга не выбрана");
            alert.setContentText("Необходимо выбрать книгу в таблице перед изменением!");

            alert.showAndWait();
        }
    }

    private void showBookEdit(BookEntity bookEntity, boolean creation){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(main.getClass().getResource("views/bookManaging.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Книга");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);
            BookManagingController controller = loader.getController();
            controller.setStage(stage);
            controller.setBook(bookEntity);
            controller.setMain(this.main);
            controller.setCreation(creation);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
