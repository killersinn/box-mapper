package com.forge.mtg.utils.mtgboxmapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * MTG Box Mapping API
 *
 * @author asepetci
 */
public class ResourceReader {

    public static HashMap<String, Double> loadPrices(String set) throws IOException {
        HashMap<String, Double> prices = new HashMap<String, Double>();
        String file = set.toUpperCase() + "-PRICES.txt";
        BufferedReader rdr = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(file)));
        String line;
        while ((line = rdr.readLine()) != null) {
            String[] pair = line.split("::");
            pair[0] = pair[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
            prices.put(pair[0], Double.parseDouble(pair[1]));
        }
        return prices;
    }
    
    public static HashMap<String, String> loadCardNames(String set) throws IOException {
        HashMap<String, String> cardNames = new HashMap<String, String>();
        String file = set.toUpperCase() + "-PRICES.txt";
        BufferedReader rdr = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(file)));
        String line;
        while ((line = rdr.readLine()) != null) {
            String[] pair = line.split("::");
            cardNames.put(pair[0].replaceAll("[^a-zA-Z]", "").toUpperCase(), pair[0]);
        }
        return cardNames;
    }

    public static TrackPattern[] loadTrackPattern(String set) throws IOException {
        TrackPattern[] trackPatterns;
        StringBuilder fileContent = new StringBuilder();
        String file = set.toUpperCase() + "-PATTERNS.txt";
        BufferedReader rdr = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(file)));
        String line;
        while ((line = rdr.readLine()) != null) {
            fileContent.append(line);
            fileContent.append("\n");
        }
        String[] tempPatterns = fileContent.toString().split("\n");
        trackPatterns = new TrackPattern[tempPatterns.length];
        for (int i = 0; i < tempPatterns.length; i++) {
            TrackPattern pattern = new TrackPattern(tempPatterns[i].split(" "));
            if (pattern.getPattern().length > 0) {
                trackPatterns[i] = pattern;
            }
        }
        return trackPatterns;
    }

    public static Track loadTrack(String set, String track, HashMap<String, Double> prices) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        String file = set.toUpperCase() + "-" + track.toUpperCase() + ".txt";
        BufferedReader rdr = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(file)));
        String line;
        while ((line = rdr.readLine()) != null) {
            line = line.replaceAll("[^a-zA-Z]", "").toUpperCase();
            if (prices.get(line) == null) {
                System.out.println("Name Mismatch at Track-" + track + ": " + line);
            }
            fileContent.append(line);
            fileContent.append("\n");
        }
        return new Track(fileContent.toString().split("\n"));
    }
}
