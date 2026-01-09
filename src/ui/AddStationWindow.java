package ui;

import dao.StationDAO;
import model.Station;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddStationWindow extends JFrame {

    private final StationDAO dao;

    private JTextField codeField;
    private JTextField nameField;
    private JTextField locationField;
    private JComboBox<String> statusBox;
    private JSpinner energySpinner;
    private JSpinner revenueSpinner;

    public AddStationWindow(StationDAO dao) {
        this.dao = dao;
        initUI();
    }

    private void initUI() {
        setTitle("Add New Station");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);

        Color bg = new Color(246, 248, 252);
        Color accent = new Color(24, 175, 96);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(bg);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(root);

        JLabel title = new JLabel("Add New Charging Station");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(accent);
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        codeField = new JTextField();
        nameField = new JTextField();
        locationField = new JTextField();
        statusBox = new JComboBox<>(new String[]{"AVAILABLE", "CHARGING", "MAINTENANCE"});
        energySpinner = new JSpinner(new SpinnerNumberModel(50, 0, 5000, 10));
        revenueSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100000.0, 10.0));

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Code"), gbc);
        gbc.gridx = 1;
        form.add(codeField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Name"), gbc);
        gbc.gridx = 1;
        form.add(nameField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Location"), gbc);
        gbc.gridx = 1;
        form.add(locationField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Status"), gbc);
        gbc.gridx = 1;
        form.add(statusBox, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Energy (kWh)"), gbc);
        gbc.gridx = 1;
        form.add(energySpinner, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Revenue (₹)"), gbc);
        gbc.gridx = 1;
        form.add(revenueSpinner, gbc);
        row++;

        JButton saveBtn = new JButton("Save Station");
        saveBtn.setBackground(accent);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        form.add(saveBtn, gbc);

        root.add(form, BorderLayout.CENTER);

        saveBtn.addActionListener(e -> onSave());
        getRootPane().setDefaultButton(saveBtn);
    }

    private void onSave() {
        try {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            String status = (String) statusBox.getSelectedItem();
            int energy = (Integer) energySpinner.getValue();
            double revenue = (Double) revenueSpinner.getValue();

            if (code.isEmpty() || name.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Code, Name, Location are required.");
                return;
            }

            Station station = new Station(0, code, name, location, status, energy, revenue);
            dao.addStation(station);

            JOptionPane.showMessageDialog(this, "Station added successfully.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
