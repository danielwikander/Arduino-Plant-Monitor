package models;

import java.io.Serializable;

public class RemovePlantRequest implements Serializable {
    private static final long serialVersionUID = -4677972606240920044L;
    private Plant plantToRemove;

    public RemovePlantRequest(Plant plantToRemove) {
        this.plantToRemove = plantToRemove;
    }

    public Plant getPlantToRemove() {
        return plantToRemove;
    }
}
