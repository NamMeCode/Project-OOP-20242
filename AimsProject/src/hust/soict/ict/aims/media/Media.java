package hust.soict.ict.aims.media;

import java.util.Comparator;

public abstract class Media implements Comparable<Media>{
    private int id;
    private String title;
    private String category;
    private float cost;
    public static final Comparator<Media> COMPARE_BY_TITLE_COST = new MediaComparatorByTitleCost();
    public static final Comparator<Media> COMPARE_BY_COST_TITLE = new MediaComparatorByCostTitle();

    public Media(int id, String title, String category, float cost) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.cost = cost;
    }

    public Media(String title, String category, float cost) {
        this.title = title;
        this.category = category;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() { return category; }

    public float getCost() {
        return cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public boolean equals(Media item) {
        return this.title.equals(item.title);
    }

    public abstract String toString();

    public int compareTo(Media other) {
        if(other instanceof DigitalVideoDisc && this instanceof DigitalVideoDisc) {
            DigitalVideoDisc item1 = (DigitalVideoDisc) this;
            DigitalVideoDisc item2 = (DigitalVideoDisc) other;
            int res = item1.getTitle().compareTo(item2.getTitle());
            if(res != 0) return res;
            else {
                res = -(item1.getLength() - item2.getLength());
                if(res != 0) return res;
                else return (int) item1.getCost() - (int) item2.getCost();
            }
        }
        int res = (int) this.cost - (int) other.cost;
        return res != 0 ? res : this.title.compareTo(other.title);
    }
}
