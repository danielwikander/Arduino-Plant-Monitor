package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a plant. The object contains the plants information,
 * such as name, icon etc.
 */
public class Plant implements Serializable {

	private static final long serialVersionUID = 2679207460693826435L;
	private ArrayList<DataPoint> dataPoints;
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
	 * Constructor that sets the plants values.
	 *
	 * @param mac
	 * @param plantSpecies
	 * @param alias
	 * @param soilMoistureMonitor
	 */
	public Plant(String mac, String email, String plantSpecies, String alias, boolean soilMoistureMonitor) {
		this.mac = mac;
		this.email = email;
		this.plantSpecies = plantSpecies;
		this.alias = alias;
		this.soilMoistureMonitor = soilMoistureMonitor;
		this.soilMoistureMin = 1;
	}

	/**
	 * Constructor that sets the plants variables if the user chose the advanced option.
	 *
	 * @param plantSpecies The type of plant.
	 */
	public Plant(String mac, String email, String plantSpecies, String alias, boolean soilMoistureMonitor,
				 int soilMoistureMax, int soilMoistureMin, boolean humidityMonitor, int humidityMax, int humidityMin,
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
		this.plantIconFile = "client/images/" + plantSpecies + ".png";
	}

	/**
	 * Method returns the plants DataPoints.
	 *
	 * @return ArrayList containing DataPoints.
	 */
	public ArrayList<DataPoint> getDataPoints() {
		return dataPoints;
	}

	/**
	 * Method sets current DataPoints with given ArrayList of DataPoints
	 *
	 * @param dataPoints Given ArrayList with DataPoints for method to work with.
	 */
	public void setDataPoints(ArrayList<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}

	/**
	 * Method returns this plants MAC-address.
	 *
	 * @return String representing the MAC-address.
	 */
	public String getMac() {
		return mac;
	}


	/**
	 * Method returns this plants email-address.
	 *
	 * @return String representing the email-address.
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Method returns this plants alias.
	 *
	 * @return String representing the alias.
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Returns the path to the plants icon file.
	 *
	 * @return Returns the path to the icon.
	 */
	public String getPlantIconFile() {
		return plantIconFile;
	}

	/**
	 * Returns the plant species (if specified).
	 *
	 * @return Returns the species.
	 */
	public String getPlantSpecies() {
		return plantSpecies;
	}

	/**
	 * Method returns a boolean of if the soil moisture should be monitored.
	 *
	 * @return boolean of the monitored soil moisture.
	 */
	public boolean monitoringSoilMoisture() {
		return soilMoistureMonitor;
	}

	/**
	 * Returns an Integer of the highest allowed soil moisture.
	 *
	 * @return an Integer value of the highest soil moisture allowed.
	 */
	public int getSoilMoistureMax() {
		return soilMoistureMax;
	}

	/**
	 * Returns an Integer of the lowest allowed soil moisture.
	 *
	 * @return an Integer value of the lowest soil moisture allowed.
	 */
	public int getSoilMoistureMin() {
		return soilMoistureMin;
	}

	/**
	 * Method returns a boolean of if the humidity should be monitored.
	 *
	 * @return boolean of the monitored humidity.
	 */
	public boolean monitoringHumidity() {
		return humidityMonitor;
	}

	/**
	 * Returns an Integer of the highest allowed humidity.
	 *
	 * @return an Integer value of the highest humidity allowed.
	 */
	public int getHumidityMax() {
		return humidityMax;
	}

	/**
	 * Returns an Integer of the lowest allowed humidity.
	 *
	 * @return an Integer value of the lowest humidity allowed.
	 */
	public int getHumidityMin() {
		return humidityMin;
	}

	/**
	 * Method returns a boolean of if the temperature should be monitored.
	 *
	 * @return boolean of the monitored temperature.
	 */
	public boolean monitoringTemperature() {
		return temperatureMonitor;
	}

	/**
	 * Returns an Integer of the highest allowed temperature.
	 *
	 * @return an Integer value of the highest temperature allowed.
	 */
	public int getTemperatureMax() {
		return temperatureMax;
	}

	/**
	 * Returns an Integer of the lowest allowed temperature.
	 *
	 * @return an Integer value of the lowest temperature allowed.
	 */
	public int getTemperatureMin() {
		return temperatureMin;
	}
}
