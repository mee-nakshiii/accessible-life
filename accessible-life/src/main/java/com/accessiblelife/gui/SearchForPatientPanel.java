package com.accessiblelife.gui;

import com.accessiblelife.api.ApiClient;
import com.accessiblelife.model.Place;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchForPatientPanel extends JPanel {
    public SearchForPatientPanel() {
        setLayout(new BorderLayout());

        JTextField searchField = new JTextField();
        JCheckBox rampCheck = new JCheckBox("Has Ramp");
        JButton searchButton = new JButton("Search");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(searchField);
        topPanel.add(rampCheck);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText();
            Boolean hasRamp = rampCheck.isSelected();
            List<Place> results = ApiClient.searchPlaces(keyword, hasRamp);

            resultArea.setText("");
            for (Place p : results) {
                resultArea.append(p.getName() + " - " + p.getSpecificAddress() + "\n");
            }
        });
    }
}