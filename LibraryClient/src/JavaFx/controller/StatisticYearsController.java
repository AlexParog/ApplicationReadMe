package JavaFx.controller;

import JavaFx.App;
import JavaFx.exception.NoConnectionException;
import JavaFx.service.API;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.time.LocalDate;

/**
 * контроллер сцены JavaFX
 */
public class StatisticYearsController {
    @FXML
    private LineChart<String, Float> chart;
    final CategoryAxis x = new CategoryAxis();
    final NumberAxis y = new NumberAxis();
    private Stage stage;
    private App main;

    /**
     * Пустой инициализатор класса
     */
    public StatisticYearsController(){}

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
            JSONObject data = API.getYearStatistics();
            x.setLabel("Год");
            y.setLabel("Количество принятых книг");
            chart.setTitle("количество возвращаемых книг по годам");
            XYChart.Series<String, Float> series = new XYChart.Series<>();
            series.setName("Количество в штуках");
            for (int i = 2010; i < LocalDate.now().getYear() + 1; i++) {
                if (data.has(String.valueOf(i))) {
                    series.getData().add(new XYChart.Data(String.valueOf(i), data.get(String.valueOf(i))));
                } else {
                    series.getData().add(new XYChart.Data(String.valueOf(i), 0));
                }
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
