package com.accessiblelife.gui;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManagePlacesForm extends JFrame {

    // --- THEME COLORS ---
    private static final Color BG_COLOR = new Color(240, 255, 240);
    private static final Color ACCENT_COLOR = new Color(144, 238, 144);
    private static final Color TEXT_COLOR = new Color(47, 79, 79);

    private final PlaceService placeService = new PlaceService(); // FIX: Added 'final'

    public ManagePlacesForm() {
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

        // Place Data Table
        String[] columnNames = {"ID", "Name", "Category", "Ramp", "Toilet", "Braille", "Elevator"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable placeTable = new JTable(model);
        placeTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        placeTable.setRowHeight(25);
        placeTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        loadPlaceData(model);

        JScrollPane scrollPane = new JScrollPane(placeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(scrollPane, BorderLayout.CENTER);

        // Footer/Action Panel
        JPanel footer = new JPanel();
        footer.setBackground(BG_COLOR);

        JButton closeBtn = createSmallStyledButton("← Back to Dashboard", new Color(180, 180, 180), TEXT_COLOR);
        closeBtn.addActionListener(e -> dispose()); // FIX: Simplified lambda

        JButton addBtn = createSmallStyledButton("Add New Place", ACCENT_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Add New Place functionality coming soon!", "Feature Note", JOptionPane.INFORMATION_MESSAGE);
        });

        footer.add(closeBtn);
        footer.add(Box.createHorizontalStrut(20));
        footer.add(addBtn);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadPlaceData(DefaultTableModel model) {
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