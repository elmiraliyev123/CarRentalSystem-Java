package GUI;

import javax.swing.*;
import java.awt.*;
import MainSys.RentalSys;

public class MainFrame extends JFrame {

    private final RentalSys system = new RentalSys();

    private final VehiclePanel vehiclePanel = new VehiclePanel(system);
    private final RentalPanel rentalPanel = new RentalPanel(system);
    private final MaintenancePanel maintenancePanel = new MaintenancePanel(system);
    private final ReportPanel reportPanel = new ReportPanel(system);

    public MainFrame() {
        setTitle("Vehicle Rental System");
        setSize(980, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setJMenuBar(buildMenuBar());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Vehicles", vehiclePanel);
        tabs.addTab("Rent", rentalPanel);
        tabs.addTab("Maintenance", maintenancePanel);
        tabs.addTab("Reports", reportPanel);

        add(tabs, BorderLayout.CENTER);

        vehiclePanel.setOnDataChanged(this::refreshAll);
        rentalPanel.setOnDataChanged(this::refreshAll);
        maintenancePanel.setOnDataChanged(this::refreshAll);

        refreshAll();
    }

    private JMenuBar buildMenuBar() {
        JMenuBar mb = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");

        load.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    MainSys.DataStore.load(system, fc.getSelectedFile().getAbsolutePath());
                    refreshAll();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        save.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    MainSys.DataStore.save(system, fc.getSelectedFile().getAbsolutePath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exit.addActionListener(e -> dispose());

        file.add(load);
        file.add(save);
        file.addSeparator();
        file.add(exit);

        JMenu view = new JMenu("View");
        JMenuItem details = new JMenuItem("Vehicle Details");
        details.addActionListener(e -> new VehicleDetailsFrame(system).setVisible(true));
        view.add(details);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> new AboutFrame().setVisible(true));
        help.add(about);

        mb.add(file);
        mb.add(view);
        mb.add(help);

        return mb;
    }

    private void refreshAll() {
        vehiclePanel.refresh();
        rentalPanel.refresh();
        maintenancePanel.refresh();
        reportPanel.refresh();
    }
}
