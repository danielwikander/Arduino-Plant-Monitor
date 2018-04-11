package Client;

public class PlantCell {

	private String plantFile;
	private String plantAlias;
	private String plantName;
	
	public PlantCell(String plantFile, String plantAlias, String plantName) {
		this.plantFile = plantFile;
		this.plantAlias = plantAlias;
		this.plantName = plantName;
	}
	
	public String getPlantFile() {
		return plantFile;
	}
	
	public String getPlantAlias() {
		return plantAlias;
	}
	
	public String getPlantName() {
		return plantName;
	}
}