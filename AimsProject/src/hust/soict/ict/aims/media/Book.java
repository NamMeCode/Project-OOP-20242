package hust.soict.ict.aims.media;

import java.util.ArrayList;
import java.util.List;

public class Book extends Media {
    private List<String> authors = new ArrayList<>();

    public Book(int id, String title, String category, float cost, String... author) {
        super(id, title, category, cost);
        this.authors.addAll(List.of(author));
    }

    public Book(String title, String category, float cost, List<String> authors) {
        super(title, category, cost);
        this.authors = authors;
    }

    public void addAuthor(String authorName) {
        if(authors.contains(authorName)) return;
        authors.add(authorName);
    }

    public void removeAuthor(String authorName) {
        authors.remove(authorName);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(this.getTitle()).append(" - ").append(this.getCategory()).append(" - ");
        for(String author: authors) {
            string.append(author).append(" - ");
        }
        string.append(": ").append(this.getCost()).append("$");
        return string.toString();
    }
}
