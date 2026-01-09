package ui;

import dao.UserDAO;
import dao.StationDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginWindow extends JFrame {

    private final UserDAO userDAO = new UserDAO();
    private final StationDAO stationDAO;

    private JTextField userField;
    private JPasswordField passField;

    public LoginWindow(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
        initUI();
    }

    private void initUI() {
        setTitle("Eco Station – Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);

        Color bg = new Color(246, 248, 252);
        Color accent = new Color(24, 175, 96);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(bg);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(root);

        JLabel title = new JLabel("Sign in to Eco Station");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(accent);
        root.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        userField = new JTextField();
        passField = new JPasswordField();

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Username"), gbc);
        gbc.gridx = 1;
        form.add(userField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Password"), gbc);
        gbc.gridx = 1;
        form.add(passField, gbc);
        row++;

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        JButton registerBtn = new JButton("Register");
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(accent);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        buttons.add(registerBtn);
        buttons.add(loginBtn);

        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        root.add(form, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> onLogin());
        registerBtn.addActionListener(e -> new RegisterWindow().setVisible(true));
        getRootPane().setDefaultButton(loginBtn);
    }

    private void onLogin() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.");
            return;
        }

        boolean ok = userDAO.validate(username, password);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Login successful.");
            openDashboard();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openDashboard() {
        dispose();
        DashboardWindow dashboard = new DashboardWindow(stationDAO);
        dashboard.setVisible(true);
    }
}
