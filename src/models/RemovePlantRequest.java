package models;

import java.io.Serializable;

/**
 * A class which holds a plant to be removed from the database.
 * @author Daniel.
 */
public class RemovePlantRequest implements Serializable {
    private static final long serialVersionUID = -4677972606240920044L;
    private Plant plantToRemove;

    /**
     * Constructor whichs sets the plant object to be removed.
     * @param plantToRemove The plant to be removed.
     */
    public RemovePlantRequest(Plant plantToRemove) {
        this.plantToRemove = plantToRemove;
    }

    /**
     * @return Returns the plant to be removed.
     */
    public Plant getPlantToRemove() {
        return plantToRemove;
    }
}
