package Inheritance;

import java.util.Objects;

import MainSys.MaintenanceRecord;

public abstract class Vehicle implements Rentable {
    protected String id;
    protected String brand;
    protected String model;
    protected double baseDailyRate;
    protected int manufacturingYear;
    protected static int totalVehicleCount = 0;
    protected MaintenanceRecord maintenanceRecord;

    public Vehicle(String id, String brand, String model, double baseDailyRate, int manufacturingYear) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.baseDailyRate = baseDailyRate;
        this.manufacturingYear = manufacturingYear;
        totalVehicleCount++;
    }

    public abstract double calculateDailyPrice();

    public static int getCount() {
        return totalVehicleCount;
    }

    public static void resetCount() {
        totalVehicleCount = 0;
    }

    public void setMaintenanceRecord(MaintenanceRecord maintenanceRecord) {
        this.maintenanceRecord = maintenanceRecord;
    }

    public MaintenanceRecord getMaintenanceRecord() {
        return maintenanceRecord;
    }

    public String getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getBaseDailyRate() { return baseDailyRate; }
    public int getManufacturingYear() { return manufacturingYear; }
    public String getTypeName() { return getClass().getSimpleName(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getTypeName() + "{id='" + id + "', brand='" + brand + "', model='" + model + 
               "', year=" + manufacturingYear + ", daily=" + String.format("%.2f", calculateDailyPrice()) + "}";
    }
}
