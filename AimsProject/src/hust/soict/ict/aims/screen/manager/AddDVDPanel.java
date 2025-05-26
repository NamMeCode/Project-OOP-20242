package hust.soict.ict.aims.screen.manager;


import hust.soict.ict.aims.media.*;
import hust.soict.ict.aims.store.*;
import java.awt.*;
import javax.swing.*;

public class AddDVDPanel extends JPanel {
    private JTextField titleField;
    private JTextField categoryField;
    private JTextField costField;
    private JTextField directorField;
    private JTextField lengthField;

    public AddDVDPanel(StoreManagerScreen frame, Store store) {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField();
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);

        JLabel costLabel = new JLabel("Cost:");
        costField = new JTextField();
        inputPanel.add(costLabel);
        inputPanel.add(costField);

        JLabel directorLabel = new JLabel("Director:");
        directorField = new JTextField();
        inputPanel.add(directorLabel);
        inputPanel.add(directorField);

        JLabel lengthLabel = new JLabel("Length:");
        lengthField = new JTextField();
        inputPanel.add(lengthLabel);
        inputPanel.add(lengthField);

        JButton addButton = new JButton("Add DVD");
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String category = categoryField.getText();
            float cost = Float.parseFloat(costField.getText());
            String director = directorField.getText();
            int length = Integer.parseInt(lengthField.getText());

            DigitalVideoDisc dvd = new DigitalVideoDisc(title, category, director, length, cost);
            store.addMedia(dvd);

            JOptionPane.showMessageDialog(AddDVDPanel.this, "DVD added to store!");
            clearFields();
        });
        inputPanel.add(addButton);

        add(frame.createMenuBar(), BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
    }

    private void clearFields() {
        titleField.setText("");
        categoryField.setText("");
        costField.setText("");
        directorField.setText("");
        lengthField.setText("");
    }
}