package hust.soict.ict.aims.screen.manager;

import hust.soict.ict.aims.media.*;
import hust.soict.ict.aims.store.*;
import java.awt.*;
import javax.swing.*;

public class AddCDPanel extends JPanel {
    private JTextField titleField;
    private JTextField categoryField;
    private JTextField costField;
    private JTextField artistField;
    private JTextArea tracksArea;
    private JTextField directorField;
    private JTextField lengthField;

    public AddCDPanel(StoreManagerScreen frame, Store store) {
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

        JLabel artistLabel = new JLabel("Artist:");
        artistField = new JTextField();
        inputPanel.add(artistLabel);
        inputPanel.add(artistField);

        JLabel directorLabel = new JLabel("Director:");
        directorField = new JTextField();
        inputPanel.add(directorLabel);
        inputPanel.add(directorField);

        JLabel lengthLabel = new JLabel("Length:");
        lengthField = new JTextField();
        inputPanel.add(lengthLabel);
        inputPanel.add(lengthField);

        JLabel tracksLabel = new JLabel("Tracks (title:length,title:length,...):");
        tracksArea = new JTextArea();
        inputPanel.add(tracksLabel);
        inputPanel.add(new JScrollPane(tracksArea));

        JButton addButton = new JButton("Add CD");
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String category = categoryField.getText();
            float cost = Float.parseFloat(costField.getText());
            String artist = artistField.getText();
            String director = directorField.getText();
            int length = Integer.parseInt(lengthField.getText());
            String tracksString = tracksArea.getText();

            CompactDisc cd = new CompactDisc(title, category, director, length, cost, artist);

            String[] tracks = tracksString.split(",");
            for (String track : tracks) {
                String[] trackInfo = track.split(":");
                String trackTitle = trackInfo[0];
                int trackLength = Integer.parseInt(trackInfo[1]);
                cd.addTrack(new Track(trackTitle, trackLength));
            }

            store.addMedia(cd);

            JOptionPane.showMessageDialog(AddCDPanel.this, "CD added to store!");
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
        artistField.setText("");
        tracksArea.setText("");
    }
}