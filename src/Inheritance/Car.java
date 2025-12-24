package Inheritance;

public class Car extends Vehicle {
    private int seatCount;
    private boolean hasGPS;

    public Car(String id, String brand, String model, double baseDailyRate, int manufacturingYear, int seatCount, boolean hasGPS) {
        super(id, brand, model, baseDailyRate, manufacturingYear);
        this.seatCount = seatCount;
        this.hasGPS = hasGPS;
    }

    public int getSeatCount() { return seatCount; }
    public boolean hasGPS() { return hasGPS; }

    @Override
    public double calculateDailyPrice() {
        double price = baseDailyRate + seatCount * 5.0;
        if (hasGPS) price += 10.0;
        return price;
    }

    @Override
    public String getRentalTerms() {
        return "Car rental requires valid driving license";
    }

    @Override
    public String toString() {
        return "Car{" + super.toString() + ", seats=" + seatCount + ", gps=" + hasGPS + "}";
    }
}
