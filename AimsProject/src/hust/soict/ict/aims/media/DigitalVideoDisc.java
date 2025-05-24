package hust.soict.ict.aims.media;

import hust.soict.ict.aims.Playable;

public class DigitalVideoDisc extends Disc implements Playable {
    public DigitalVideoDisc(int id, String title, String category, float cost, int length, String director) {
        super(id, title, category, cost, length, director);
    }

    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        super(title, category, director, length, cost);
    }

    public boolean equals(DigitalVideoDisc disc) {
        if(!this.getTitle().equals(disc.getTitle())) return false;
        if(disc.getCategory()!=null) if(!this.getCategory().equals(disc.getCategory())) return false;
        if(disc.getDirector()!=null) if(!this.getDirector().equals(disc.getDirector())) return false;
        if(this.getCost()!=disc.getCost()) return false;
        return this.getLength() == disc.getLength();
    }

    public String toString() {
        return this.getTitle() +
                " - " + this.getCategory() +
                " - " + this.getDirector() +
                " - " + this.getLength() +
                ": " + this.getCost() + "$";
    }

    public boolean isMatch(String title) {
        String[] wordSet = title.split(" ");
        for(String word: wordSet) {
            if(this.getTitle().contains(word)) return true;
        }
        return false;
    }

    public void play() {
        System.out.println("Playing DVD: " + this.getTitle());
        System.out.println("DVD length: " + this.getLength());
    }
}
