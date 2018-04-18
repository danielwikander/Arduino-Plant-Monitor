package models;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

/**
 * This class represents a plant.
 * The object contains the plants information, such as name, icon etc.
 * //TODO: Plant should contain values for notifications.
 * //TODO: Plant should contain HashMap of DataPoints.
 */
public class Plant {

	private String plantIconFile;
	private String plantAlias;
	private String plantSpecies;
	private ObservableList<DataPoint> dataPoints;

	/**
	 * Constructor that sets the plants variables.
	 * @param plantIconFile	The image file to use as an icon.
	 * @param plantAlias	The alias for the plant.
	 * @param plantSpecies	The type of plant.
	 */
	public Plant(String plantIconFile, String plantAlias, String plantSpecies) {
		this.plantIconFile = plantIconFile;
		this.plantAlias = plantAlias;
		this.plantSpecies = plantSpecies;
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
		return plantAlias;
	}

	/**
	 * Returns the plant species (if specified).
	 * @return Returns the species.
	 */
	public String getPlantSpecies() {
		return plantSpecies;
	}
}