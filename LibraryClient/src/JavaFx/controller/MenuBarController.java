package JavaFx.controller;

import JavaFx.App;
import JavaFx.entity.CoWorkersEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * контроллер сцены JavaFX
 */
public class MenuBarController {
    private App main;
    private Stage stage;
    private CoWorkersEntity worker;

    @FXML
    private Label emplInfo;

    /**
     * Пустой инициализатор класса
     */
    public MenuBarController() {
    }

    /**
     * Устанавливает значение параметру main
     *
     * @param main App
     */
    public void setMain(App main) {
        this.main = main;
    }

    /**
     * Устанавливает значение параметру worker
     *
     * @param worker CoWorkersEntity
     */
    public void setWorker(CoWorkersEntity worker) {
        this.worker = worker;
        emplInfo.setText("       " + worker.getFirstName() + " " + worker.getLastName());
    }

    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает значение параметру stage
     *
     * @param stage Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    private void handleIssues() {
        main.handleIssues();
    }

    @FXML
    private void handleBooks() {
        main.handleBooks();
    }

    @FXML
    private void handleAuthors() {
        main.handleAuthors();
    }

    @FXML
    private void handleAuthor() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(main.getClass().getResource("views/aboutAuthor.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Об авторе");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);
            AboutAuthorController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        main.getPrimaryStage().close();
    }

    @FXML
    private void handleExit() throws Exception {
        main.getPrimaryStage().close();
        main.start(new Stage());
    }

    @FXML
    private void handleStatictics1() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(main.getClass().getResource("views/statisticsYears.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Статистика по принятиям");
            stage.setResizable(false);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);
            StatisticYearsController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this.main);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStatictics2() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(main.getClass().getResource("views/statisticsWorker.fxml"));
            AnchorPane page = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Статистика по выдачам");
            stage.setResizable(false);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);
            StatisticsWorkerController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this.main);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        main.initSearch();
    }
}
