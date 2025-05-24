package hust.soict.ict.aims.store;

import hust.soict.ict.aims.media.Media;

import java.util.ArrayList;

public class Store {
    private ArrayList<Media> itemsInStore = new ArrayList<>();

    public ArrayList<Media> getItemsInStore() {
        return itemsInStore;
    }

    public void addMedia(Media item) {
        itemsInStore.add(item);
        System.out.println("The item has been added");
    }

    public void removeMedia(Media item) {
        if(itemsInStore.remove(item)) System.out.println("The item has been removed");
        else System.out.println("The item has not been removed");
    }
}
