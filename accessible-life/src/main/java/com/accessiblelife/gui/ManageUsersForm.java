package com.accessiblelife.gui;

import com.accessiblelife.model.User;
import com.accessiblelife.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ManageUsersForm extends JFrame {

    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;
    private static final Color LOGOUT_COLOR = ThemeColors.LOGOUT_RED;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;

    private final UserService userService = new UserService();
    private final JTable userTable;
    private final DefaultTableModel model;

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

        // --- User Data Table ---
        String[] columnNames = {"ID", "Name", "Email", "Role"};
        model = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(model);

        // FIX: Apply modern font and styling to table
        userTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userTable.setRowHeight(30); // Increased row height for better user experience
        userTable.setGridColor(ThemeColors.BORDER_GRAY);
        userTable.setBackground(ThemeColors.CARD_BG);
        userTable.setForeground(TEXT_COLOR);

        // FIX: Style the table header
        JTableHeader tableHeader = userTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableHeader.setBackground(ThemeColors.ACCENT_SECONDARY); // Light mint background for header
        tableHeader.setForeground(TEXT_COLOR);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);

        // Center-align text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < userTable.getColumnCount(); i++) {
            userTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        loadUserData();

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        scrollPane.setBackground(BG_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        // Footer/Action Panel
        JPanel footer = new JPanel();
        footer.setBackground(BG_COLOR);

        // Buttons use ThemeButton
        JButton closeBtn = ThemeButton.createPrimary("← Back to Dashboard", ThemeColors.BORDER_GRAY);
        closeBtn.setForeground(TEXT_COLOR);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        closeBtn.setMaximumSize(new Dimension(250, 50));
        closeBtn.addActionListener(e -> dispose());

        JButton deleteBtn = ThemeButton.createPrimary("Delete Selected User", LOGOUT_COLOR);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        deleteBtn.setMaximumSize(new Dimension(250, 50));
        deleteBtn.addActionListener(e -> deleteSelectedUser());

        footer.add(closeBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(deleteBtn);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadUserData() {
        model.setRowCount(0);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            String role = user.isAdmin() ? "Admin" : "Standard";
            model.addRow(new Object[]{user.getId(), user.getName(), user.getEmail(), role});
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long userId = (long) model.getValueAt(selectedRow, 0);
        String userName = (String) model.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to permanently delete user: " + userName + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (userService.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user. Please ensure all associated records are handled.", "DB Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}