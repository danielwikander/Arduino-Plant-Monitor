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
	 * @param mac
	 * @param email
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
	}

	/**
	 * Constructor that sets the plants variables if the user chose the advanced option.
	 * @param plantSpecies
	 *            The type of plant.
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
	 * @param dataPoints
	 *            Given ArrayList with DataPoints for method to work with.
	 */
	public void setDataPoints(ArrayList<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}

	/**
	 * Method sets the plantspecies String with given String .
	 * 
	 * @param plantSpecies
	 *            Given String for method to replace with.
	 */
	public void setPlantSpecies(String plantSpecies) {
		this.plantSpecies = plantSpecies;
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
	 * Method sets the mac String with given String.
	 * 
	 * @param mac
	 *            Given String for method to replace with.
	 */
	public void setMac(String mac) {
		this.mac = mac;
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
	 * Method sets the email String with given String.
	 * 
	 * @param email
	 *            Given String for method to replace with.
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Method sets the alias String with given String.
	 * 
	 * @param alias
	 *            Given String for method to replace with.
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Method sets the plantIconFile String with given String.
	 * 
	 * @param plantIconFile
	 *            Given String for method to replace with.
	 */
	public void setPlantIconFile(String plantIconFile) {
		this.plantIconFile = plantIconFile;
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
	 * Returns the plants alias.
	 * 
	 * @return Returns the alias.
	 */
	public String getPlantAlias() {
		return alias;
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
	 * Method sets the the boolean of if the soil moisture should be monitored.
	 * 
	 * @param soilMoistureMonitor
	 *            the boolean-flag to set.
	 */
	public void setSoilMoistureMonitor(boolean soilMoistureMonitor) {
		this.soilMoistureMonitor = soilMoistureMonitor;
	}
	
	/**
	 * Returns an Integer of the highest allowed soil moisture.
	 * @return an Integer value of the highest soil moisture allowed.
	 */
	public int getSoilMoistureMax() {
		return soilMoistureMax;
	}
	
	/**
	 * Set the highest allowed soil moisture level.
	 * @param soilMoistureMax Given Integer to be set.
	 */
	public void setSoilMoistureMax(int soilMoistureMax) {
		this.soilMoistureMax = soilMoistureMax;
	}

	/**
	 * Returns an Integer of the lowest allowed soil moisture.
	 * @return an Integer value of the lowest soil moisture allowed.
	 */
	public int getSoilMoistureMin() {
		return soilMoistureMin;
	}

	/**
	 * Set the lowest allowed soil moisture level.
	 * @param soilMoistureMin Given Integer to be set.
	 */
	public void setSoilMoistureMin(int soilMoistureMin) {
		this.soilMoistureMin = soilMoistureMin;
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
	 * Method sets the the boolean of if the humidity should be monitored.
	 * 
	 * @param humidityMonitor
	 *            the boolean-flag to set.
	 */
	public void setHumidityMonitor(boolean humidityMonitor) {
		this.humidityMonitor = humidityMonitor;
	}

	/**
	 * Returns an Integer of the highest allowed humidity.
	 * @return an Integer value of the highest humidity allowed.
	 */
	public int getHumidityMax() {
		return humidityMax;
	}

	/**
	 * Set the highest allowed humidity level.
	 * @param humidityMax Given Integer to be set.
	 */
	public void setHumidityMax(int humidityMax) {
		this.humidityMax = humidityMax;
	}

	/**
	 * Returns an Integer of the lowest allowed humidity.
	 * @return an Integer value of the lowest humidity allowed.
	 */
	public int getHumidityMin() {
		return humidityMin;
	}

	/**
	 * Set the lowest allowed humidity level.
	 * @param humidityMin Given Integer to be set.
	 */
	public void setHumidityMin(int humidityMin) {
		this.humidityMin = humidityMin;
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
	 * Method returns a boolean of if the temperature should be monitored.
	 * 
	 * @return boolean of the monitored temperature.
	 */
	public void setTemperatureMonitor(boolean temperatureMonitor) {
		this.temperatureMonitor = temperatureMonitor;
	}

	/**
	 * Returns an Integer of the highest allowed temperature.
	 * @return an Integer value of the highest temperature allowed.
	 */
	public int getTemperatureMax() {
		return temperatureMax;
	}
	
	/**
	 * Set the highest allowed temperature level.
	 * @param temperatureMax Given Integer to be set.
	 */
	public void setTemperatureMax(int temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	/**
	 * Returns an Integer of the lowest allowed temperature.
	 * @return an Integer value of the lowest temperature allowed.
	 */
	public int getTemperatureMin() {
		return temperatureMin;
	}

	/**
	 * Set the lowest allowed temperature level.
	 * @param temperatureMin Given Integer to be set.
	 */
	public void setTemperatureMin(int temperatureMin) {
		this.temperatureMin = temperatureMin;
	}
}