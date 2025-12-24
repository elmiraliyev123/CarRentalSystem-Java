package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import MainSys.RentalSys;
import MainSys.RentalAgreement;
import Inheritance.Vehicle;

public class RentalPanel extends JPanel {

    private final RentalSys system;
    private Runnable onDataChanged = () -> {};

    private final JComboBox<String> vehicleIdCB = new JComboBox<>();
    private final JTextField customerTF = new JTextField();
    private final JSpinner daysSP = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
    private final JCheckBox insuranceCB = new JCheckBox("Insurance Included");
    private final JLabel costLB = new JLabel("Total Cost: 0.00");

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Agreement ID","Customer","Vehicle ID","Days","Insurance","Total"}, 0
    ) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };

    private final JTable table = new JTable(model);

    public RentalPanel(RentalSys system) {
        this.system = system;
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.add(new JLabel("Vehicle ID"));
        form.add(vehicleIdCB);
        form.add(new JLabel("Customer Name"));
        form.add(customerTF);
        form.add(new JLabel("Duration (Days)"));
        form.add(daysSP);
        form.add(new JLabel(""));
        form.add(insuranceCB);
        form.add(new JLabel(""));
        form.add(costLB);

        JButton calcBtn = new JButton("Calculate");
        JButton rentBtn = new JButton("Create Agreement");

        JPanel btns = new JPanel(new GridLayout(1, 2, 8, 8));
        btns.add(calcBtn);
        btns.add(rentBtn);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.add(form, BorderLayout.CENTER);
        top.add(btns, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        calcBtn.addActionListener(e -> updateCostLabel());

        rentBtn.addActionListener(e -> {
            String vid = (String) vehicleIdCB.getSelectedItem();
            if (vid == null) return;

            String customer = customerTF.getText().trim();
            if (customer.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Customer name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vehicle v = system.searchVehicle(vid);
            if (v == null) return;

            int days = (Integer) daysSP.getValue();
            boolean ins = insuranceCB.isSelected();

            String agreementId = system.nextAgreementId();
            RentalAgreement ra = new RentalAgreement(agreementId, customer, days, ins, v);
            double total = ra.calculateTotalCost();

            system.addRevenue(total);

            model.addRow(new Object[]{
                    agreementId,
                    customer,
                    vid,
                    days,
                    ins,
                    String.format("%.2f", total)
            });

            customerTF.setText("");
            daysSP.setValue(1);
            insuranceCB.setSelected(false);
            updateCostLabel();
            onDataChanged.run();
        });
    }

    public void setOnDataChanged(Runnable r) {
        this.onDataChanged = (r == null) ? () -> {} : r;
    }

    public void refresh() {
        vehicleIdCB.removeAllItems();
        List<Vehicle> vehicles = system.getVehiclesSnapshot();
        for (Vehicle v : vehicles) vehicleIdCB.addItem(v.getId());
        updateCostLabel();
    }

    private void updateCostLabel() {
        String vid = (String) vehicleIdCB.getSelectedItem();
        if (vid == null) {
            costLB.setText("Total Cost: 0.00");
            return;
        }
        Vehicle v = system.searchVehicle(vid);
        if (v == null) {
            costLB.setText("Total Cost: 0.00");
            return;
        }
        int days = (Integer) daysSP.getValue();
        boolean ins = insuranceCB.isSelected();
        RentalAgreement ra = new RentalAgreement("TEMP", "TEMP", days, ins, v);
        costLB.setText("Total Cost: " + String.format("%.2f", ra.calculateTotalCost()));
    }
}
