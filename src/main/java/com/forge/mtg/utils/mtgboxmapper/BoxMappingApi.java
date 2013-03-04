package com.forge.mtg.utils.mtgboxmapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * MTG Box Mapping API ©2013 Arman SEPETCI
 *
 * @author asepetci
 */
public class BoxMappingApi {

    private HashMap<String, Double> prices;
    private HashMap<String, String> cardNames;
    private TrackPattern[] trackPatterns;
    private Track[] tracks = new Track[4];
    private Box mtgBox = new Box();
    private BoosterMap mtgMap = new BoosterMap();
    
    public static String getDailyKey(String setName){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        long instanceKey;
        instanceKey = Long.parseLong(dateFormat.format(date));
        if (setName.equalsIgnoreCase("GTC")){
            instanceKey = instanceKey-8752456;
        }
        instanceKey = instanceKey^3;
        return String.valueOf(instanceKey);
    }

    public BoxMappingApi(String setName) throws Throwable {
        try {
            cardNames = ResourceReader.loadCardNames(setName);
            prices = ResourceReader.loadPrices(setName);
            tracks[0] = ResourceReader.loadTrack(setName, "a", prices);
            tracks[1] = ResourceReader.loadTrack(setName, "b", prices);
            tracks[2] = ResourceReader.loadTrack(setName, "c", prices);
            tracks[3] = ResourceReader.loadTrack(setName, "d", prices);
            trackPatterns = ResourceReader.loadTrackPattern(setName);
        } catch (IOException ioe) {
            String exceptionMessage = "Can't load set " + setName + ", one of the resources is not available; " + ioe.getLocalizedMessage();
            throw new Throwable(exceptionMessage);
        }
    }

    public void setSleeves(String sleeveInput) {
        char[] pattern;

        int cursor = 0;
        for (int i = 0; i < sleeveInput.length(); i++) {
            if (sleeveInput.charAt(i) == '-') {
                cursor++;
            } else {
                int lineQueue = i - (cursor * 13);
                mtgBox.setBooster(cursor, lineQueue, String.valueOf(sleeveInput.charAt(i)));
            }
        }

        char[] orderedMap = new char[36];
        int mapCursor = 0;
        for (int i = 11; i >= 0; i--) {
            for (int j = 0; j <= 2; j++) {
                Booster booster = mtgBox.getBooster(j, i);
                mtgMap.setBooster(mapCursor, booster);
                orderedMap[mapCursor] = booster.getSleeveName().charAt(0);
                mapCursor++;
            }
        }
        pattern = capturePattern(String.valueOf(orderedMap));
        
        System.err.println("BEFORE");
        System.err.println("Pattern: " + String.valueOf(pattern));
        System.err.println("orderedMap: " + String.valueOf(orderedMap));
        
        HashMap<String, Integer> instrucions = new HashMap<String, Integer>();
        for (int patternCursor = 0; patternCursor < pattern.length; patternCursor++) {
            char[] optimisedPattern = pattern.clone();
            if (patternCursor != 0) {
                for (int p = 0; p < pattern.length; p++) {
                    optimisedPattern[(p + patternCursor) % optimisedPattern.length] = pattern[p];
                }
            }
            int diff = mismatchCount(optimisedPattern, String.valueOf(orderedMap).substring(0, 18).toCharArray());
            instrucions.put(String.valueOf(optimisedPattern) + "self", diff);
        }
        int testCursor = 18 - 3;
        while (testCursor < 18 && testCursor >= 0) {
            String testStr = String.valueOf(orderedMap).substring(0, 18);
            String self = testStr.substring(testCursor, testCursor + 3);
            String rest = testStr.substring(0, testCursor) + testStr.substring(testCursor + 3);
            for (int i = 0; i < testCursor; i = i + 3) {
                String resultStr = "";
                if (i - 3 < 0) {
                    resultStr += self + rest;
                } else {
                    resultStr += rest.substring(0, i) + self + rest.substring(i);
                }
                for (int patternCursor = 0; patternCursor < pattern.length; patternCursor++) {
                    char[] optimisedPattern = pattern.clone();
                    if (patternCursor != 0) {
                        for (int p = 0; p < pattern.length; p++) {
                            optimisedPattern[(p + patternCursor) % optimisedPattern.length] = pattern[p];
                        }
                    }
                    int diff = mismatchCount(optimisedPattern, resultStr.toCharArray());
                    instrucions.put(String.valueOf(optimisedPattern) + "-" + testCursor + ">" + i, diff);
                }
            }
            testCursor = testCursor - 3;
        }
        int minVal = 999;
        for (String key : instrucions.keySet()) {
            if (instrucions.get(key) < minVal) {
                minVal = instrucions.get(key);
            }
        }
        ArrayList<String> closestMatchList = new ArrayList<String>();
        for (String key : instrucions.keySet()) {
            if (instrucions.get(key) == minVal) {
                closestMatchList.add(key);
            }
        }
        instrucions = new HashMap<String, Integer>();
        for (String result : closestMatchList) {
            char[] optimisedPattern = pattern.clone();
            for (int p = 0; p < pattern.length; p++) {
                optimisedPattern[(p + 18 + minVal) % optimisedPattern.length] = pattern[p];
            }
            for (int patternCursor = 0; patternCursor < pattern.length; patternCursor++) {
                optimisedPattern = pattern.clone();
                if (patternCursor != 0) {
                    for (int p = 0; p < pattern.length; p++) {
                        optimisedPattern[(p + patternCursor) % optimisedPattern.length] = pattern[p];
                    }
                }
                int diff = mismatchCount(optimisedPattern, String.valueOf(orderedMap).substring(18).toCharArray());
                instrucions.put(result + ",self", diff);
            }
            testCursor = 18 - 3;
            while (testCursor < 18 && testCursor >= 0) {
                String testStr = String.valueOf(orderedMap).substring(18);
                String self = testStr.substring(testCursor, testCursor + 3);
                String rest = testStr.substring(0, testCursor) + testStr.substring(testCursor + 3);
                for (int i = 0; i < testCursor; i = i + 3) {
                    String resultStr = "";
                    if (i - 3 < 0) {
                        resultStr += self + rest;
                    } else {
                        resultStr += rest.substring(0, i) + self + rest.substring(i);
                    }
                    int diff = mismatchCount(optimisedPattern, resultStr.toCharArray());
                    instrucions.put(result + "," + (testCursor+18) + ">" + (i+18), diff);
                }
                testCursor = testCursor - 3;
            }
        }
        minVal = 999;
        for (String key : instrucions.keySet()) {
            if (instrucions.get(key) < minVal) {
                minVal = instrucions.get(key);
            }
        }
        closestMatchList = new ArrayList<String>();
        for (String key : instrucions.keySet()) {
            if (instrucions.get(key) == minVal) {
                closestMatchList.add(key);
            }
        }
        
        String istructionStr = closestMatchList.get(0);
        
        System.err.println("istructionStr: " + istructionStr);
        
        if (istructionStr.length() > 0){
            String[] resultAsArr = istructionStr.split("-");
            pattern = resultAsArr[0].toCharArray();
            String[] moveInfo = resultAsArr[1].split(",");
            for (int i=0; i<moveInfo.length; i++){
                if (moveInfo[i] != "self"){
                    String[] queueStr = moveInfo[i].split(">");
                    int fromQueue = Integer.valueOf(queueStr[0]);
                    int toQueue = Integer.valueOf(queueStr[1]);
                    mtgMap.setBoosterMap(ArrayFunctions.shiftUp(toQueue, mtgMap.getBoosterMap()));
                    mtgMap.setBoosterMap(ArrayFunctions.shiftUp(toQueue, mtgMap.getBoosterMap()));
                    mtgMap.setBoosterMap(ArrayFunctions.shiftUp(toQueue, mtgMap.getBoosterMap()));
                    mtgMap.setBoosterMap(ArrayFunctions.move(fromQueue + 3, toQueue, mtgMap.getBoosterMap()));
                    mtgMap.setBoosterMap(ArrayFunctions.move(fromQueue + 4, toQueue + 1, mtgMap.getBoosterMap()));
                    mtgMap.setBoosterMap(ArrayFunctions.move(fromQueue + 5, toQueue + 2, mtgMap.getBoosterMap()));
                    mtgMap.setBoosterMap(ArrayFunctions.delete(fromQueue + 3, mtgMap.getBoosterMap()));
                    mtgMap.setBoosterMap(ArrayFunctions.delete(fromQueue + 3, mtgMap.getBoosterMap()));
                    mtgMap.setBoosterMap(ArrayFunctions.delete(fromQueue + 3, mtgMap.getBoosterMap()));
                }
            }
        }
        Booster[] boosters = mtgMap.getBoosterMap();
        orderedMap = new char[boosters.length];
        for (int i=0; i<boosters.length; i++){
            orderedMap[i] = boosters[i].getSleeveName().charAt(0);
        }
        char[] reArrangedMap = orderedMap.clone();
        for (int i = 0; i < reArrangedMap.length; i++) {
            if (reArrangedMap[i] != pattern[(i % pattern.length)]) {
                reArrangedMap = ArrayFunctions.insertChar(i, reArrangedMap);
                mtgMap.setBoosterMap(ArrayFunctions.shiftUp(i, mtgMap.getBoosterMap()));
            }
        }
        System.err.println("AFTER");
        System.err.println("Pattern: " + String.valueOf(pattern));
        System.err.println("orderedMap: " + String.valueOf(orderedMap));
    }

    private int mismatchCount(char[] pattern, char[] orderedMap) {
        int cursorFixer = 0;
        StringBuilder reOrderInstructions = new StringBuilder();
        for (int i = 0; i < orderedMap.length + cursorFixer; i++) {
            if (i < orderedMap.length) {
                if (pattern[i % pattern.length] != orderedMap[i - cursorFixer]) {
                    reOrderInstructions.append(">");
                    cursorFixer++;
                } else {
                    reOrderInstructions.append(orderedMap[i - cursorFixer]);
                }
            } else {
                reOrderInstructions.append(orderedMap[i - cursorFixer]);
            }
        }
        return cursorFixer;
    }

    private char[] capturePattern(String orderedStr) {
        HashMap<String, String> sleeveMap = new HashMap<String, String>();
        for (int i = 0; i < orderedStr.length(); i++) {
            String tempChar = String.valueOf(orderedStr.charAt(i));
            if (!sleeveMap.containsKey(tempChar)) {
                sleeveMap.put(tempChar, findNextOf(tempChar, orderedStr));
            }
        }
        String tempKey = String.valueOf(orderedStr.charAt(0));
        StringBuilder sleevePattern = new StringBuilder();
        for (int i = 0; i < sleeveMap.keySet().size(); i++) {
            sleevePattern.append(tempKey);
            tempKey = sleeveMap.get(tempKey);
        }
        return sleevePattern.toString().toCharArray();
    }

    private String findNextOf(String item, String orderedStr) {
        String tempStr = orderedStr;
        HashMap<String, Integer> nextIndexMap = new HashMap<String, Integer>();
        while (tempStr.contains(item)) {
            int tempIndex = tempStr.lastIndexOf(item) + 1;
            if (tempIndex < tempStr.length()) {
                String nextChar = tempStr.substring(tempIndex).substring(0, 1);
                int tempCount = 0;
                if (nextIndexMap.containsKey(nextChar)) {
                    tempCount = nextIndexMap.get(nextChar);
                    nextIndexMap.remove(nextChar);
                }
                tempCount++;
                nextIndexMap.put(nextChar, tempCount);
            }
            tempStr = tempStr.substring(0, tempIndex - 1);
        }
        String tempReturn = null;
        int maxCount = 0;
        for (String tempKey : nextIndexMap.keySet()) {
            if (nextIndexMap.get(tempKey) > maxCount) {
                maxCount = nextIndexMap.get(tempKey);
                tempReturn = tempKey;
            }
        }
        return tempReturn;
    }

    public void selectCard(String locationInfo, String name, boolean isMultipleInput) throws Exception{
        try{
            int columnAlignment = Integer.parseInt(locationInfo.substring(0, 1).replace("L", "0").replace("M", "1").replace("R", "2"));
            int rowNumber = Integer.parseInt(locationInfo.substring(1)) - 1;
            mtgBox.getBooster(columnAlignment, rowNumber).setCardLocation(whichTrack(name, tracks));
            mtgBox.getBooster(columnAlignment, rowNumber).setCardName(name);
            if (!isMultipleInput) {
                trackPatternMatch();
                guessStart();
            }
        } catch(NullPointerException npe){
            throw new Exception("Card entered does not match the pattern. Box is possibly scrambled...");
        }
    }

    private ArrayList<Location> whichTrack(String cardName, Track[] tracks) {
        ArrayList<Location> locations = new ArrayList<Location>();
        cardName = cardName.replaceAll("[^a-zA-Z]", "");
        for (int i = 0; i < tracks.length; i++) {
            String[] cardList = tracks[i].getCardList();
            for (int j = 0; j < cardList.length; j++) {
                if (cardList[j].equalsIgnoreCase(cardName)) {
                    locations.add(new Location(i, j));
                }
            }
        }
        return locations;
    }

    private void trackPatternMatch() {
        String patternStr = createPattern();
        for (int i = 0; i < trackPatterns.length; i++) {
            if (trackPatterns[i] != null) {
                String check = trackPatterns[i].getPatternAsString();
                boolean match = (check.replaceAll(patternStr, "").length() != check.length());
                if (!match) {
                    trackPatterns[i] = null;
                } else {
                    int trackIndex = check.replaceAll(patternStr, "-").indexOf("-");
                    String[] reorderedTrack = new String[trackPatterns[i].getPattern().length];
                    for (int j = 0; j < reorderedTrack.length; j++) {
                        reorderedTrack[(j + trackIndex) % reorderedTrack.length] = trackPatterns[i].getPattern()[j];
                    }
                    trackPatterns[i].setPattern(reorderedTrack);
                    System.err.println("Available pattern: " + trackPatterns[i].getPatternAsString());
                }
            }
        }
        String[] commonStr = new String[22];
        for (int i = 0; i < trackPatterns.length; i++) {
            if (trackPatterns[i] != null) {
                String[] currentPattern = trackPatterns[i].getPattern();
                for (int j = 0; j < currentPattern.length; j++) {
                    if (commonStr[j] == null) {
                        commonStr[j] = currentPattern[j];
                    } else if (!commonStr[j].contains(currentPattern[j])) {
                        commonStr[j] = commonStr[j].replace("[", "");
                        commonStr[j] = commonStr[j].replace("]", "");
                        commonStr[j] = "[" + commonStr[j] + currentPattern[j] + "]";
                    }
                }
            }
        }
        String[] commonStrReversed = new String[22];
        for (int i = commonStr.length - 1; i >= 0; i--) {
            commonStrReversed[21 - i] = commonStr[i];
        }
        System.err.print("CommonPattern ");
        Booster[] boosters = mtgMap.getBoosterMap();
        for (int i = 0; i < boosters.length; i++) {
            System.err.print(commonStrReversed[(i % commonStrReversed.length)]);
            boosters[i].setTrack(commonStrReversed[(i % commonStrReversed.length)]);
        }
        System.err.println();
    }

    private String createPattern() {
        Booster[] boosters = mtgMap.getBoosterMap();
        StringBuilder patternStr = new StringBuilder();
        for (int i = 0; i < boosters.length; i++) {
            if (boosters[i].getCardLocation() != null) {
                String selectedTracks = "";
                for (Location loc : boosters[i].getCardLocation()) {
                    if (selectedTracks.isEmpty() || !selectedTracks.contains(String.valueOf(loc.getTrack()))) {
                        selectedTracks += String.valueOf(loc.getTrack());
                    }
                }
                patternStr.append("[" + selectedTracks + "]");
            } else {
                patternStr.append(".");
            }
        }
        return patternStr.toString().replaceAll("0", "A").replaceAll("1", "B").replaceAll("2", "C").replaceAll("3", "D");
    }

    private void guessStart() {
        initGuess();
        for (int i = 0; i < mtgMap.getBoosterMap().length; i++) {
            if (mtgMap.getBoosterMap()[i].getCardLocation() != null && mtgMap.getBoosterMap()[i].getCardLocation().size() > 0) {
                for (int j = 0; j < mtgMap.getBoosterMap()[i].getCardLocation().size(); j++) {
                    Location thisBoosterLocation = mtgMap.getBoosterMap()[i].getCardLocation().get(j);
                    String trackInfo = String.valueOf(thisBoosterLocation.getTrack()).replaceAll("0", "A").replaceAll("1", "B").replaceAll("2", "C").replaceAll("3", "D");
                    if (trackInfo.equals(mtgMap.getBoosterMap()[i].getTrack())) {
                        int counter = 0;
                        for (int k = i + 1; k < mtgMap.getBoosterMap().length; k++) {
                            if (mtgMap.getBoosterMap()[k].getTrack().contains("[") || mtgMap.getBoosterMap()[k].getTrack().contains("]")){
                                // Multiple options, questionable quess. discard 
                                break;
                            }
                            if (mtgMap.getBoosterMap()[k].getTrack().equals(trackInfo)) {
                                counter--;
                                int locationQueue = (thisBoosterLocation.getQueue() + counter) % (tracks[thisBoosterLocation.getTrack()].getCardList().length);
                                if (locationQueue < 0) {
                                    locationQueue += (tracks[thisBoosterLocation.getTrack()].getCardList().length);
                                }
                                if (!mtgMap.getBoosterMap()[k].getGuessedLocations().contains(locationQueue)) {
                                    mtgMap.getBoosterMap()[k].getGuessedLocations().add(locationQueue);
                                }
                            }
                        }
                        counter = 0;
                        for (int k = i - 1; k >= 0; k--) {
                            if (mtgMap.getBoosterMap()[k].getTrack().contains("[") || mtgMap.getBoosterMap()[k].getTrack().contains("]")){
                                // Multiple options, questionable quess. discard 
                                break;
                            }
                            if (mtgMap.getBoosterMap()[k].getTrack().equals(trackInfo)) {
                                counter++;
                                int locationQueue = (thisBoosterLocation.getQueue() + counter) % (tracks[thisBoosterLocation.getTrack()].getCardList().length);
                                if (!mtgMap.getBoosterMap()[k].getGuessedLocations().contains(locationQueue)) {
                                    mtgMap.getBoosterMap()[k].getGuessedLocations().add(locationQueue);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void initGuess() {
        for (int i = 0; i < mtgMap.getBoosterMap().length; i++) {
            mtgMap.getBoosterMap()[i].setGuessedLocations(new ArrayList<Integer>());
            if (mtgMap.getBoosterMap()[i].getCardLocation() != null) {
                String trackIndicators = "";
                String queueIndicators = "";
                for (int j = 0; j < mtgMap.getBoosterMap()[i].getCardLocation().size(); j++) {
                    Location currentTrack = mtgMap.getBoosterMap()[i].getCardLocation().get(j);
                    if (!trackIndicators.contains(String.valueOf(currentTrack.getTrack()))) {
                        trackIndicators += String.valueOf(currentTrack.getTrack());
                    }
                    queueIndicators += String.valueOf(currentTrack.getQueue()) + ",";
                }
                if (queueIndicators.contains(",")) {
                    queueIndicators = queueIndicators.substring(0, queueIndicators.lastIndexOf(","));
                }
                ArrayList<Location> foundList = new ArrayList<Location>();
                boolean foundSameTrack = false;
                for (int j = 0; j < mtgMap.getBoosterMap().length; j++) {
                    if (j != i && mtgMap.getBoosterMap()[j].getCardLocation() != null) {
                        ArrayList<Location> removeFromList = new ArrayList<Location>();
                        for (int k = 0; k < mtgMap.getBoosterMap()[j].getCardLocation().size(); k++) {
                            Location currentTrack = mtgMap.getBoosterMap()[j].getCardLocation().get(k);
                            if (trackIndicators.contains(String.valueOf(currentTrack.getTrack()))) {
                                foundSameTrack = true;
                                int trackCount = 0;
                                if (i > j) {
                                    for (int t = i; t > j; t--) {
                                        if (mtgMap.getBoosterMap()[t].getTrack().length() > 1 && mtgMap.getBoosterMap()[t].getTrack().contains(mtgMap.getBoosterMap()[j].getTrack())){
                                            trackCount = 0;
                                            break;
                                        }
                                        if (mtgMap.getBoosterMap()[t].getTrack().equals(mtgMap.getBoosterMap()[j].getTrack())) {
                                            trackCount--;
                                        }
                                    }
                                    if(trackCount == 0){
                                        break;
                                    }
                                    if (!queueIndicators.contains(String.valueOf(currentTrack.getQueue() + trackCount))) {
                                        removeFromList.add(currentTrack);
                                    } else {
                                        foundList.add(new Location(currentTrack.getTrack(), currentTrack.getQueue() + trackCount));
                                    }
                                } else {
                                    for (int t = i; t < j; t++) {
                                        if (mtgMap.getBoosterMap()[t].getTrack().length() > 1 && mtgMap.getBoosterMap()[t].getTrack().contains(mtgMap.getBoosterMap()[j].getTrack())){
                                            trackCount = 0;
                                            break;
                                        }
                                        if (mtgMap.getBoosterMap()[t].getTrack().equals(mtgMap.getBoosterMap()[j].getTrack())) {
                                            trackCount++;
                                        }
                                    }
                                    if(trackCount == 0){
                                        break;
                                    }
                                    if (!queueIndicators.contains(String.valueOf(currentTrack.getQueue() + trackCount))) {
                                        removeFromList.add(currentTrack);
                                    } else {
                                        foundList.add(new Location(currentTrack.getTrack(), currentTrack.getQueue() + trackCount));
                                    }
                                }
                            }
                        }
                        if (removeFromList.size() > 0) {
                            mtgMap.getBoosterMap()[j].getCardLocation().removeAll(removeFromList);
                        }
                    }
                }
                if (foundSameTrack && foundList.size() > 0) {
                    mtgMap.getBoosterMap()[i].setCardLocation(foundList);
                }
            }
        }
    }

    public HashMap<String, BoxMappingApiPrediction> showAll() {
        HashMap<String, BoxMappingApiPrediction> predictionMap = new HashMap<String, BoxMappingApiPrediction>();
        String[] boxHorizontalLocations = "L M R".split(" ");
        for (int i = 0; i < boxHorizontalLocations.length; i++) {
            for (int j = 1; j <= 12; j++) {
                String boxLocation = boxHorizontalLocations[i] + j;
                predictionMap.put(boxLocation, showGuess(boxLocation));
            }
        }
        return predictionMap;
    }

    public String jsonShowAll() {
        StringBuilder jsonStr = new StringBuilder();
        String[] boxHorizontalLocations = "L M R".split(" ");
        jsonStr.append("{\"error\" : \"\", \"response\" : [");
        for (int i = 0; i < boxHorizontalLocations.length; i++) {
            for (int j = 1; j <= 12; j++) {
                String boxLocation = boxHorizontalLocations[i] + j;
                jsonStr.append("{\"location\" : \"");
                jsonStr.append(boxLocation);
                jsonStr.append("\", \"trackPattern\" : \"");
                BoxMappingApiPrediction prediction = showGuess(boxLocation);
                jsonStr.append(prediction.getTrackPattern());
                jsonStr.append("\", \"predictions\" : [");
                for (int k = 0; k < prediction.getPredictions().size(); k++) {
                    Prediction currentPredictionRow = prediction.getPredictions().get(k);
                    jsonStr.append("{\"safeName\" : \"");
                    jsonStr.append(currentPredictionRow.getSafeName());
                    jsonStr.append("\", \"approximatePrice\" : \"$");
                    jsonStr.append(currentPredictionRow.getApproximatePrice());
                    jsonStr.append("\", \"track\" : \"");
                    jsonStr.append(currentPredictionRow.getTrack());
                    jsonStr.append("\", \"queue\" : ");
                    jsonStr.append(currentPredictionRow.getTrackQueue());
                    if (k < prediction.getPredictions().size() - 1) {
                        jsonStr.append("},");
                    } else {
                        jsonStr.append("}");
                    }
                }
                if (j < 12 || i < boxHorizontalLocations.length - 1) {
                    jsonStr.append("]},");
                } else {
                    jsonStr.append("]}");
                }
            }
        }
        jsonStr.append("]}");
        return jsonStr.toString();
    }

    public BoxMappingApiPrediction showGuess(String locationInfo) {
        BoxMappingApiPrediction prediction = new BoxMappingApiPrediction();
        int columnAlignment = Integer.parseInt(locationInfo.substring(0, 1).replace("L", "0").replace("M", "1").replace("R", "2"));
        int rowNumber = Integer.parseInt(locationInfo.substring(1)) - 1;
        Booster booster = mtgBox.getBooster(columnAlignment, rowNumber);
        prediction.setTrackPattern(booster.getTrack());
        ArrayList<Prediction> predictions = new ArrayList<Prediction>();
        if (booster.getCardName() == null || booster.getCardName().isEmpty()) {
            String trackHumanReadableName = booster.getTrack().replace("[", "").replace("]", "");
            String trackName = booster.getTrack().replaceAll("A", "0").replaceAll("B", "1").replaceAll("C", "2").replaceAll("D", "3").replace("[", "").replace("]", "");
            for (int t = 0; t < trackName.length(); t++) {
                int trackNumber = Integer.parseInt(String.valueOf(trackName.charAt(t)));
                String[] pool = tracks[trackNumber].getCardList();
                ArrayList<Integer> guessList = booster.getGuessedLocations();
                for (int i = 0; i < guessList.size(); i++) {
                    String card = pool[guessList.get(i)];
                    Prediction predictionToAdd = new Prediction(getHumanReadableName(card), getPrice(card), String.valueOf(trackHumanReadableName.charAt(t)), guessList.get(i));
                    if(!predictions.contains(predictionToAdd)){
                        predictions.add(predictionToAdd);
                    }
                }
            }
        } else {
            for (int t = 0; t < booster.getCardLocation().size(); t++) {
                String trackHumanReadableName = String.valueOf(booster.getCardLocation().get(t).getTrack()).replaceAll("0", "A").replaceAll("1", "B").replaceAll("2", "C").replaceAll("3", "D");
                Prediction predictionToAdd = new Prediction(getHumanReadableName(booster.getCardName()), getPrice(booster.getCardName()), trackHumanReadableName, booster.getCardLocation().get(t).getQueue());
                if(!predictions.contains(predictionToAdd)){
                    predictions.add(predictionToAdd);
                }
            }
        }
        prediction.setPredictions(predictions);
        return prediction;
    }
    private double getPrice(String key){
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();
        return prices.get(key);
    }
    private String getHumanReadableName(String key){
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();
        return cardNames.get(key);
    }
}
