package models;

public class RemoveRequest {
    private Plant plantToRemove;

    public RemoveRequest(Plant plantToRemove) {
        this.plantToRemove = plantToRemove;
    }

    public Plant getPlantToRemove() {
        return plantToRemove;
    }
}
