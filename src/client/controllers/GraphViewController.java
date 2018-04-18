package client.controllers;

import models.Plant;
import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class GraphViewController {

	private final NumberAxis valueXAxis = new NumberAxis(0, 100, 5);
	private final NumberAxis valueYAxis = new NumberAxis(0, 100, 5);
	private final NumberAxis temperatureXAxis = new NumberAxis(0, 100, 5);
	private final NumberAxis temperatureYAxis = new NumberAxis(0, 100, 5);

	@FXML
	private Label plantAliasLabel;
	@FXML
	private AreaChart<Number, Number> valueChart = new AreaChart<Number,Number>(valueXAxis, valueYAxis);
	@FXML
	private AreaChart<Number, Number> temperatureChart = new AreaChart<Number,Number>(temperatureXAxis, temperatureYAxis);

	public void initialize(Plant plant) {
		plantAliasLabel.setText(plant.getPlantAlias());

		XYChart.Series valueSeries = new XYChart.Series();
		valueSeries.setName("Värde");
		//TODO: Hämta data och presentera.
		// valueSeries.getData().add(new XYChart.Data(1, 4));
	}

}