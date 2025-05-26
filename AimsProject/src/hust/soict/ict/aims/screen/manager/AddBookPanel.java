package hust.soict.ict.aims.screen.manager;

import hust.soict.ict.aims.media.Book;
import hust.soict.ict.aims.store.Store;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class AddBookPanel extends JPanel {
    private JTextField titleField;
    private JTextField categoryField;
    private JTextField costField;
    private JTextField authorsField;

    public AddBookPanel(StoreManagerScreen frame, Store store) {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

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

        JLabel authorsLabel = new JLabel("Authors (comma-separated):");
        authorsField = new JTextField();
        inputPanel.add(authorsLabel);
        inputPanel.add(authorsField);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String category = categoryField.getText();
            float cost = Float.parseFloat(costField.getText());
            String authorsString = authorsField.getText();
            ArrayList<String> authors = new ArrayList<>(Arrays.asList(authorsString.split(",")));

            Book book = new Book(title, category, cost, authors);
            store.addMedia(book);

            JOptionPane.showMessageDialog(AddBookPanel.this, "Book added to store!");
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
        authorsField.setText("");
    }
}