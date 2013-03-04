package com.forge.mtg.utils.mtgboxmapper;

/**
 * MTG Box Mapping API ©2013 Arman SEPETCI
 *
 * @author asepetci
 */
public class BoxColumn {

    private Booster[] boosters = new Booster[12];

    public Booster[] getBoosters() {
        return boosters;
    }

    public void setBooster(Integer rowNumber, String sleeveName) {
        if (boosters[rowNumber] == null) {
            boosters[rowNumber] = new Booster();
        }
        boosters[rowNumber].setSleeveName(sleeveName);
    }

    public Booster getBooster(Integer rowNumber) {
        return boosters[rowNumber];
    }
}
