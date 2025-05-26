package hust.soict.ict.aims.screen.manager;

import hust.soict.ict.aims.media.Media;
import hust.soict.ict.aims.store.MediaStore;
import hust.soict.ict.aims.store.Store;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StoreManagerScreen extends JFrame {
    private Store store;
    private final AddBookPanel addBookPanel;
    private final AddCDPanel addCDPanel;
    private final AddDVDPanel addDVDPanel;

    public StoreManagerScreen(Store store) {
        this.store = store;

        addBookPanel = new AddBookPanel(this, store);
        addCDPanel = new AddCDPanel(this, store);
        addDVDPanel = new AddDVDPanel(this, store);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(createNorth(), BorderLayout.NORTH);
        cp.add(createCenter(), BorderLayout.CENTER);

        setTitle("Store");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JMenuBar createMenuBar() {
        JMenu menu = new JMenu("Options");

        JMenuItem viewStoreMenuItem = new JMenuItem("View store");
        menu.add(viewStoreMenuItem);
        viewStoreMenuItem.addActionListener(e -> {
            Container cp = getContentPane();
            cp.removeAll();
            cp.add(createNorth(), BorderLayout.NORTH);
            cp.add(createCenter(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        JMenu smUpdateStore = new JMenu("Update Store");
        JMenuItem addBookMenuItem = new JMenuItem("Add Book");
        addBookMenuItem.addActionListener(e -> {
            setContentPane(addBookPanel);
            revalidate();
            repaint();
        });
        smUpdateStore.add(addBookMenuItem);

        JMenuItem addCDMenuItem = new JMenuItem("Add CD");
        addCDMenuItem.addActionListener(e -> {
            setContentPane(addCDPanel);
            revalidate();
            repaint();
        });
        smUpdateStore.add(addCDMenuItem);

        JMenuItem addDVDMenuItem = new JMenuItem("Add DVD");
        addDVDMenuItem.addActionListener(e -> {
            setContentPane(addDVDPanel);
            revalidate();
            repaint();
        });
        smUpdateStore.add(addDVDMenuItem);
        menu.add(smUpdateStore);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuBar.add(menu);

        return menuBar;
    }

    JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        JLabel title = new JLabel("AIMS");
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 50));
        title.setForeground(Color.CYAN);

        title.add(Box.createRigidArea(new Dimension(10, 10)));
        header.add(title);
        header.add(Box.createHorizontalGlue());
        header.add(Box.createRigidArea(new Dimension(10, 10)));

        return header;
    }

    JPanel createNorth() {
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(createMenuBar());
        north.add(createHeader());
        return north;
    }

    JPanel createCenter() {
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(3, 3, 2, 2));

        ArrayList<Media> mediaInStore = store.getItemsInStore();
        for(int i = 0; i < mediaInStore.size(); i++) {
            MediaStore cell = new MediaStore(mediaInStore.get(i));
            center.add(cell);
        }

        return center;
    }
}
