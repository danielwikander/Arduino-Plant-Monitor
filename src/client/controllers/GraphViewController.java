package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import models.DataPoint;
import models.Plant;
import java.util.ArrayList;
import javafx.scene.chart.XYChart.Series;
import javafx.*;

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


	@SuppressWarnings("unchecked")
	public void initialize(Plant plant) {
		plantAliasLabel.setText(plant.getPlantAlias());

		ArrayList<DataPoint> dataPointArrayList = plant.getDataPoints();

		Series<String, Integer> soilMoistureSeries = new XYChart.Series<>();
		Series<String, Integer> lightLevelSeries = new XYChart.Series<>();
		Series<String, Integer> humiditySeries = new XYChart.Series<>();
		Series<String, Integer> temperatureSeries = new XYChart.Series<>();
		soilMoistureSeries.setName("Jordfuktighet");
		lightLevelSeries.setName("Ljusnivå");
		humiditySeries.setName("Luftfuktighet");
		temperatureSeries.setName("Temperatur");

		for (DataPoint dp : dataPointArrayList) {
			soilMoistureSeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getSoilMoistureLevel()));
			lightLevelSeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getLightLevel()));
			humiditySeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getHumidity()));
			temperatureSeries.getData().add(new XYChart.Data<>(dateFormat(dp.getTimeStamp()), dp.getTemperature()));
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