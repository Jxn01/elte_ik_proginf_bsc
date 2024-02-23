package game.utils;

public class Vehicle {
    private int modelId;
    private String license;
    private int color1;
    private int color2;

    public Vehicle() {
    }

    public Vehicle(int modelId, String license, int color1, int color2) {
        this.modelId = modelId;
        this.license = license;
        this.color1 = color1;
        this.color2 = color2;
    }

    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
