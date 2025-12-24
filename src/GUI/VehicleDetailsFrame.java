package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import MainSys.MaintenanceRecord;
import MainSys.RentalSys;
import Inheritance.Vehicle;

public class VehicleDetailsFrame extends JFrame {

    private final RentalSys system;

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Type","ID","Brand","Model","Year","Base Rate","Daily Price","Terms","Maint. ID","Maint. Date","Maint. Desc"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };

    private final JTable table = new JTable(model);
    private final JLabel summaryLabel = new JLabel();

    public VehicleDetailsFrame(RentalSys system) {
        this.system = system;

        setTitle("Vehicle Details");
        setSize(960, 520);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(8, 8));

        JPanel top = new JPanel(new BorderLayout(8, 8));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refresh());
        summaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        top.add(refreshBtn, BorderLayout.WEST);
        top.add(summaryLabel, BorderLayout.CENTER);

        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refresh();
    }

    public final void refresh() {
        model.setRowCount(0);
        List<Vehicle> vehicles = system.getVehiclesSnapshot();
        for (Vehicle v : vehicles) {
            MaintenanceRecord mr = v.getMaintenanceRecord();
            model.addRow(new Object[]{
                    v.getTypeName(),
                    v.getId(),
                    v.getBrand(),
                    v.getModel(),
                    v.getManufacturingYear(),
                    String.format("%.2f", v.getBaseDailyRate()),
                    String.format("%.2f", v.calculateDailyPrice()),
                    v.getRentalTerms(),
                    (mr == null) ? "" : mr.getId(),
                    (mr == null) ? "" : mr.getDate(),
                    (mr == null) ? "" : mr.getDescription()
            });
        }

        summaryLabel.setText("Vehicles: " + vehicles.size() +
                " | Total revenue: " + String.format("%.2f", system.calculateTotalRevenue()));
    }
}
