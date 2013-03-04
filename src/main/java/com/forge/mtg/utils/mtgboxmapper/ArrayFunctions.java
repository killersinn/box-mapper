package com.forge.mtg.utils.mtgboxmapper;

/**
 * MTG Box Mapping API ©2013 Arman SEPETCI
 *
 * @author asepetci
 */
public class ArrayFunctions {

    public static char[] insertChar(int charAt, char[] original) {
        char[] insertedArr = new char[original.length + 1];
        insertedArr[charAt] = '*';
        for (int i = 0; i < original.length; i++) {
            if (i < charAt) {
                insertedArr[i] = original[i];
            } else {
                insertedArr[i + 1] = original[i];
            }
        }
        return insertedArr;
    }

    public static char[] removeChar(int charAt, char[] original) {
        char[] insertedArr = new char[original.length - 1];
        for (int i = 0; i < original.length; i++) {
            if (i < charAt) {
                insertedArr[i] = original[i];
            } else if (i > charAt) {
                insertedArr[i - 1] = original[i];
            }
        }
        return insertedArr;
    }

    public static Booster[] shiftUp(int charAt, Booster[] mtgMap) {
        Booster[] shiftedArr = new Booster[mtgMap.length + 1];
        shiftedArr[charAt] = new Booster();
        shiftedArr[charAt].setCardName("MISSING");
        for (int i = 0; i < mtgMap.length; i++) {
            if (i < charAt) {
                shiftedArr[i] = mtgMap[i];
            } else {
                shiftedArr[i + 1] = mtgMap[i];
            }
        }
        return shiftedArr;
    }

    public static Booster[] move(int from, int to, Booster[] mtgMap) {
        mtgMap[to] = mtgMap[from];
        return mtgMap;
    }

    public static Booster[] delete(int from, Booster[] mtgMap) {
        Booster[] removedArr = new Booster[mtgMap.length - 1];
        for (int i = 0; i < mtgMap.length; i++) {
            if (i < from) {
                removedArr[i] = mtgMap[i];
            } else if (i > from) {
                removedArr[i - 1] = mtgMap[i];
            }
        }
        return removedArr;
    }
}
