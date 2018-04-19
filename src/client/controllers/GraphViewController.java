package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import models.DataPoint;
import models.Plant;
import java.util.ArrayList;
import javafx.scene.chart.XYChart.Series;
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


	@SuppressWarnings("unchecked")
	public void initialize(Plant plant) {
		plantAliasLabel.setText(plant.getPlantAlias());

		ArrayList<DataPoint> dataPointArrayList = plant.getDataPoints();

		Series<String, Integer> valueSeries = new XYChart.Series<>();
		valueSeries.setName("Värde");
		for (DataPoint dp : dataPointArrayList) {
			valueSeries.getData().add(new XYChart.Data<>(dp.getTimeStamp(), dp.getSoilMoistureLevel()));
		}
		valueChart.getData().addAll(valueSeries);
//		valueSeries.getData().add(new XYChart.Data(1, 4));
//		valueSeries.getData().add(new XYChart.Data<>(1, 4));
	}

}