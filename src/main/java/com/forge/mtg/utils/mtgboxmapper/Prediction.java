package com.forge.mtg.utils.mtgboxmapper;

/**
 * MTG Box Mapping API
 *
 * @author asepetci
 */
public class Prediction {

    private String safeName;
    private double approximatePrice;
    private String track;
    private int trackQueue;

    public Prediction() {
    }

    public Prediction(String safeName, double approximatePrice, String track, int trackQueue) {
        this.safeName = safeName;
        this.approximatePrice = approximatePrice;
        this.track = track;
        this.trackQueue = trackQueue;
    }

    public String getSafeName() {
        return safeName;
    }

    public void setSafeName(String safeName) {
        this.safeName = safeName;
    }

    public double getApproximatePrice() {
        return approximatePrice;
    }

    public void setApproximatePrice(double approximatePrice) {
        this.approximatePrice = approximatePrice;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public int getTrackQueue() {
        return trackQueue;
    }

    public void setTrackQueue(int trackQueue) {
        this.trackQueue = trackQueue;
    }
}
