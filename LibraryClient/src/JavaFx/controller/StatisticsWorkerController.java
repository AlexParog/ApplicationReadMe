package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.json.JSONObject;

/**
 * контроллер сцены JavaFX
 */
public class StatisticsWorkerController {
    @FXML
    private BarChart<String, Integer> chart;
    final CategoryAxis x = new CategoryAxis();
    final NumberAxis y = new NumberAxis();
    private Stage stage;
    private App main;

    /**
     * Пустой инициализатор класса
     */
    public StatisticsWorkerController(){}

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
    private void initialize() {
        try {
            JSONObject data = API.getWorkerStatistics();
            x.setLabel("Работник");
            y.setLabel("Количество выдач");
            chart.setTitle("Лучшие работники по выдачам");
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName("Количество выдач");
            for (String key : data.keySet()) {
                series.getData().add(new XYChart.Data(key, data.get(key)));
            }
            chart.getData().add(series);
            chart.getParent().setStyle("-fx-border-style: solid; -fx-background-color: #C0C0C0;");
        }catch (NoConnectionException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Нет соединения");
            alert.setHeaderText("Не установлено соединение с сервером");
            alert.setContentText("Связь с сервером не установлена. Попробуйте позже.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleClose(){
        this.stage.close();
    }
}
