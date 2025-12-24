package GUI;
import MainSys.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import MainSys.RentalSys;
import Inheritance.Vehicle;

public class ReportPanel extends JPanel {

    private final RentalSys system;

    private final JLabel vehicleCountLB = new JLabel("Vehicles: 0");
    private final JLabel revenueLB = new JLabel("Revenue: 0.00");
    private final JTextArea topTA = new JTextArea();

    public ReportPanel(RentalSys system) {
        this.system = system;
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new GridLayout(1, 2, 10, 10));
        top.add(vehicleCountLB);
        top.add(revenueLB);

        topTA.setEditable(false);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(topTA), BorderLayout.CENTER);
    }

    public void refresh() {
        vehicleCountLB.setText("Vehicles: " + Vehicle.getCount());
        revenueLB.setText("Revenue: " + String.format("%.2f", system.calculateTotalRevenue()));

        List<Vehicle> list = new ArrayList<>(system.getVehiclesSnapshot());
        list.sort(Comparator.comparingDouble(Vehicle::calculateDailyPrice).reversed());

        StringBuilder sb = new StringBuilder();
        sb.append("Top 5 Most Expensive Daily Price:\n\n");
        for (int i = 0; i < Math.min(5, list.size()); i++) {
            Vehicle v = list.get(i);
            sb.append(i + 1).append(") ")
              .append(v.getTypeName()).append(" ")
              .append(v.getId()).append(" - ")
              .append(String.format("%.2f", v.calculateDailyPrice()))
              .append("\n");
        }
        topTA.setText(sb.toString());
    }
}
