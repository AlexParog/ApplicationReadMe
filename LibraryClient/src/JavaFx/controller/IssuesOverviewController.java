package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import JavaFx.utils.DateUtil;
import JavaFx.entity.IssueOfBookEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.time.LocalDate;

/**
 * контроллер сцены JavaFX
 */
public class IssuesOverviewController {

    @FXML
    private TableView<IssueOfBookEntity> issueTable;
    @FXML
    private TableColumn<IssueOfBookEntity, String> bookColumn;
    @FXML
    private TableColumn<IssueOfBookEntity, String> readerColumn;
    @FXML
    private Label bookName;
    @FXML
    private Label bookAuthor;
    @FXML
    private Label inventoryNumber;
    @FXML
    private Label ClientInfo;
    @FXML
    private Label returnDate;
    @FXML
    private Label lost;
    @FXML
    private Label landedWorker;

    private App main;
    private Stage stage;

    /**
     * Пустой инициализатор класса
     */
    public IssuesOverviewController(){}

    @FXML
    private void initialize(){
        try {
            issueTable.setItems(API.getAllIssues());
            bookColumn.setCellValueFactory(cellData -> cellData.getValue().getBookNameProperty());
            readerColumn.setCellValueFactory(cellData -> cellData.getValue().getClientNameProperty());

            showIssueOverviewDetails(null);
            issueTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> showIssueOverviewDetails(newValue)
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
    private void handleNewIssue(){
        main.initSearch();
    }

    @FXML
    private void handleCloseIssue(){
        int selectedIndex = issueTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            IssueOfBookEntity issueOfBookEntity = issueTable.getItems().get(selectedIndex);
            if (issueOfBookEntity.getReturnDate().isAfter(LocalDate.now())) {
                try {
                    issueOfBookEntity.setReturnDate(LocalDate.now());
                    API.updateIssue(new JSONObject(issueTable.getItems().get(selectedIndex).toString().replace("=", ":")));
                    main.initIssuePage();
                }catch (NoConnectionException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(stage);
                    alert.setTitle("Нет соединения");
                    alert.setHeaderText("Не установлено соединение с сервером");
                    alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Книга уже принята");
                alert.setHeaderText("Данная книга уже принята");
                alert.setContentText("Нельзя принять уже принятую книгу!");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Нет выделения");
            alert.setHeaderText("Выдача не выбрана");
            alert.setContentText("Необходимо выбрать выдачу в таблице перед её закрытием!");
            alert.showAndWait();
        }
    }

    private void showIssueOverviewDetails(IssueOfBookEntity issue){
        if(issue != null){
            bookName.setText(issue.getExemplarEntity().getBookEntity().getNameOfTheBook());
            bookAuthor.setText(issue.getExemplarEntity().getBookEntity().getAuthor().getFirstName()+
                    " "+issue.getExemplarEntity().getBookEntity().getAuthor().getLastName());
            inventoryNumber.setText(String.valueOf(issue.getInventoryNumber()));
            ClientInfo.setText(issue.getReader().getFirstName()+" "+issue.getReader().getLastName());
            returnDate.setText(DateUtil.format(issue.getReturnDate()));
            if(issue.getLost()){
                lost.setText("Да");
            }else{
                lost.setText("Нет");
            }
            landedWorker.setText(issue.getWorker().getFirstName()+" "+issue.getWorker().getLastName());
        }
        else{
            bookName.setText("");
            bookAuthor.setText("");
            inventoryNumber.setText("");
            ClientInfo.setText("");
            returnDate.setText("");
            lost.setText("");
            landedWorker.setText("");
        }
    }
}
