package hust.soict.ict.aims.media;

import hust.soict.ict.aims.Playable;

import java.util.ArrayList;
import java.util.List;

public class CompactDisc extends Disc implements Playable {
    private String artist;
    private ArrayList<Track> tracks = new ArrayList<>();

    public CompactDisc(int id, String title, String category, float cost, int length, String director, String artist, ArrayList<Track> tracks) {
        super(id, title, category, cost, length, director);
        this.artist = artist;
        this.tracks = tracks;
    }

    public CompactDisc(String title, String category, String director, int length, float cost, String artist) {
        super(title, category, director, length, cost);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void addTrack(Track track) {
        if(tracks.contains(track)) System.out.println("This track is already added");
        else {
            tracks.add(track);
            System.out.println("Track added successfully");
        }
    }

    public void removeTrack(Track track) {
        if(tracks.contains(track)) {
            tracks.remove(track);
            System.out.println("Track removed successfully");
        }
        else System.out.println("This track is not in the disc");
    }

    public int getLength() {
        int sumLength=0;
        for(Track track: tracks) {
            sumLength += track.getLength();
        }
        return sumLength;
    }

    public String toString() {
        return this.getTitle() +
                " - " + this.getCategory() +
                " - " + this.getArtist() +
                " - " + this.getDirector() +
                " - " + this.getLength() +
                ": " + this.getCost() + "$";
    }

    public void play() {
        System.out.println("Playing CD: " + this.getTitle());
        System.out.println("CD length: " + this.getLength());
        for(Track track: tracks) {
            track.play();
        }
    }
}
