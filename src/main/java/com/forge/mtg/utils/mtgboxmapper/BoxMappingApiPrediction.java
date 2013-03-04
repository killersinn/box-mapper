package com.forge.mtg.utils.mtgboxmapper;

import java.util.ArrayList;

/**
 * MTG Box Mapping API ©2013 Arman SEPETCI
 *
 * @author asepetci
 */
public class BoxMappingApiPrediction {

    private String trackPattern;
    private ArrayList<Prediction> predictions;

    public BoxMappingApiPrediction() {
    }

    public String getTrackPattern() {
        return trackPattern;
    }

    public void setTrackPattern(String trackPattern) {
        this.trackPattern = trackPattern;
    }

    public ArrayList<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<Prediction> predictions) {
        this.predictions = predictions;
    }
}
