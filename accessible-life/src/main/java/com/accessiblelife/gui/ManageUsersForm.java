package com.accessiblelife.gui;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageUsersForm extends JFrame {

    // --- THEME COLORS ---
    private static final Color BG_COLOR = new Color(240, 255, 240);
    // FIX: ACCENT_COLOR field is kept for consistency if it's used elsewhere,
    // but if only used in button creation (now in UIHelper), it should be deleted.
    // Assuming it's deleted here since the button styling uses the static UIHelper methods.
    private static final Color TEXT_COLOR = new Color(47, 79, 79);
    private static final Color LOGOUT_COLOR = new Color(255, 100, 100);

    private final UserService userService = new UserService(); // FIX: Added 'final'

    public ManageUsersForm() {
        setTitle("Admin: Manage Users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Registered Users", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 36));
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // User Data Table
        String[] columnNames = {"ID", "Name", "Email", "Role"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable userTable = new JTable(model);
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userTable.setRowHeight(25);
        userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        loadUserData(model);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(scrollPane, BorderLayout.CENTER);

        // Footer/Action Panel
        JPanel footer = new JPanel();
        footer.setBackground(BG_COLOR);

        JButton closeBtn = createSmallStyledButton("← Back to Dashboard", new Color(180, 180, 180), TEXT_COLOR);
        closeBtn.addActionListener(e -> dispose()); // FIX: Simplified lambda

        JButton deleteBtn = createSmallStyledButton("Delete Selected User", LOGOUT_COLOR, Color.WHITE);
        deleteBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Delete functionality coming soon!");
        });

        footer.add(closeBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(deleteBtn);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadUserData(DefaultTableModel model) {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            String role = user.isAdmin() ? "Admin" : "Standard";
            model.addRow(new Object[]{user.getId(), user.getName(), user.getEmail(), role});
        }
    }

    // NOTE: This helper method prevents the "Local variable 'button' is redundant" warning
    // when using the simplified UIHelper calls.
    private JButton createSmallStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setMaximumSize(new Dimension(250, 50));
        return button;
    }
}