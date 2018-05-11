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
	 * Initializes the Graph View. Sets the background color of the top panel,
	 * and initializes the graphs with data from the selected plant.
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

		if (dataPointArrayList != null) {
			for (DataPoint dp : dataPointArrayList) {
				soilMoistureSeries.getData()
						.add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getSoilMoistureLevel()));
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

	// TODO: Fix bug with Dates on the X-axis
	@SuppressWarnings("unchecked")
	public void showDayGraph() {
		int dayLimit = 48;
		if (dataPointArrayList.size() >= dayLimit) {
			this.resetSeries();
			for (int i = dataPointArrayList.size() - 48; i < dataPointArrayList.size(); i++) {
				soilMoistureSeries.getData()
						.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
								dataPointArrayList.get(i).getSoilMoistureLevel()));
				lightLevelSeries.getData().add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
						dataPointArrayList.get(i).getLightLevel()));
				humiditySeries.getData().add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
						dataPointArrayList.get(i).getHumidity()));
				temperatureSeries.getData().add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
						dataPointArrayList.get(i).getTemperature()));
			}
		}
	}

	public void showWeekGraph() {
		int weekLimit = 48 * 7;
		if (dataPointArrayList.size() >= weekLimit) {
			this.resetSeries();
			for (int i = dataPointArrayList.size() - (48 * 7) - 1; i < dataPointArrayList.size(); i++) {
				if (i % 6 == 0) {
					soilMoistureSeries.getData()
							.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
									dataPointArrayList.get(i).getSoilMoistureLevel()));
					lightLevelSeries.getData()
							.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
									dataPointArrayList.get(i).getLightLevel()));
					humiditySeries.getData()
							.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
									dataPointArrayList.get(i).getHumidity()));
					temperatureSeries.getData()
							.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
									dataPointArrayList.get(i).getTemperature()));
				}
			}
		}
	}

	public void showMonthGraph() {
		int monthLimit = 48 * 7 * 30;
		if (dataPointArrayList.size() >= monthLimit) {
			this.resetSeries();
			for (int i = dataPointArrayList.size() - monthLimit - 1; i < dataPointArrayList.size(); i++) {
				if (i % 5 == 0) {
					soilMoistureSeries.getData()
							.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
									dataPointArrayList.get(i).getSoilMoistureLevel()));
					lightLevelSeries.getData()
							.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
									dataPointArrayList.get(i).getLightLevel()));
					humiditySeries.getData()
							.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
									dataPointArrayList.get(i).getHumidity()));
					temperatureSeries.getData()
							.add(new XYChart.Data<>(dateFormat(dataPointArrayList.get(i).getTimeStamp()),
									dataPointArrayList.get(i).getTemperature()));
				}
			}
		}
	}

	public void showAllGraph() {
		this.resetSeries();
		for (DataPoint dp : dataPointArrayList) {
			soilMoistureSeries.getData()
					.add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getSoilMoistureLevel()));
			lightLevelSeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getLightLevel()));
			humiditySeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getHumidity()));
			temperatureSeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getTemperature()));
		}
	}

	private void resetSeries() {
		soilMoistureSeries.getData().clear();
		lightLevelSeries.getData().clear();
		humiditySeries.getData().clear();
		temperatureSeries.getData().clear();
		soilMoistureSeries.setName("Jordfuktighet");
		lightLevelSeries.setName("Ljusnivå");
		humiditySeries.setName("Luftfuktighet");
		temperatureSeries.setName("Temperatur");
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