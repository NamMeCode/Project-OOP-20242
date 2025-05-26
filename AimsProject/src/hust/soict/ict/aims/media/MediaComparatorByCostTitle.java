package hust.soict.ict.aims.media;

import java.util.Comparator;

public class MediaComparatorByCostTitle implements Comparator<Media> {
    public int compare(Media item1, Media item2) {
        int res = (int) item1.getCost() - (int) item2.getCost();
        return res != 0 ? res : item1.getTitle().compareTo(item2.getTitle());
    }
}
