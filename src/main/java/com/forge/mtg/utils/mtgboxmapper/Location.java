package com.forge.mtg.utils.mtgboxmapper;

/**
 * MTG Box Mapping API
 *
 * @author asepetci
 */
public class Location {

    private int track;
    private int queue;

    public Location(int track, int queue) {
        this.track = track;
        this.queue = queue;
    }

    public int getTrack() {
        return track;
    }

    public int getQueue() {
        return queue;
    }
}
