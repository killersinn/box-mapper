package com.forge.mtg.utils.mtgboxmapper;

import java.util.ArrayList;

/**
 * MTG Box Mapping API ©2013 Arman SEPETCI
 *
 * @author asepetci
 */
public class Booster {

    private String cardName = "";
    private String sleeveName = "";
    private ArrayList<Location> cardLocation;
    private String track = null;
    private ArrayList<Integer> guessedLocations;

    public Booster() {
    }

    public String getSleeveName() {
        return sleeveName;
    }

    public void setSleeveName(String sleeveName) {
        this.sleeveName = sleeveName;
    }

    public ArrayList<Location> getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(ArrayList<Location> cardLocation) {
        this.cardLocation = cardLocation;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public ArrayList<Integer> getGuessedLocations() {
        return guessedLocations;
    }

    public void setGuessedLocations(ArrayList<Integer> guessedLocations) {
        this.guessedLocations = guessedLocations;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
