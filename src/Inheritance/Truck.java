package Inheritance;

public class Truck extends Vehicle {
    private double loadCapacityTonnes;
    private boolean hasTrailer;

    public Truck(String id, String brand, String model, double baseDailyRate, int manufacturingYear, double loadCapacityTonnes, boolean hasTrailer) {
        super(id, brand, model, baseDailyRate, manufacturingYear);
        this.loadCapacityTonnes = loadCapacityTonnes;
        this.hasTrailer = hasTrailer;
    }

    public double getLoadCapacityTonnes() { return loadCapacityTonnes; }
    public boolean hasTrailer() { return hasTrailer; }

    @Override
    public double calculateDailyPrice() {
        double price = baseDailyRate + loadCapacityTonnes * 20.0;
        if (hasTrailer) price += 30.0;
        return price;
    }

    @Override
    public String getRentalTerms() {
        return "Truck rental requires commercial driving license";
    }

    @Override
    public String toString() {
        return "Truck{" + super.toString() + ", capacityTonnes=" + loadCapacityTonnes + ", trailer=" + hasTrailer + "}";
    }
}
