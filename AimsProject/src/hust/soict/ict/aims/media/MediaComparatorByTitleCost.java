package hust.soict.ict.aims.media;

import java.util.Comparator;

public class MediaComparatorByTitleCost implements Comparator<Media> {
    public int compare(Media item1, Media item2) {
        int res = item1.getTitle().compareTo(item2.getTitle());
        return res != 0 ? res : (int) item1.getCost() - (int) item2.getCost();
    }
}
