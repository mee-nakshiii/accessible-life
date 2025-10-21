package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ManagePlacesForm extends JFrame {

    private static final Color BG_COLOR = ThemeColors.BG_PRIMARY;
    private static final Color TEXT_COLOR = ThemeColors.TEXT_PRIMARY;
    private static final Color ACCENT_COLOR = ThemeColors.ACCENT_PRIMARY;
    private static final Color LOGOUT_COLOR = ThemeColors.LOGOUT_RED;


    private final PlaceService placeService = new PlaceService();
    private final JTable placeTable;
    private final DefaultTableModel model;

    private final long currentUserId;

    public ManagePlacesForm() {
        this(0);
    }

    public ManagePlacesForm(long userId) {
        this.currentUserId = userId;

        setTitle("Admin: Manage Places");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Registered Accessible Places", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 36));
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // --- Place Data Table ---
        String[] columnNames = {"ID", "Name", "Category", "Ramp", "Toilet", "Braille", "Elevator"};
        model = new DefaultTableModel(columnNames, 0);
        placeTable = new JTable(model);

        placeTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        placeTable.setRowHeight(30);
        placeTable.setGridColor(ThemeColors.BORDER_GRAY);
        placeTable.setBackground(ThemeColors.CARD_BG);
        placeTable.setForeground(TEXT_COLOR);

        JTableHeader tableHeader = placeTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableHeader.setBackground(ThemeColors.ACCENT_SECONDARY);
        tableHeader.setForeground(TEXT_COLOR);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 3; i < placeTable.getColumnCount(); i++) {
            placeTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        loadPlaceData();

        JScrollPane scrollPane = new JScrollPane(placeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        scrollPane.setBackground(BG_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        // Footer/Action Panel
        JPanel footer = new JPanel();
        footer.setBackground(BG_COLOR);

        JButton closeBtn = createStyledButton("← Back to Dashboard", ThemeColors.BORDER_GRAY, TEXT_COLOR);
        closeBtn.addActionListener(e -> dispose());

        JButton addBtn = createStyledButton("Add New Place", ACCENT_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> addPlace()); // Calls helper method

        // NEW: Edit Button
        JButton editBtn = createStyledButton("Edit Details/Verify", ACCENT_COLOR.darker(), Color.WHITE);
        editBtn.addActionListener(e -> editPlace()); // Calls helper method

        JButton deleteBtn = createStyledButton("Delete Selected Place", LOGOUT_COLOR, Color.WHITE);
        deleteBtn.addActionListener(e -> deleteSelectedPlace());

        footer.add(closeBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(addBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(editBtn); // Added Edit Button
        footer.add(Box.createHorizontalStrut(20));
        footer.add(deleteBtn);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Helper method to open AddPlaceForm and ensure refresh upon close
    private void addPlace() {
        AddPlaceForm addForm = new AddPlaceForm(currentUserId);
        addForm.setVisible(true);
        addForm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadPlaceData(); // Refresh table after form closes
            }
        });
    }

    // NEW Helper method to handle editing
    private void editPlace() {
        int selectedRow = placeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a place to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long placeId = (long) model.getValueAt(selectedRow, 0);
        Place placeToEdit = placeService.getPlaceById(placeId);

        if (placeToEdit != null) {
            AddPlaceForm editForm = new AddPlaceForm(currentUserId, placeToEdit);
            editForm.setVisible(true);
            editForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    loadPlaceData(); // Refresh table after edit form closes
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Could not retrieve place details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // ... (loadPlaceData and deleteSelectedPlace methods remain) ...
    private void loadPlaceData() {
        model.setRowCount(0);
        List<Place> places = placeService.getAllPlaces();

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

    private void deleteSelectedPlace() {
        int selectedRow = placeTable.getSelectedRow();
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
                loadPlaceData(); // Refresh table
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