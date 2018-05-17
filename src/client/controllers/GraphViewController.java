package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.DataPoint;
import models.Plant;

import java.util.ArrayList;


import java.time.LocalDate;

/**
 * The controller for the Graph View. This controller handles the logic for the
 * view that the user is presented with then they select a plant from the menu.
 * The view presents the user with graphs showing the history of that plants
 * data.
 * @author Anton, Daniel.
 */
public class GraphViewController {

	@FXML
	private CategoryAxis valueXAxis;
	@FXML
	private NumberAxis valueYAxis;
	@FXML
	private CategoryAxis temperatureXAxis;
	@FXML
	private NumberAxis temperatureYAxis;
	@FXML
	private Label plantAliasLabel;
	@FXML
	private LineChart<String, Integer> valueChart;
	@FXML
	private LineChart<String, Integer> temperatureChart;
	@FXML
	private DatePicker fromDatePicker;
	@FXML
	private DatePicker toDatePicker;
	@FXML
	private HBox topPanelHBox;
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

		if (dataPointArrayList != null && dataPointArrayList.size() > 0) {
			showGraph(dataPointArrayList);
			initializeDisabledDateCells();
			initializeDatePickListeners();
		}

		valueChart.getData().addAll(soilMoistureSeries);
		valueChart.getData().addAll(humiditySeries);
		valueChart.getData().addAll(lightLevelSeries);
		temperatureChart.getData().addAll(temperatureSeries);

		valueXAxis.setTickLabelRotation(0);
		temperatureXAxis.setTickLabelRotation(0);
	}
	
	/**
	 * Disables date cells with no datapoints from the date picker.
	 */
	private void initializeDisabledDateCells() {
		ArrayList<LocalDate> existingDates = new ArrayList<LocalDate>();
		for (DataPoint dp : dataPointArrayList) {
			String[] dateStringArray = dp.getTimeStamp().substring(0, 10).split("-");
			existingDates.add(LocalDate.of(Integer.parseInt(dateStringArray[0]), Integer.parseInt(dateStringArray[1]),
					Integer.parseInt(dateStringArray[2])));
		}
		fromDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);
						boolean validDate = true;
						for (LocalDate d : existingDates) {
							if (date.isEqual(d)) {
								validDate = false;
							} 
						}
						setDisable(empty || validDate);
					}
				};
			}
		});
		toDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);
						boolean validDate = true;
						for (LocalDate d : existingDates) {
							if (date.isEqual(d)) {
								validDate = false;
							} 
						}
						setDisable(empty || validDate || date.isBefore(fromDatePicker.getValue()));
					}
				};
			}
		});
	}

	/**
	 * Adds a change listener to the date pickers.
	 */
	private void initializeDatePickListeners() {
		fromDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			if (toDatePicker.valueProperty().isNotNull().get()) {
				if (fromDatePicker.getValue().isBefore(toDatePicker.getValue())) {
					showCustomGraph();
				} else {
					toDatePicker.setValue(newValue);
					showCustomGraph();
				}
			}
		});
		toDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			if (fromDatePicker.valueProperty().isNotNull().get()) {
				if (fromDatePicker.getValue().isBefore(toDatePicker.getValue())) {
					showCustomGraph();
				} else {
					toDatePicker.setValue(newValue);
					showCustomGraph();
				}
			}
		});
	}

	/**
	 * Extracts the datapoints from the selected dates in the date pickers.
	 * Sends the datapoints to the showGraph method.
	 */
	private void showCustomGraph() {
		int startIndex = 0;
		int stopIndex = 0;
		boolean firstDateNotSet = true;
		ArrayList<DataPoint> customDataPointArrayList = new ArrayList<DataPoint>();
		for (DataPoint dp : dataPointArrayList) {
			String[] dateStringArray = dp.getTimeStamp().substring(0, 10).split("-");
			LocalDate date = LocalDate.of(Integer.parseInt(dateStringArray[0]), Integer.parseInt(dateStringArray[1]),
					Integer.parseInt(dateStringArray[2]));
			if (fromDatePicker.getValue().isEqual(date) && firstDateNotSet) {
				startIndex = dataPointArrayList.indexOf(dp);
				firstDateNotSet = false;
			} else if (toDatePicker.getValue().isEqual(date)) {
				stopIndex = dataPointArrayList.indexOf(dp);
			}
		}
		for (int i = startIndex; i <= stopIndex; i++) {
			customDataPointArrayList.add(dataPointArrayList.get(i));
		}
		showGraph(customDataPointArrayList);
	}

	/**
	 * Generates average values from the datapoint array list.
	 * Presents the average values in the graph.
	 * @param dataArrayList Array List to trim and show.
	 */
	private void showGraph(ArrayList<DataPoint> dataArrayList) {
		this.resetSeries();
		ArrayList<Integer> soilMoistureArrayList = new ArrayList<Integer>();
		ArrayList<Integer> lightLevelArrayList = new ArrayList<Integer>();
		ArrayList<Integer> humidityArrayList = new ArrayList<Integer>();
		ArrayList<Integer> temperatureArrayList = new ArrayList<Integer>();
		ArrayList<String> dateArrayList = new ArrayList<String>();
		int trimmedArrayList = (dataArrayList.size() / 48);
		for (int i = 0; i < dataArrayList.size() - trimmedArrayList; i += trimmedArrayList) {
			int soilMoistureLevelAverage = 0;
			int lightLevelAverage = 0;
			int humidityAverage = 0;
			int temperatureAverage = 0;
			for (int j = 0; j < trimmedArrayList; j++) {
				soilMoistureLevelAverage += dataArrayList.get(i + j).getSoilMoistureLevel();
				lightLevelAverage += dataArrayList.get(i + j).getLightLevel();
				humidityAverage += dataArrayList.get(i + j).getHumidity();
				temperatureAverage += dataArrayList.get(i + j).getTemperature();
			}
			soilMoistureLevelAverage = soilMoistureLevelAverage / trimmedArrayList;
			lightLevelAverage = lightLevelAverage / trimmedArrayList;
			humidityAverage = humidityAverage / trimmedArrayList;
			temperatureAverage = temperatureAverage / trimmedArrayList;

			soilMoistureArrayList.add(soilMoistureLevelAverage);
			lightLevelArrayList.add(lightLevelAverage);
			humidityArrayList.add(humidityAverage);
			temperatureArrayList.add(temperatureAverage);
			dateArrayList.add(dataArrayList.get(i).getTimeStamp());
		}

		for (int i = 0; i < 47; i++) {
			soilMoistureSeries.getData()
					.add(new XYChart.Data<>(dateFormat(dateArrayList.get(i)), soilMoistureArrayList.get(i)));
			lightLevelSeries.getData()
					.add(new XYChart.Data<>(dateFormat(dateArrayList.get(i)), lightLevelArrayList.get(i)));
			humiditySeries.getData()
					.add(new XYChart.Data<>(dateFormat(dateArrayList.get(i)), humidityArrayList.get(i)));
			temperatureSeries.getData()
					.add(new XYChart.Data<>(dateFormat(dateArrayList.get(i)), temperatureArrayList.get(i)));
		}
	}

	/**
	 * Resets the series in the graph.
	 */
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