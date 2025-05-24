package hust.soict.ict.aims.store;

import hust.soict.ict.aims.media.DigitalVideoDisc;

public class Store {
    public static final int MAX_NUMBER_STORE=100;
    private DigitalVideoDisc[] itemsInStore = new DigitalVideoDisc[MAX_NUMBER_STORE];
    private int qtyStore;

    public void addDVD(DigitalVideoDisc dvd) {
        if(qtyStore<MAX_NUMBER_STORE) {
            itemsInStore[qtyStore]=dvd;
            qtyStore++;
            System.out.println("The DVD has been added");
        }
        else System.out.println("The store is full");
    }

    public void removeDVD(DigitalVideoDisc dvd) {
        for(int i=0; i<qtyStore; i++) {
            if(itemsInStore[i].equals(dvd)) {
                itemsInStore[i]=null;
                for(int j=i+1; j<qtyStore; j++) itemsInStore[j-1]=itemsInStore[j];
                qtyStore--;
                System.out.println("The disc has been removed");
                return;
            }
        }
        System.out.println("The disc has not been removed");
    }
}
