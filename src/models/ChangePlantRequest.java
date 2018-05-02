package models;

public class ChangePlantRequest {
    private Plant plantToChange;

    public ChangePlantRequest(Plant plantToChange) {
        this.plantToChange = plantToChange;
    }

    public Plant getPlantToChange() {
        return plantToChange;
    }
}
