package com.forge.mtg.utils.mtgboxmapper;

/**
 * MTG Box Mapping API
 *
 * @author asepetci
 */
public class BoosterMap {

    private Booster[] boosterMap = new Booster[36];

    public Booster[] getBoosterMap() {
        return boosterMap;
    }

    public void setBoosterMap(Booster[] boosterMap) {
        this.boosterMap = boosterMap;
    }

    public void setBooster(Integer location, Booster booster) {
        boosterMap[location] = booster;
    }

    public Booster getBooster(Integer location) {
        return boosterMap[location];
    }
}
