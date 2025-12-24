package MainSys;

import Inheritance.Vehicle;

public class RentalAgreement {
    private String id;
    private String customerName;
    private int durationDays;
    private boolean insuranceIncluded;
    private Vehicle rentedVehicle;

    public RentalAgreement(String id, String customerName, int durationDays, boolean insuranceIncluded, Vehicle rentedVehicle) {
        this.id = id;
        this.customerName = customerName;
        this.durationDays = durationDays;
        this.insuranceIncluded = insuranceIncluded;
        this.rentedVehicle = rentedVehicle;
    }

    public double calculateTotalCost() {
        double cost = rentedVehicle.calculateDailyPrice() * durationDays;
        if (insuranceIncluded) cost += durationDays * 15.0;
        return cost;
    }

    @Override
    public String toString() {
        return "RentalAgreement{id='" + id + "', customerName='" + customerName + "', durationDays=" + durationDays +
                ", insuranceIncluded=" + insuranceIncluded + ", rentedVehicle=" + rentedVehicle.getId() +
                ", totalCost=" + String.format("%.2f", calculateTotalCost()) + "}";
    }
}
