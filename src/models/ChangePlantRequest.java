package models;

import java.io.Serializable;

/**
 * A class which holds a plant to be changed in the database.
 * @author Anton.
 */
public class ChangePlantRequest implements Serializable {
    private static final long serialVersionUID = -4929315440419583481L;
    private Plant plantToChange;

    /**
     * Constructor whichs sets the plant object to be changed.
     * @param plantToChange The plant to be changed.
     */
    public ChangePlantRequest(Plant plantToChange) {
        this.plantToChange = plantToChange;
    }

    /**
     * @return Returns the plant to be changed.
     */
    public Plant getPlantToChange() {
        return plantToChange;
    }
}