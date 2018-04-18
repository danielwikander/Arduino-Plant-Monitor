package models;

import javafx.collections.ObservableList;

import java.io.Serializable;

/**
 * This class represents a plant.
 * The object contains the plants information, such as name, icon etc.
 * //TODO: Plant should contain values for notifications.
 * //TODO: Plant should contain HashMap of DataPoints.
 */
public class Plant implements Serializable {

	private ObservableList<DataPoint> dataPoints;

	private String plantIconFile;
	private String plantSpecies;
	private String mac;
	private String email;
	private String alias;
	private boolean soilMoistureMonitor;
	private int soilMoistureMax;
	private int soilMoistureMin;
	private boolean humidityMonitor;
	private int humidityMax;
	private int humidityMin;
	private boolean temperatureMonitor;
	private int temperatureMax;
	private int temperatureMin;

	/**
	 * Constructor that sets the plants variables.
	 * @param plantSpecies	The type of plant.
	 */
	public Plant(String mac, String email, String plantSpecies, String alias,
				 boolean soilMoistureMonitor, int soilMoistureMax, int soilMoistureMin,
				 boolean humidityMonitor, int humidityMax, int humidityMin,
				 boolean temperatureMonitor, int temperatureMax, int temperatureMin) {
		this.mac = mac;
		this.email = email;
		this.plantSpecies = plantSpecies;
		this.alias = alias;
		this.soilMoistureMonitor = soilMoistureMonitor;
		this.soilMoistureMax = soilMoistureMax;
		this.soilMoistureMin = soilMoistureMin;
		this.humidityMonitor = humidityMonitor;
		this.humidityMax = humidityMax;
		this.humidityMin = humidityMin;
		this.temperatureMonitor = temperatureMonitor;
		this.temperatureMax = temperatureMax;
		this.temperatureMin = temperatureMin;
	}

	public ObservableList<DataPoint> getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(ObservableList<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}

	public void setPlantSpecies(String plantSpecies) {
		this.plantSpecies = plantSpecies;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setPlantIconFile(String plantIconFile) {
		this.plantIconFile = plantIconFile;
	}

	/**
	 * Returns the path to the plants icon file.
	 * @return	Returns the path to the icon.
	 */
	public String getPlantIconFile() {
		return plantIconFile;
	}

	/**
	 * Returns the plants alias.
	 * @return Returns the alias.
	 */
	public String getPlantAlias() {
		return alias;
	}

	/**
	 * Returns the plant species (if specified).
	 * @return Returns the species.
	 */
	public String getPlantSpecies() {
		return plantSpecies;
	}
}