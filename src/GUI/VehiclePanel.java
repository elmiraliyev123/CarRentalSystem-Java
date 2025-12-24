package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
import MainSys.RentalSys;
import Inheritance.Vehicle;

public class VehiclePanel extends JPanel {

    private final RentalSys system;
    private Runnable onDataChanged = () -> {};

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Type","ID","Brand","Model","Year","Daily Price","Terms"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };

    private final JTable table = new JTable(model);
    private final JTextField searchTF = new JTextField();

    public VehiclePanel(RentalSys system) {
        this.system = system;
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new BorderLayout(8, 8));
        JPanel left = new JPanel(new GridLayout(1, 3, 8, 8));
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        left.add(addBtn);
        left.add(editBtn);
        left.add(deleteBtn);

        JPanel right = new JPanel(new BorderLayout(8, 8));
        JButton searchBtn = new JButton("Search ID");
        JButton clearBtn = new JButton("Clear");

        right.add(searchTF, BorderLayout.CENTER);
        JPanel rBtns = new JPanel(new GridLayout(1, 2, 8, 8));
        rBtns.add(searchBtn);
        rBtns.add(clearBtn);
        right.add(rBtns, BorderLayout.EAST);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            VehicleFormDialog dlg = new VehicleFormDialog(SwingUtilities.getWindowAncestor(this), null);
            dlg.setVisible(true);
            Vehicle v = dlg.getResult();
            if (v != null) {
                if (system.searchVehicle(v.getId()) != null) {
                    JOptionPane.showMessageDialog(this, "ID already exists.", "Add Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                system.addVehicle(v);
                onDataChanged.run();
            }
        });

        editBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) return;
            String id = (String) model.getValueAt(r, 1);
            Vehicle v = system.searchVehicle(id);
            if (v == null) return;

            VehicleFormDialog dlg = new VehicleFormDialog(SwingUtilities.getWindowAncestor(this), v);
            dlg.setVisible(true);
            Vehicle edited = dlg.getResult();
            if (edited != null) {
                system.deleteVehicle(id);
                system.addVehicle(edited);
                onDataChanged.run();
            }
        });

        deleteBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) return;
            String id = (String) model.getValueAt(r, 1);
            if (system.deleteVehicle(id)) onDataChanged.run();
        });

        searchBtn.addActionListener(e -> {
            String id = searchTF.getText().trim();
            Vehicle v = system.searchVehicle(id);
            model.setRowCount(0);
            if (v != null) addRow(v);
        });

        clearBtn.addActionListener(e -> {
            searchTF.setText("");
            refresh();
        });
    }

    public void setOnDataChanged(Runnable r) {
        this.onDataChanged = (r == null) ? () -> {} : r;
    }

    public void refresh() {
        model.setRowCount(0);
        List<Vehicle> vehicles = system.getVehiclesSnapshot();
        for (Vehicle v : vehicles) addRow(v);
    }

    private void addRow(Vehicle v) {
        model.addRow(new Object[]{
                v.getTypeName(),
                v.getId(),
                v.getBrand(),
                v.getModel(),
                v.getManufacturingYear(),
                String.format("%.2f", v.calculateDailyPrice()),
                v.getRentalTerms()
        });
    }
}
