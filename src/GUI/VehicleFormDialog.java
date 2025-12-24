package GUI;

import javax.swing.*;
import java.awt.*;
import MainSys.RentalSys;
import Inheritance.Vehicle;
import Inheritance.Car;
import Inheritance.Truck;
import Inheritance.Motorcycle;

public class VehicleFormDialog extends JDialog {

    private Vehicle result;

    private final JComboBox<String> typeCB = new JComboBox<>(new String[]{"Car","Truck","Motorcycle"});
    private final JTextField idTF = new JTextField();
    private final JTextField brandTF = new JTextField();
    private final JTextField modelTF = new JTextField();
    private final JTextField rateTF = new JTextField();
    private final JTextField yearTF = new JTextField();

    private final JLabel extra1LB = new JLabel("Seats / Capacity / CC");
    private final JTextField extra1TF = new JTextField();

    private final JLabel extra2LB = new JLabel("GPS / Trailer / Helmet");
    private final JCheckBox extra2CB = new JCheckBox("Yes");

    public VehicleFormDialog(Window owner, Vehicle edit) {
        super(owner, "Vehicle Form", ModalityType.APPLICATION_MODAL);
        setSize(520, 320);
        setLocationRelativeTo(owner);

        JPanel form = new JPanel(new GridLayout(8, 2, 8, 8));
        form.add(new JLabel("Type"));
        form.add(typeCB);

        form.add(new JLabel("ID"));
        form.add(idTF);

        form.add(new JLabel("Brand"));
        form.add(brandTF);

        form.add(new JLabel("Model"));
        form.add(modelTF);

        form.add(new JLabel("Base Daily Rate"));
        form.add(rateTF);

        form.add(new JLabel("Manufacturing Year"));
        form.add(yearTF);

        form.add(extra1LB);
        form.add(extra1TF);

        form.add(extra2LB);
        form.add(extra2CB);

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        JPanel btns = new JPanel(new GridLayout(1, 2, 8, 8));
        btns.add(ok);
        btns.add(cancel);

        setLayout(new BorderLayout(10, 10));
        add(form, BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);

        typeCB.addActionListener(e -> updateExtraLabels());
        updateExtraLabels();

        if (edit != null) {
            idTF.setText(edit.getId());
            brandTF.setText(edit.getBrand());
            modelTF.setText(edit.getModel());
            rateTF.setText(String.valueOf(edit.getBaseDailyRate()));
            yearTF.setText(String.valueOf(edit.getManufacturingYear()));
            idTF.setEditable(false);

            if (edit instanceof Car c) {
                typeCB.setSelectedItem("Car");
                extra1TF.setText(String.valueOf(c.getSeatCount()));
                extra2CB.setSelected(c.hasGPS());
            } else if (edit instanceof Truck t) {
                typeCB.setSelectedItem("Truck");
                extra1TF.setText(String.valueOf(t.getLoadCapacityTonnes()));
                extra2CB.setSelected(t.hasTrailer());
            } else if (edit instanceof Motorcycle m) {
                typeCB.setSelectedItem("Motorcycle");
                extra1TF.setText(String.valueOf(m.getEngineCC()));
                extra2CB.setSelected(m.isHelmetIncluded());
            }
            typeCB.setEnabled(false);
        }

        ok.addActionListener(e -> {
            try {
                String type = (String) typeCB.getSelectedItem();
                String id = idTF.getText().trim();
                String br = brandTF.getText().trim();
                String mo = modelTF.getText().trim();
                double rate = Double.parseDouble(rateTF.getText().trim());
                int year = Integer.parseInt(yearTF.getText().trim());

                if (id.isEmpty() || br.isEmpty() || mo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID/Brand/Model required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if ("Car".equals(type)) {
                    int seats = Integer.parseInt(extra1TF.getText().trim());
                    boolean gps = extra2CB.isSelected();
                    result = new Car(id, br, mo, rate, year, seats, gps);
                } else if ("Truck".equals(type)) {
                    double cap = Double.parseDouble(extra1TF.getText().trim());
                    boolean tr = extra2CB.isSelected();
                    result = new Truck(id, br, mo, rate, year, cap, tr);
                } else {
                    int cc = Integer.parseInt(extra1TF.getText().trim());
                    boolean helm = extra2CB.isSelected();
                    result = new Motorcycle(id, br, mo, rate, year, cc, helm);
                }

                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancel.addActionListener(e -> {
            result = null;
            dispose();
        });
    }

    private void updateExtraLabels() {
        String type = (String) typeCB.getSelectedItem();
        if ("Car".equals(type)) {
            extra1LB.setText("Seat Count");
            extra2LB.setText("Has GPS");
        } else if ("Truck".equals(type)) {
            extra1LB.setText("Load Capacity (Tonnes)");
            extra2LB.setText("Has Trailer");
        } else {
            extra1LB.setText("Engine CC");
            extra2LB.setText("Helmet Included");
        }
    }

    public Vehicle getResult() {
        return result;
    }
}
