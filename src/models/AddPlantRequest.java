package models;

import java.io.Serializable;

/**
 * A class which holds a plant to be added into the database.
 * @author David.
 */
public class AddPlantRequest implements Serializable {
    private static final long serialVersionUID = 3294167165909921662L;
    private Plant plantToAdd;
    private boolean macAvailable;

	/**
     * Constructor whichs sets the plant object to be added.
     * @param plantToAdd The plant to be added.
     */
    public AddPlantRequest(Plant plantToAdd) {
        this.plantToAdd = plantToAdd;
        this.macAvailable = false;
    }

    /**
     * @return Returns the plant to be added.
     */
    public Plant getPlantToAdd() {
        return plantToAdd;
    }
    
    public boolean isMacAvailable() {
		return macAvailable;
	}

	public void setMacAvailable(boolean macAvailable) {
		this.macAvailable = macAvailable;
	}
}