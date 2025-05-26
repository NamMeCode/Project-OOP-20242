package hust.soict.ict.test.cart;

import hust.soict.ict.aims.cart.Cart;
import hust.soict.ict.aims.media.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class CartTest {
    public static void main(String[] args) {
        ArrayList<Media> mediae = new ArrayList<Media>();

        DigitalVideoDisc dvd=new DigitalVideoDisc(1000, "The Lion King",
                "Animation", 10f, 87, "Roger Allers");
        mediae.add(dvd);

        Track track1 = new Track("Love of my life", 3);
        Track track2 = new Track("Bohemian Rhapsody", 6);
        ArrayList<Track> tracks = new ArrayList<>();
        tracks.add(track1);
        tracks.add(track2);
        CompactDisc cd = new CompactDisc(1001, "Queen", "Rock",
                10f, 30, "Queen band", "Queen band", tracks);
        mediae.add(cd);

        Book book = new Book(1002, "Queen", "Phylosophy",
                5f, "Albus Camus");

        mediae.add(book);

        System.out.println("Original order");
        for(Media m: mediae) {
            System.out.println(m.toString());
        }

        System.out.println("Sort by cost then title");
        Collections.sort(mediae, Media.COMPARE_BY_COST_TITLE);
        for(Media item: mediae) {
            System.out.println(item.toString());
        }

        System.out.println("Sort by title then cost");
        Collections.sort(mediae, Media.COMPARE_BY_TITLE_COST);
        for(Media item: mediae) {
            System.out.println(item.toString());
        }
    }
}
