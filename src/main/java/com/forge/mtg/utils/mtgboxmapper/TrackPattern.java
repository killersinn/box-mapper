package com.forge.mtg.utils.mtgboxmapper;

/**
 * MTG Box Mapping API
 *
 * @author asepetci
 */
public class TrackPattern {

    private String[] pattern;

    public TrackPattern(String[] pattern) {
        this.pattern = pattern;
    }

    public String[] getPattern() {
        return pattern;
    }

    public void setPattern(String[] pattern) {
        this.pattern = pattern;
    }

    public String getPatternAsString() {
        StringBuilder patternStr = new StringBuilder();
        for (int i = pattern.length - 1; i >= 0; i--) {
            patternStr.append(pattern[i]);
        }
        patternStr.append(patternStr.toString());
        return patternStr.toString();
    }
}
