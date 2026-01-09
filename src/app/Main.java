package app;

import dao.StationDAO;
import ui.LoginWindow;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        StationDAO stationDAO = new StationDAO();
        SwingUtilities.invokeLater(() -> {
            LoginWindow login = new LoginWindow(stationDAO);
            login.setVisible(true);
        });
    }
}
