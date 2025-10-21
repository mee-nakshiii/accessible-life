package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class UserPlacesForm extends JFrame {

    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;
    private static final Color LOGOUT_COLOR = ThemeColors.LOGOUT_RED;

    private final PlaceService placeService = new PlaceService();
    private final JTable userPlaceTable;
    private final DefaultTableModel model;
    private final long currentUserId; // To filter places by the logged-in user

    public UserPlacesForm(long userId) {
        this.currentUserId = userId;
        setTitle("My Submitted Places");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("My Submitted Places", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 36));
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // --- Place Data Table ---
        String[] columnNames = {"ID", "Name", "Category", "Ramp", "Toilet", "Braille", "Elevator"};
        model = new DefaultTableModel(columnNames, 0);
        userPlaceTable = new JTable(model);

        userPlaceTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userPlaceTable.setRowHeight(30);
        userPlaceTable.setGridColor(ThemeColors.BORDER_GRAY);
        userPlaceTable.setBackground(ThemeColors.CARD_BG);
        userPlaceTable.setForeground(TEXT_COLOR);

        JTableHeader tableHeader = userPlaceTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableHeader.setBackground(ThemeColors.ACCENT_SECONDARY);
        tableHeader.setForeground(TEXT_COLOR);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 3; i < userPlaceTable.getColumnCount(); i++) {
            userPlaceTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        loadUserPlaceData();

        JScrollPane scrollPane = new JScrollPane(userPlaceTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        scrollPane.setBackground(BG_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        // Footer/Action Panel
        JPanel footer = new JPanel();
        footer.setBackground(BG_COLOR);

        JButton closeBtn = createStyledButton("← Back to Home", ThemeColors.BORDER_GRAY, TEXT_COLOR);
        closeBtn.addActionListener(e -> dispose());

        JButton addBtn = createStyledButton("Add New Place", ACCENT_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> {
            new AddPlaceForm(currentUserId).setVisible(true); // Pass user ID to AddPlaceForm
            // Implement a way to refresh table after AddPlaceForm closes (e.g., using a WindowListener)
        });

        JButton editBtn = createStyledButton("Edit Selected Place", ThemeColors.ACCENT_SECONDARY.darker(), Color.WHITE);
        editBtn.addActionListener(e -> editSelectedPlace());

        JButton deleteBtn = createStyledButton("Delete Selected Place", LOGOUT_COLOR, Color.WHITE);
        deleteBtn.addActionListener(e -> deleteSelectedPlace());

        footer.add(closeBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(addBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(editBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(deleteBtn);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    // NEW: Load places specific to this user
    private void loadUserPlaceData() {
        model.setRowCount(0);
        List<Place> places = placeService.getPlacesByUserId(currentUserId);

        for (Place place : places) {
            model.addRow(new Object[]{
                    place.getId(),
                    place.getName(),
                    place.getCategory(),
                    place.isHasRamp() ? "Yes" : "No",
                    place.isHasAccessibleToilet() ? "Yes" : "No",
                    place.isHasBrailleSignage() ? "Yes" : "No",
                    place.isHasElevator() ? "Yes" : "No"
            });
        }
    }

    private void editSelectedPlace() {
        int selectedRow = userPlaceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a place to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long placeId = (long) model.getValueAt(selectedRow, 0);
        Place placeToEdit = placeService.getPlaceById(placeId);

        if (placeToEdit != null) {
            // Open an edit form. We can reuse/adapt AddPlaceForm for this.
            AddPlaceForm editForm = new AddPlaceForm(currentUserId, placeToEdit);
            editForm.setVisible(true);

            // Add a WindowListener to refresh table when edit form closes
            editForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    loadUserPlaceData(); // Refresh table when edit form is closed
                }
            });

        } else {
            JOptionPane.showMessageDialog(this, "Could not retrieve place details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedPlace() {
        int selectedRow = userPlaceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a place to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long placeId = (long) model.getValueAt(selectedRow, 0);
        String placeName = (String) model.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to permanently delete place: " + placeName + "?\nNOTE: This will delete associated reviews.",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (placeService.deletePlace(placeId)) {
                JOptionPane.showMessageDialog(this, "Place deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUserPlaceData(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete place.", "DB Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = ThemeButton.createPrimary(text, bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setMaximumSize(new Dimension(250, 50));
        return button;
    }
}