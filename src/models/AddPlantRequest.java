package models;

import java.io.Serializable;

/**
 * A class which holds a plant to be added into the database.
 * @author David.
 */
public class AddPlantRequest implements Serializable {
    private static final long serialVersionUID = 3294167165909921662L;
    private Plant plantToAdd;

    /**
     * Constructor whichs sets the plant object to be added.
     * @param plantToAdd The plant to be added.
     */
    public AddPlantRequest(Plant plantToAdd) {
        this.plantToAdd = plantToAdd;
    }

    /**
     * @return Returns the plant to be added.
     */
    public Plant getPlantToAdd() {
        return plantToAdd;
    }
}