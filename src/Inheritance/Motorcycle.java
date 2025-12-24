package Inheritance;

public class Motorcycle extends Vehicle {
    private int engineCC;
    private boolean helmetIncluded;

    public Motorcycle(String id, String brand, String model, double baseDailyRate, int manufacturingYear, int engineCC, boolean helmetIncluded) {
        super(id, brand, model, baseDailyRate, manufacturingYear);
        this.engineCC = engineCC;
        this.helmetIncluded = helmetIncluded;
    }

    public int getEngineCC() { return engineCC; }
    public boolean isHelmetIncluded() { return helmetIncluded; }

    @Override
    public double calculateDailyPrice() {
        double price = baseDailyRate + engineCC * 0.05;
        if (helmetIncluded) price += 5.0;
        return price;
    }

    @Override
    public String getRentalTerms() {
        return "Motorcycle rental requires helmet usage";
    }

    @Override
    public String toString() {
        return "Motorcycle{" + super.toString() + ", cc=" + engineCC + ", helmet=" + helmetIncluded + "}";
    }
}
