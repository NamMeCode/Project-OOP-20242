package hust.soict.ict.aims.media;

import hust.soict.ict.aims.Playable;

public class Track implements Playable {
    private String title;
    private int length;

    public Track(String title, int length) {
        this.title = title;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public boolean equals(Track track) {
        return this.title.equals(track.title) && this.length == track.length;
    }

    public void play() {
        System.out.println("Playing track: " + this.title);
        System.out.println("Track length: " + this.length);
    }
}
