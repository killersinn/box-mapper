package com.forge.mtg.utils.mtgboxmapper;

/**
 * MTG Box Mapping API ©2013 Arman SEPETCI
 *
 * @author asepetci
 */
public class Box {

    private BoxColumn[] boxContents = new BoxColumn[3];

    public Box() {
    }

    public BoxColumn[] getBoxContents() {
        return boxContents;
    }

    public void setBooster(Integer columnAlignment, Integer rowNumber, String sleeveName) {
        if (boxContents[columnAlignment] == null) {
            boxContents[columnAlignment] = new BoxColumn();
        }
        boxContents[columnAlignment].setBooster(rowNumber, sleeveName);
    }

    public Booster getBooster(Integer columnAlignment, Integer rowNumber) {
        return boxContents[columnAlignment].getBooster(rowNumber);
    }
}
