package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import MainSys.RentalSys;
import MainSys.MaintenanceRecord;
import Inheritance.Vehicle;

public class MaintenancePanel extends JPanel {

    private final RentalSys system;
    private Runnable onDataChanged = () -> {};

    private final JComboBox<String> vehicleIdCB = new JComboBox<>();
    private final JTextField recIdTF = new JTextField();
    private final JTextField dateTF = new JTextField();
    private final JTextField descTF = new JTextField();
    private final JTextArea outTA = new JTextArea();

    public MaintenancePanel(RentalSys system) {
        this.system = system;
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.add(new JLabel("Vehicle ID"));
        form.add(vehicleIdCB);
        form.add(new JLabel("Record ID"));
        form.add(recIdTF);
        form.add(new JLabel("Date"));
        form.add(dateTF);
        form.add(new JLabel("Description"));
        form.add(descTF);

        JButton setBtn = new JButton("Set Record");
        JButton showBtn = new JButton("Show Record");

        JPanel btns = new JPanel(new GridLayout(1, 2, 8, 8));
        btns.add(setBtn);
        btns.add(showBtn);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.add(form, BorderLayout.CENTER);
        top.add(btns, BorderLayout.SOUTH);

        outTA.setEditable(false);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(outTA), BorderLayout.CENTER);

        setBtn.addActionListener(e -> {
            String vid = (String) vehicleIdCB.getSelectedItem();
            if (vid == null) return;
            Vehicle v = system.searchVehicle(vid);
            if (v == null) return;

            String rid = recIdTF.getText().trim();
            String dt = dateTF.getText().trim();
            String ds = descTF.getText().trim();

            if (rid.isEmpty() || dt.isEmpty() || ds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            v.setMaintenanceRecord(new MaintenanceRecord(rid, dt, ds));
            outTA.setText("Updated:\n" + v.getMaintenanceRecord());
            onDataChanged.run();
        });

        showBtn.addActionListener(e -> {
            String vid = (String) vehicleIdCB.getSelectedItem();
            if (vid == null) return;
            Vehicle v = system.searchVehicle(vid);
            if (v == null) return;

            MaintenanceRecord mr = v.getMaintenanceRecord();
            outTA.setText(mr == null ? "No maintenance record." : mr.toString());
        });
    }

    public void setOnDataChanged(Runnable r) {
        this.onDataChanged = (r == null) ? () -> {} : r;
    }

    public void refresh() {
        vehicleIdCB.removeAllItems();
        List<Vehicle> vehicles = system.getVehiclesSnapshot();
        for (Vehicle v : vehicles) vehicleIdCB.addItem(v.getId());
    }
}
