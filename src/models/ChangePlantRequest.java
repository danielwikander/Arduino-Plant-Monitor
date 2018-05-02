package models;

import java.io.Serializable;

public class ChangePlantRequest implements Serializable {
    private static final long serialVersionUID = -4929315440419583481L;
    private Plant plantToChange;

    public ChangePlantRequest(Plant plantToChange) {
        this.plantToChange = plantToChange;
    }

    public Plant getPlantToChange() {
        return plantToChange;
    }
}
