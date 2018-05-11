package client.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.DataPoint;
import models.Plant;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * The controller for the Graph View. This controller handles the logic for the
 * view that the user is presented with then they select a plant from the menu.
 * The view presents the user with graphs showing the history of that plants
 * data.
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
	public Button dayButton;
	@FXML
	public Button weekButton;
	@FXML
	public Button monthButton;
	@FXML
	public Button allButton;
	@FXML
	HBox topPanelHBox;
	private ArrayList<DataPoint> dataPointArrayList;
	private Series<String, Integer> soilMoistureSeries;
	private Series<String, Integer> lightLevelSeries;
	private Series<String, Integer> humiditySeries;
	private Series<String, Integer> temperatureSeries;

	/**
	 * Initializes the Graph View. Sets the background color of the top panel, and
	 * initializes the graphs with data from the selected plant.
	 * 
	 * @param plant
	 *            The plant to retrieve data from.
	 */
	@SuppressWarnings("unchecked")
	public void initialize(Plant plant) {
		topPanelHBox.setStyle("-fx-background-color: #a8cb9c;");

		plantAliasLabel.setText(plant.getAlias());

		this.dataPointArrayList = plant.getDataPoints();

		soilMoistureSeries = new XYChart.Series<>();
		lightLevelSeries = new XYChart.Series<>();
		humiditySeries = new XYChart.Series<>();
		temperatureSeries = new XYChart.Series<>();
		soilMoistureSeries.setName("Jordfuktighet");
		lightLevelSeries.setName("Ljusnivå");
		humiditySeries.setName("Luftfuktighet");
		temperatureSeries.setName("Temperatur");

		if (dataPointArrayList.size() > 0) {
			showAllGraph();
		}

		valueChart.getData().addAll(soilMoistureSeries);
		valueChart.getData().addAll(humiditySeries);
		valueChart.getData().addAll(lightLevelSeries);
		temperatureChart.getData().addAll(temperatureSeries);

		valueXAxis.setTickLabelRotation(0);
		temperatureXAxis.setTickLabelRotation(0);
	}

	@SuppressWarnings("unchecked")
	public void showDayGraph() {
		ArrayList<DataPoint> lastDayDataPointArrayList = new ArrayList<DataPoint>();
		for (int i = dataPointArrayList.size() - 48; i < dataPointArrayList.size(); i++) {
			lastDayDataPointArrayList.add(dataPointArrayList.get(i));
		}
		showGraph(lastDayDataPointArrayList);
	}

	public void showWeekGraph() {
		ArrayList<DataPoint> lastWeekDataPointArrayList = new ArrayList<DataPoint>();
		for (int i = dataPointArrayList.size() - 336; i < dataPointArrayList.size(); i++) {
			lastWeekDataPointArrayList.add(dataPointArrayList.get(i));
		}
		showGraph(lastWeekDataPointArrayList);
	}

	public void showMonthGraph() {
		ArrayList<DataPoint> lastMonthDataPointArrayList = new ArrayList<DataPoint>();
		for (int i = dataPointArrayList.size() - 1488; i < dataPointArrayList.size(); i++) {
			lastMonthDataPointArrayList.add(dataPointArrayList.get(i));
		}
		showGraph(lastMonthDataPointArrayList);
	}

	public void showAllGraph() {
		showGraph(dataPointArrayList);
	}

	public void showGraph(ArrayList<DataPoint> graphList) {
		this.resetSeries();
		ArrayList<Integer> soilMoistureArrayListYear = new ArrayList<Integer>();
		ArrayList<Integer> lightLevelArrayListYear = new ArrayList<Integer>();
		ArrayList<Integer> humidityArrayListYear = new ArrayList<Integer>();
		ArrayList<Integer> temperatureArrayListYear = new ArrayList<Integer>();
		ArrayList<String> dateArrayListYear = new ArrayList<String>();
		int trimmedArrayList = (graphList.size() / 48);
		for (int i = 0; i < graphList.size() - trimmedArrayList; i += trimmedArrayList) {
			int soilMoistureLevelAverage = 0;
			int lightLevelAverage = 0;
			int humidityAverage = 0;
			int temperatureAverage = 0;
			for (int j = 0; j < trimmedArrayList; j++) {
				soilMoistureLevelAverage += graphList.get(i + j).getSoilMoistureLevel();
				lightLevelAverage += graphList.get(i + j).getLightLevel();
				humidityAverage += graphList.get(i + j).getHumidity();
				temperatureAverage += graphList.get(i + j).getTemperature();
			}
			soilMoistureLevelAverage = soilMoistureLevelAverage / trimmedArrayList;
			lightLevelAverage = lightLevelAverage / trimmedArrayList;
			humidityAverage = humidityAverage / trimmedArrayList;
			temperatureAverage = temperatureAverage / trimmedArrayList;

			soilMoistureArrayListYear.add(soilMoistureLevelAverage);
			lightLevelArrayListYear.add(lightLevelAverage);
			humidityArrayListYear.add(humidityAverage);
			temperatureArrayListYear.add(temperatureAverage);
			dateArrayListYear.add(graphList.get(i).getTimeStamp());
		}

		for (int i = 0; i < 47; i++) {
			soilMoistureSeries.getData()
					.add(new XYChart.Data<>(dateFormat(dateArrayListYear.get(i)), soilMoistureArrayListYear.get(i)));
			lightLevelSeries.getData()
					.add(new XYChart.Data<>(dateFormat(dateArrayListYear.get(i)), lightLevelArrayListYear.get(i)));
			humiditySeries.getData()
					.add(new XYChart.Data<>(dateFormat(dateArrayListYear.get(i)), humidityArrayListYear.get(i)));
			temperatureSeries.getData()
					.add(new XYChart.Data<>(dateFormat(dateArrayListYear.get(i)), temperatureArrayListYear.get(i)));
		}
	}

	private void resetSeries() {
		soilMoistureSeries.getData().clear();
		lightLevelSeries.getData().clear();
		humiditySeries.getData().clear();
		temperatureSeries.getData().clear();
	}

	/**
	 * Formats a date string from yyyy-mm-dd hh:mm:ss to yy-mm-dd \n hh:mm
	 * 
	 * @param dateToFormat
	 *            The date to format.
	 * @return The formatted date.
	 */
	public String dateFormat(String dateToFormat) {
		return dateToFormat.substring(2, 10) + "\n   " + dateToFormat.substring(11, 16);
	}

}