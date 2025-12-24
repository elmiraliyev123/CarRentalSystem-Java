package MainSys;

import java.io.*;
import java.util.List;
import Inheritance.Vehicle;
import Inheritance.Car;
import Inheritance.Truck;
import Inheritance.Motorcycle;

public class DataStore {

    public static void save(RentalSys system, String path) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            List<Vehicle> list = system.getVehiclesSnapshot();
            for (Vehicle v : list) pw.println(encodeVehicle(v));
            pw.println("REVENUE|" + system.calculateTotalRevenue());
        }
    }

    public static void load(RentalSys system, String path) throws IOException {
        system.clearAll();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("REVENUE|")) {
                    String[] p = line.split("\\|", -1);
                    if (p.length > 1) system.setRevenue(Double.parseDouble(p[1]));
                    continue;
                }
                Vehicle v = decodeVehicle(line);
                if (v != null) system.addVehicle(v);
            }
        }
    }

    private static String encodeVehicle(Vehicle v) {
        String mr = "";
        if (v.getMaintenanceRecord() != null) {
            MaintenanceRecord m = v.getMaintenanceRecord();
            mr = m.getId().replace("|","/") + "," + m.getDate().replace("|","/") + "," + m.getDescription().replace("|","/");
        }

        if (v instanceof Car c) {
            return "CAR|" + c.getId() + "|" + c.getBrand() + "|" + c.getModel() + "|" + c.getBaseDailyRate() + "|" + c.getManufacturingYear() +
                    "|" + c.getSeatCount() + "|" + c.hasGPS() + "|" + mr;
        }
        if (v instanceof Truck t) {
            return "TRUCK|" + t.getId() + "|" + t.getBrand() + "|" + t.getModel() + "|" + t.getBaseDailyRate() + "|" + t.getManufacturingYear() +
                    "|" + t.getLoadCapacityTonnes() + "|" + t.hasTrailer() + "|" + mr;
        }
        if (v instanceof Motorcycle m) {
            return "MOTO|" + m.getId() + "|" + m.getBrand() + "|" + m.getModel() + "|" + m.getBaseDailyRate() + "|" + m.getManufacturingYear() +
                    "|" + m.getEngineCC() + "|" + m.isHelmetIncluded() + "|" + mr;
        }
        return "";
    }

    private static Vehicle decodeVehicle(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 8) return null;

        String type = p[0];
        String id = p[1];
        String brand = p[2];
        String model = p[3];
        double rate = Double.parseDouble(p[4]);
        int year = Integer.parseInt(p[5]);

        Vehicle v = null;

        if ("CAR".equals(type)) {
            int seats = Integer.parseInt(p[6]);
            boolean gps = Boolean.parseBoolean(p[7]);
            v = new Car(id, brand, model, rate, year, seats, gps);
        } else if ("TRUCK".equals(type)) {
            double cap = Double.parseDouble(p[6]);
            boolean tr = Boolean.parseBoolean(p[7]);
            v = new Truck(id, brand, model, rate, year, cap, tr);
        } else if ("MOTO".equals(type)) {
            int cc = Integer.parseInt(p[6]);
            boolean helm = Boolean.parseBoolean(p[7]);
            v = new Motorcycle(id, brand, model, rate, year, cc, helm);
        }

        if (v != null && p.length >= 9 && !p[8].isBlank()) {
            String[] mr = p[8].split(",", -1);
            if (mr.length >= 3) v.setMaintenanceRecord(new MaintenanceRecord(mr[0], mr[1], mr[2]));
        }

        return v;
    }
}
