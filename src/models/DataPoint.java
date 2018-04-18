package models;

import java.io.Serializable;

/**
 * Represents a collection of plant data at a specific point in time.
 */
public class DataPoint implements Serializable {

    private String timeStamp;
    private int soilMoistureLevel;
    private int humidity;
    private int lightLevel;
    private int temperature;

    public DataPoint (String timeStamp, int soilMoistureLevel, int humidity, int temperature, int lightLevel) {
        this.timeStamp = timeStamp;
        this.soilMoistureLevel = soilMoistureLevel;
        this.humidity = humidity;
        this.lightLevel = lightLevel;
        this.temperature = temperature;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getSoilMoistureLevel() {
        return soilMoistureLevel;
    }

    public void setSoilMoistureLevel(int soilMoistureLevel) {
        this.soilMoistureLevel = soilMoistureLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
