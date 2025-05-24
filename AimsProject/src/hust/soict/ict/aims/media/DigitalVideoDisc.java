package hust.soict.ict.aims.media;

public class DigitalVideoDisc extends Media {
    private String title;
    private String category;
    private String director;
    private int length;
    private float cost;
    private int id=1000;
    private static int nbDigitalVideoDisc=0;

    public DigitalVideoDisc(String title) {
        this.title = title;
        this.id+=nbDigitalVideoDisc++;
    }

    public DigitalVideoDisc(String title, String category, float cost) {
        this.title = title;
        this.category = category;
        this.cost = cost;
        this.id+=nbDigitalVideoDisc++;
    }

    public DigitalVideoDisc(String title, String category, String director, float cost) {
        this.title = title;
        this.category = category;
        this.director = director;
        this.cost = cost;
        this.id+=nbDigitalVideoDisc++;
    }

    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        this.title = title;
        this.category = category;
        this.director = director;
        this.length = length;
        this.cost = cost;
        this.id+=nbDigitalVideoDisc++;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDirector() {
        return director;
    }

    public int getLength() {
        return length;
    }

    public float getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    public boolean equals(DigitalVideoDisc disc) {
        if(!this.title.equals(disc.title)) return false;
        if(disc.category!=null) if(!this.category.equals(disc.category)) return false;
        if(disc.director!=null) if(!this.director.equals(disc.director)) return false;
        if(this.cost!=disc.cost) return false;
        return this.length == disc.length;
    }

    public String toString() {
        StringBuffer DVD = new StringBuffer();
        DVD.append(title);
        if(!category.isEmpty()) DVD.append(" - " + category);
        else if(!director.isEmpty()) DVD.append(" - " + director);
        else if(length != 0) DVD.append(" - " + length);
        else if(cost != 0) DVD.append(": " + cost);
        return DVD.toString();
    }

    public boolean isMatch(String title) {
        String[] wordSet = title.split(" ");
        for(String word: wordSet) {
            if(this.title.contains(word)) return true;
        }
        return false;
    }
}
