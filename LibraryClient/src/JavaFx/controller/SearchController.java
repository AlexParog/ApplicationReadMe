package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import JavaFx.entity.ExemplarEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class SearchController {
    @FXML
    private TextField bookName;

    @FXML
    private TextField author;

    private App main;
    private Stage stage;

    /**
     * Пустой инициализатор
     */
    public SearchController(){}

    /**
     * Устанавливает значение параметру main
     * @param main App
     */
    public void setMain(App main){
        this.main = main;
    }

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
    private void handleSearch() {
        if(validation()){
            ObservableList<ExemplarEntity> found = FXCollections.observableArrayList();
            if (bookName.getText().length() > 0) {
                try {
                    found.addAll(API.findBookByName(bookName.getText()));
                }catch (NoConnectionException e){
                    e.printStackTrace();
                }
            } else {
                try{
                    found.addAll(API.findBookByAuthor(author.getText()));
                }catch (NoConnectionException e) {
                    e.printStackTrace();
                }
            }
            if (found.size()>0){
                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(this.getClass().getResource("../views/issueCreating.fxml"));
                    AnchorPane personOverview = (AnchorPane) loader.load();
                    IssueCreatingController controller = loader.getController();
                    controller.setMain(this.main);
                    controller.setTableItems(found);
                    Scene scene = new Scene(personOverview);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    controller.setStage(stage);
                    this.stage.close();
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Вариантов не нашлось");
                alert.setHeaderText("Не нашлось ни одного экземпляра");
                alert.setContentText("Вероятно, введены неверные данные. " +
                        "Либо все данные книги заняты и отсутствуют в библиотеке");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleClose() {
        this.stage.close();
    }

    private boolean validation(){
        String errormessage = "";
        if (bookName.getText().length()!=0 && author.getText().length()!=0){
            errormessage+="Выберите лишь один параметр поиска! Второе поле оставьте пустым!";
        }else if (bookName.getText().length()==0 && author.getText().length()==0){
            errormessage+="Выберите хотя бы один параметр поиска! Заполните одно из полей!";
        }
        if (errormessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Некорректные данные");
            alert.setHeaderText("Пожалуйста, исправьте некорректные поля!");
            alert.setContentText(errormessage);
            alert.showAndWait();
            return false;
        }
    }
}
