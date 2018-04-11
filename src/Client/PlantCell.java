package Client;

/**
 * This class represents a specific cell in a list of plants.
 * The cell contains the name of the plant, the
 */
public class PlantCell {

	private String plantIconFile;
	private String plantAlias;
	private String plantSpecies;

	/**
	 * Sets the plantCells variables.
	 * @param plantIconFile	The image file to use as an icon.
	 * @param plantAlias	The alias for the plant.
	 * @param plantSpecies	The type of plant.
	 */
	public PlantCell(String plantIconFile, String plantAlias, String plantSpecies) {
		this.plantIconFile = plantIconFile;
		this.plantAlias = plantAlias;
		this.plantSpecies = plantSpecies;
	}

	/**
	 * Returns the plants icon file.
	 * @return	Returns the icon.
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