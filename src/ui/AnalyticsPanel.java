package ui;

import dao.StationDAO;
import model.Station;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AnalyticsPanel extends JPanel {

    private final StationDAO dao;

    private JLabel totalStationsLabel;
    private JLabel totalEnergyLabel;
    private JLabel totalRevenueLabel;
    private JLabel availableLabel;
    private JLabel chargingLabel;
    private JLabel maintenanceLabel;
    private JLabel avgRevenueLabel;
    private JLabel topStationLabel;

    public AnalyticsPanel(StationDAO dao) {
        this.dao = dao;
        buildUI();
        loadData();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(16, 16, 16, 16));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Analytics Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 2, 12, 10));
        grid.setOpaque(false);

        totalStationsLabel = new JLabel();
        totalEnergyLabel = new JLabel();
        totalRevenueLabel = new JLabel();
        availableLabel = new JLabel();
        chargingLabel = new JLabel();
        maintenanceLabel = new JLabel();
        avgRevenueLabel = new JLabel();
        topStationLabel = new JLabel();

        addRow(grid, "Total stations:", totalStationsLabel);
        addRow(grid, "Total energy (kWh):", totalEnergyLabel);
        addRow(grid, "Total revenue (₹):", totalRevenueLabel);
        addRow(grid, "Stations AVAILABLE:", availableLabel);
        addRow(grid, "Stations CHARGING:", chargingLabel);
        addRow(grid, "Stations MAINTENANCE:", maintenanceLabel);
        addRow(grid, "Average revenue per station (₹):", avgRevenueLabel);
        addRow(grid, "Top revenue station:", topStationLabel);

        add(grid, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh Analytics");
        refreshBtn.addActionListener(e -> loadData());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private void addRow(JPanel panel, String labelText, JLabel valueLabel) {
        JLabel l = new JLabel(labelText);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(l);
        panel.add(valueLabel);
    }

    public void loadData() {
        int totalStations = dao.getTotalStations();
        int totalEnergy = dao.getTotalEnergy();
        double totalRevenue = dao.getTotalRevenue();

        int available = dao.countByStatus("AVAILABLE");
        int charging = dao.countByStatus("CHARGING");
        int maintenance = dao.countByStatus("MAINTENANCE");

        double avgRevenue = dao.getAverageRevenue();
        Station top = dao.getTopRevenueStation();

        totalStationsLabel.setText(String.valueOf(totalStations));
        totalEnergyLabel.setText(String.valueOf(totalEnergy));
        totalRevenueLabel.setText(String.valueOf(totalRevenue));
        availableLabel.setText(String.valueOf(available));
        chargingLabel.setText(String.valueOf(charging));
        maintenanceLabel.setText(String.valueOf(maintenance));
        avgRevenueLabel.setText(String.format("%.2f", avgRevenue));

        if (top != null) {
            topStationLabel.setText(
                    top.getName() + " (" + top.getCode() + "), ₹" + top.getRevenue()
            );
        } else {
            topStationLabel.setText("No data");
        }
    }
}
