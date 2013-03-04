package com.forge.mtg.utils.mtgboxmapper;

/**
 * MTG Box Mapping API
 *
 * @author asepetci
 */
public class Track {

    private String[] cardList;

    public Track(String[] cardList) {
        this.cardList = cardList;
    }

    public String[] getCardList() {
        return cardList;
    }
}
