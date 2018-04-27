package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.DataPoint;
import models.Plant;

import java.util.ArrayList;

/**
 * The controller for the Graph View.
 * This controller handles the logic for the view that the user is
 * presented with then they select a plant from the menu.
 * The view presents the user with graphs showing the history of
 * that plants data.
 */
public class GraphViewController {

	@FXML
	public CategoryAxis valueXAxis;
	@FXML
	public NumberAxis valueYAxis;
	@FXML
	public CategoryAxis temperatureXAxis;
	@FXML
	public NumberAxis temperatureYAxis;
	@FXML
	public Label plantAliasLabel;
	@FXML
	public LineChart<String, Integer> valueChart;
	@FXML
	public LineChart<String, Integer> temperatureChart;
	@FXML
	public DatePicker fromDatePicker;
	@FXML
	public DatePicker toDatePicker;
	@FXML
	HBox topPanelHBox;

	/**
	 * Initializes the Graph View.
	 * Sets the background color of the top panel,
	 * and initializes the graphs with data from the selected plant.
	 * @param plant	The plant to retrieve data from.
	 */
	@SuppressWarnings("unchecked")
	public void initialize(Plant plant) {
		topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");

		System.out.println(plantAliasLabel);
		System.out.println(plant.getAlias());
		plantAliasLabel.setText(plant.getAlias());

		ArrayList<DataPoint> dataPointArrayList = plant.getDataPoints();

		Series<String, Integer> soilMoistureSeries = new XYChart.Series<>();
		Series<String, Integer> lightLevelSeries = new XYChart.Series<>();
		Series<String, Integer> humiditySeries = new XYChart.Series<>();
		Series<String, Integer> temperatureSeries = new XYChart.Series<>();
		soilMoistureSeries.setName("Jordfuktighet");
		lightLevelSeries.setName("Ljusnivå");
		humiditySeries.setName("Luftfuktighet");
		temperatureSeries.setName("Temperatur");

		if(dataPointArrayList !=null){
			for (DataPoint dp : dataPointArrayList) {
				soilMoistureSeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getSoilMoistureLevel()));
				lightLevelSeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getLightLevel()));
				humiditySeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getHumidity()));
				temperatureSeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getTemperature()));
			}
		}

		valueChart.getData().addAll(soilMoistureSeries);
		valueChart.getData().addAll(humiditySeries);
        valueChart.getData().addAll(lightLevelSeries);
        temperatureChart.getData().addAll(temperatureSeries);

		valueXAxis.setTickLabelRotation(0);
		temperatureXAxis.setTickLabelRotation(0);
	}

    /**
     * Formats a date string from yyyy-mm-dd hh:mm:ss to yy-mm-dd \n hh:mm
     * @param dateToFormat  The date to format.
     * @return              The formatted date.
     */
	public String dateFormat(String dateToFormat) {
	    return dateToFormat.substring(2,10) + "\n   " + dateToFormat.substring(11,16);
    }

}