package ui;

import dao.StationDAO;
import model.Station;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StationsPanel extends JPanel {

    private final StationDAO dao;
    private DefaultTableModel tableModel;
    private JTable table;

    public StationsPanel(StationDAO dao) {
        this.dao = dao;
        buildUI();
        reload();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(16, 16, 16, 16));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Stations Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Code", "Name", "Location", "Status", "Energy (kWh)", "Revenue (₹)"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(22);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");
        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);
        buttons.add(refreshBtn);
        add(buttons, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> onAdd());
        editBtn.addActionListener(e -> onEdit());
        deleteBtn.addActionListener(e -> onDelete());
        refreshBtn.addActionListener(e -> reload());
    }

    public void reload() {
        tableModel.setRowCount(0);
        List<Station> list = dao.getAllStations();
        for (Station s : list) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getCode(),
                    s.getName(),
                    s.getLocation(),
                    s.getStatus(),
                    s.getEnergyKwh(),
                    s.getRevenue()
            });
        }
    }

    private Integer getSelectedId() {
        int row = table.getSelectedRow();
        if (row == -1) return null;
        return (Integer) tableModel.getValueAt(row, 0);
    }

    private void onAdd() {
        AddStationWindow win = new AddStationWindow(dao);
        win.setLocationRelativeTo(this);
        win.setVisible(true);
        reload();
    }

    private void onEdit() {
        Integer id = getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Select a station to edit.");
            return;
        }

        int row = table.getSelectedRow();
        String code = tableModel.getValueAt(row, 1).toString();
        String name = tableModel.getValueAt(row, 2).toString();
        String location = tableModel.getValueAt(row, 3).toString();
        String status = tableModel.getValueAt(row, 4).toString();
        String energyStr = tableModel.getValueAt(row, 5).toString();
        String revenueStr = tableModel.getValueAt(row, 6).toString();

        String newCode = JOptionPane.showInputDialog(this, "Code:", code);
        if (newCode == null) return;
        String newName = JOptionPane.showInputDialog(this, "Name:", name);
        if (newName == null) return;
        String newLocation = JOptionPane.showInputDialog(this, "Location:", location);
        if (newLocation == null) return;
        String newStatus = JOptionPane.showInputDialog(this,
                "Status (AVAILABLE/CHARGING/MAINTENANCE):", status);
        if (newStatus == null) return;
        String newEnergyStr = JOptionPane.showInputDialog(this, "Energy (kWh):", energyStr);
        if (newEnergyStr == null) return;
        String newRevenueStr = JOptionPane.showInputDialog(this, "Revenue (₹):", revenueStr);
        if (newRevenueStr == null) return;

        try {
            int newEnergy = Integer.parseInt(newEnergyStr.trim());
            double newRevenue = Double.parseDouble(newRevenueStr.trim());
            Station updated = new Station(id, newCode.trim(), newName.trim(),
                    newLocation.trim(), newStatus.trim(), newEnergy, newRevenue);
            dao.updateStation(updated);
            reload();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        Integer id = getSelectedId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Select a station to delete.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete selected station?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.deleteStationById(id);
            reload();
        }
    }
}
