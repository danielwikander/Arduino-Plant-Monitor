package models;

import java.io.Serializable;

/**
 * Represents a collection of plant data at a specific point in time.
 */
public class DataPoint implements Serializable {

	private static final long serialVersionUID = 5914959873620340574L;
	private String timeStamp;
	private int soilMoistureLevel;
	private int humidity;
	private int lightLevel;
	private int temperature;

	/**
	 * Constructor creates a DataPoint containing a time-stamp and data for the
	 * plants humidity level, soil moisture level temperature and light-level.
	 * 
	 * @param timeStamp
	 *            Time and date of the DataPoint.
	 * @param soilMoistureLevel
	 *            Data representing the soil moisture level.
	 * @param humidity
	 *            Data representing the humidity level.
	 * @param temperature
	 *            Data representing the temperature.
	 * @param lightLevel
	 *            Data representing the light level.
	 */
	public DataPoint(String timeStamp, int soilMoistureLevel, int humidity, int temperature, int lightLevel) {
		this.timeStamp = timeStamp;
		this.soilMoistureLevel = soilMoistureLevel;
		this.humidity = humidity;
		this.lightLevel = lightLevel;
		this.temperature = temperature;
	}

	/**
	 * Returns the string of the time stamp.
	 * 
	 * @return String of the time and date.
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Returns the value of the soil moisture level.
	 * 
	 * @return Integer value of the soil moisture.
	 */
	public int getSoilMoistureLevel() {
		return soilMoistureLevel;
	}

	/**
	 * Returns the value of the humidity level.
	 * 
	 * @return Integer value of the humidity.
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * Returns the value of the light level.
	 * 
	 * @return Integer value of the light level.
	 */
	public int getLightLevel() {
		return lightLevel;
	}

	/**
	 * Returns the value of the temperature
	 * 
	 * @return Integer value of the temperature.
	 */
	public int getTemperature() {
		return temperature;
	}

}
