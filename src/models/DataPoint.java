package models;

import java.io.Serializable;

/**
 * Represents a collection of plant data at a specific point in time.
 */
public class DataPoint implements Serializable {

    private String timePoint;
    private String soilMoistureLevel;
    private String airMoistureLevel;
    private String lightLevel;
    private String temperature;

    public String getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

    public String getSoilMoistureLevel() {
        return soilMoistureLevel;
    }

    public void setSoilMoistureLevel(String soilMoistureLevel) {
        this.soilMoistureLevel = soilMoistureLevel;
    }

    public String getAirMoistureLevel() {
        return airMoistureLevel;
    }

    public void setAirMoistureLevel(String airMoistureLevel) {
        this.airMoistureLevel = airMoistureLevel;
    }

    public String getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(String lightLevel) {
        this.lightLevel = lightLevel;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
