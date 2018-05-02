package models;

public class AddPlantRequest {
    private Plant plantToAdd;

    public AddPlantRequest(Plant plantToAdd) {
        this.plantToAdd = plantToAdd;
    }

    public Plant getPlantToAdd() {
        return plantToAdd;
    }
}
