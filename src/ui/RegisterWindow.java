package ui;

import dao.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterWindow extends JFrame {

    private final UserDAO userDAO = new UserDAO();

    private JTextField userField;
    private JPasswordField passField;
    private JPasswordField confirmField;

    public RegisterWindow() {
        initUI();
    }

    private void initUI() {
        setTitle("Eco Station – Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);

        Color bg = new Color(246, 248, 252);
        Color accent = new Color(24, 175, 96);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(bg);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        setContentPane(root);

        JLabel title = new JLabel("Create an account");
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
        confirmField = new JPasswordField();

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

        gbc.gridx = 0; gbc.gridy = row;
        form.add(new JLabel("Confirm Password"), gbc);
        gbc.gridx = 1;
        form.add(confirmField, gbc);
        row++;

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(accent);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        form.add(registerBtn, gbc);

        root.add(form, BorderLayout.CENTER);

        registerBtn.addActionListener(e -> onRegister());
        getRootPane().setDefaultButton(registerBtn);
    }

    private void onRegister() {
        String username = userField.getText().trim();
        String pass = new String(passField.getPassword());
        String confirm = new String(confirmField.getPassword());

        if (username.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields.");
            return;
        }

        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        boolean ok = userDAO.register(username, pass);
        if (ok) {
            JOptionPane.showMessageDialog(this, "User registered. You can login now.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username already exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
