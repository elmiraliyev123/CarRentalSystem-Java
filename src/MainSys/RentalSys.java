package MainSys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import Inheritance.Vehicle;

public class RentalSys {
    private ArrayList<Vehicle> vehicleList = new ArrayList<>();
    private HashSet<String> activeIds = new HashSet<>();
    private double totalRevenue;
    private int agreementCounter = 1;

    public void addVehicle(Vehicle v) {
        if (v == null) return;
        if (activeIds.contains(v.getId())) return;
        vehicleList.add(v);
        activeIds.add(v.getId());
    }

    public boolean deleteVehicle(String id) {
        Vehicle v = searchVehicle(id);
        if (v == null) return false;
        vehicleList.remove(v);
        activeIds.remove(id);
        return true;
    }

    public Vehicle searchVehicle(String id) {
        for (Vehicle v : vehicleList) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }

    public void displayAll() {
        for (Vehicle v : vehicleList) System.out.println(v);
    }

    public double calculateTotalRevenue() {
        return totalRevenue;
    }

    public void addRevenue(double amount) {
        totalRevenue += amount;
    }

    public void setRevenue(double revenue) {
        this.totalRevenue = revenue;
    }

    public List<Vehicle> getVehiclesSnapshot() {
        return new ArrayList<>(vehicleList);
    }

    public void clearAll() {
        vehicleList.clear();
        activeIds.clear();
        totalRevenue = 0.0;
        agreementCounter = 1;
        Vehicle.resetCount();
    }

    public String nextAgreementId() {
        return "R" + (agreementCounter++);
    }
}
