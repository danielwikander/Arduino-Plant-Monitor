package models;

import java.io.Serializable;

public class AddPlantRequest implements Serializable {
    private static final long serialVersionUID = 3294167165909921662L;
    private Plant plantToAdd;

    public AddPlantRequest(Plant plantToAdd) {
        this.plantToAdd = plantToAdd;
    }

    public Plant getPlantToAdd() {
        return plantToAdd;
    }
}
