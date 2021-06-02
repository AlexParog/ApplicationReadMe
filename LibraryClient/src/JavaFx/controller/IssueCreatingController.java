package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import JavaFx.utils.DateUtil;
import JavaFx.utils.PhoneNumber;
import JavaFx.entity.ExemplarEntity;
import JavaFx.entity.IssueOfBookEntity;
import JavaFx.entity.ReaderEntity;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.time.LocalDate;

/**
 * контроллер сцены JavaFX
 */
public class IssueCreatingController {
    @FXML
    private TableView<ExemplarEntity> exemplarTable;
    @FXML
    private TableColumn<ExemplarEntity,String> bookColumn;
    @FXML
    private TableColumn<ExemplarEntity,String> authorColumn;
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
    @FXML
    private Label inventoryNumber;
    @FXML
    private TextField telephone;
    @FXML
    private TextField returnDate;

    private Stage stage;

    private App main;

    private ReaderEntity readerEntity;

    /**
     * Пустой инициализатор класса
     */
    public IssueCreatingController(){}

    @FXML
    private void initialize(){
        bookColumn.setCellValueFactory(cellData -> cellData.getValue().getBookEntity().getBookNameProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getBookEntity().getAuthorProperty());
        showExemplarDetails(null);
        exemplarTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showExemplarDetails(newValue)
        );
    }

    /**
     * Устанавливает данные для отображения в таблице exemplarTable
     * @param exemplarEntities ObservableList с ExemplarEntity
     */
    public void setTableItems(ObservableList<ExemplarEntity> exemplarEntities){
        exemplarTable.setItems(exemplarEntities);
        returnDate.setPromptText("дд.мм.гггг");
    }

    /**
     * Устанавливает значение параметру main
     * @param main App
     */    public void setMain(App main) {
        this.main = main;
    }

    /**
     * Устанавливает значение параметру stage
     * @param stage Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showExemplarDetails(ExemplarEntity exemplarEntity){
        if(exemplarEntity != null){
            bookName.setText(exemplarEntity.getBookEntity().getNameOfTheBook());
            issueYear.setText(DateUtil.format(exemplarEntity.getBookEntity().getYearOfIssue()));
            bookAuthor.setText(exemplarEntity.getBookEntity().getAuthor().getFirstName()+" "+exemplarEntity.getBookEntity().getAuthor().getLastName());
            publishingHouse.setText(exemplarEntity.getBookEntity().getPublishingHouse().getName()+", "+exemplarEntity.getBookEntity().getPublishingHouse().getCity());
            edition.setText(exemplarEntity.getBookEntity().getEditionType().getTypeName());
            inventoryNumber.setText(String.valueOf(exemplarEntity.getInventoryNumber()));
        }
        else{
            bookName.setText("");
            issueYear.setText("");
            bookAuthor.setText("");
            publishingHouse.setText("");
            edition.setText("");
            inventoryNumber.setText("");
        }
    }

    @FXML
    private void handleOK(){
        if (validation()){
            IssueOfBookEntity issueOfBookEntity = new IssueOfBookEntity();
            issueOfBookEntity.setWorker(main.getWorker());
            issueOfBookEntity.setReader(readerEntity);
            issueOfBookEntity.setExemplarEntity(exemplarTable.getItems().get(exemplarTable.getSelectionModel().getSelectedIndex()));
            issueOfBookEntity.setReturnDate(DateUtil.parse(returnDate.getText()));
            issueOfBookEntity.setLost(false);
            issueOfBookEntity.setInventoryNumber(exemplarTable.getItems().get(exemplarTable.getSelectionModel().getSelectedIndex()).getInventoryNumber());
            try {
                API.addIssue(new JSONObject(issueOfBookEntity.toString().replace("=", ":")));
                stage.close();
                main.initIssuePage();
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
    private void handleBack(){
        stage.close();
        main.initIssuePage();
    }

    private boolean validation(){
        if(readerExist()){
            if(DateUtil.validDate(returnDate.getText())){
                if(DateUtil.parse(returnDate.getText()).isAfter(LocalDate.now())){
                    if(exemplarTable.getSelectionModel().getSelectedIndex()>=0){
                        return true;
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initOwner(stage);
                        alert.setTitle("Нет выделения");
                        alert.setHeaderText("Книга не выбрана");
                        alert.setContentText("Необходимо выбрать книгу в таблице перед выдачей!");
                        alert.showAndWait();
                        return false;
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Неверная дата");
                    alert.setHeaderText("Дата выбрана неверно");
                    alert.setContentText("Необходимо выбрать дату позже сегодняшнего дня!");
                    alert.showAndWait();
                    return false;
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Дата введена некорректно");
                alert.setContentText("Введите верно даты возврата!");
                alert.showAndWait();
                return false;
            }
        }else{
            return false;
        }
    }

    private boolean readerExist(){
        if(PhoneNumber.isValidatePhoneNumber(telephone.getText())){
            try {
                readerEntity = API.findReader(telephone.getText());
                if (readerEntity != null) {
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Читатель не найден");
                    alert.setContentText("Читатель с таким номером не найден!");
                    alert.showAndWait();
                    return false;
                }
            }catch (NoConnectionException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Нет соединения");
                alert.setHeaderText("Не установлено соединение с сервером");
                alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
                alert.showAndWait();
                return false;
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("Номер введен неверно");
            alert.setContentText("Введите верно мобильный номер!");
            alert.showAndWait();
            return false;
        }
    }
}
