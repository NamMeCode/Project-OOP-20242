package hust.soict.ict.aims.cart;

import hust.soict.ict.aims.media.DigitalVideoDisc;
import hust.soict.ict.aims.media.Media;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Media> itemsOrdered = new ArrayList<>();

    public void addMedia(Media item) {
        if(itemsOrdered.contains(item)) return;
        itemsOrdered.add(item);
    }

    public void removeMedia(Media item) {
        itemsOrdered.remove(item);
    }

    public float totalCost() {
        float sum=0f;
        for(Media item: itemsOrdered) {
            sum+=item.getCost();
        }
        return sum;
    }

    public float printTotalCost() {
        float sum=totalCost();
        int i=1;
        for(Media item: itemsOrdered) {
            System.out.printf("%d\t%s\t%.2f\n", i++, item.getTitle(), item.getCost());
        }
        System.out.println("The cart total cost is " + sum);
        return sum;
    }

    public void print() {
        System.out.println("******************CART*****************");
        System.out.println("Ordered Items:");
        int i=1;
        for(Media item: itemsOrdered) {
            System.out.print(i + ". DVD - ");
            System.out.print(item.toString());
            System.out.println();
        }
        System.out.println("Total cost: " + totalCost());
        System.out.println("***************************************");
    }

    public void searchByID(String id) {
        int ID = Integer.parseInt(id);
        for(Media item: itemsOrdered) {
            if(item.getId() == ID) {
                System.out.println(item.toString());
                return;
            }
        }
        System.out.println("No match is found");
    }

    public void searchByTitle(String keywords) {
        boolean match=false;
        for(Media item: itemsOrdered) {
            if(item instanceof DigitalVideoDisc disc) {
                if(disc.isMatch(keywords)) System.out.println(disc.toString());
            }
            match=true;
        }
        if(!match) System.out.println("No match is found");
    }
}
