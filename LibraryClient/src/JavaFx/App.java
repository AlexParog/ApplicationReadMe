package JavaFx;

import JavaFx.controller.*;
import JavaFx.entity.CoWorkersEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Запускаемое приложение и контроллер сцены JavaFX
 */
public class App extends Application {

    private Stage primaryStage;
    private AnchorPane anchorPane;
    private BorderPane rootLayout;
    private CoWorkersEntity worker;

    /**
     * возвращает значение параметра primaryStage
     * @return Stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * возвращает значение параметра anchorPane
     * @return AnchorPane
     */
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * возвращает значение параметра return rootLayout;
     * @return BorderPane
     */
    public BorderPane getRootLayout() {
        return rootLayout;
    }

    /**
     * возвращает значение параметра worker
     * @return CoWorkersEntity
     */
    public CoWorkersEntity getWorker() {
        return worker;
    }

    /**
     * Устанавливает значение параметру worker
     * @param worker CoWorkersEntity
     */
    public void setWorker(CoWorkersEntity worker) {
        this.worker = worker;
    }

    /**
     * Запускает показ интерфейса
     * @param primaryStage Stage
     */
    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ReadMe");

        initLogin();
    }

    /**
     * инициализирует стартовое окно
     */
    public void initLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("views/login.fxml"));
            anchorPane = loader.load();
            LoginController loginController = loader.getController();
            loginController.setMain(this);
            Scene scene = new Scene((anchorPane));
            primaryStage.setScene(scene);
            loginController.setStage(primaryStage);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * инициализирует главное окно с выданными книгами
     */
    public void initIssuePage(){
        initRoot();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("views/issuesOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);
            IssuesOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * инициализирует окно поиска
     */
    public void initSearch(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("views/search.fxml"));
            AnchorPane layout = loader.load();
            SearchController controller = loader.getController();
            Stage stage = new Stage();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * инициализирцет главное окно с авторами
     */
    public void handleAuthors(){
        initRoot();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("views/authorsOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);
            AuthorsOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Инициализирует главное окно с книгами
     */
    public void handleBooks(){
        initRoot();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("views/booksOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            this.getRootLayout().setCenter(personOverview);
            BooksOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleIssues(){
        initRoot();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("views/issuesOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            this.getRootLayout().setCenter(personOverview);
            IssuesOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Инициализирует меню-бар
     */
    public void initRoot(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("views/menuBar.fxml"));
            rootLayout = loader.load();
            MenuBarController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.setMain(this);
            controller.setWorker(worker);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * точка входа в программу
     * @param args String[]
     */
    public static void main(String[] args) {
        launch(args);
    }
}
