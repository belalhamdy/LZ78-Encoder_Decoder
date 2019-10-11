package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LZ78 {

    private static List<String> lastDictionary;

    public static char[] decompress(List<Pair<Integer, Character>> compressed) throws Exception {
        ArrayList<String> dictionary = new ArrayList<>();

        StringBuilder message = new StringBuilder();

        for (Pair<Integer, Character> p : compressed) {
            String entry = "";
            if (p.getKey() != 0) {
                if (dictionary.size() < p.getKey()){
                    //Just the worst possible errors message.
                    throw new Exception("Compressed data contains invalid pointer to non-existing entries in the dynamically generated dictionary.");
                }
                entry = dictionary.get(p.getKey()-1);
                message.append(entry);
            }
            entry += p.getValue();
            message.append(p.getValue());
            dictionary.add(entry);
        }
        return message.toString().toCharArray();

    }

    public static List<Pair<Integer, Character>> compress(char[] message) {
        if (lastDictionary == null) lastDictionary = new ArrayList<>();
        else lastDictionary.clear();
        HashMap<String, Integer> dictionary = new HashMap<>();

        List<Pair<Integer, Character>> compressed = new ArrayList<>();
        StringBuilder window = new StringBuilder();
        int matchIdx = 0;
        int directoryIdx = 1;
        for (char c : message) {
            window.append(c);
            int found = dictionary.getOrDefault(window.toString(), 0);
            if (found != 0) {
                matchIdx = found;
            } else {
                compressed.add(new Pair<>(matchIdx, c));
                matchIdx = 0;
                dictionary.put(window.toString(), directoryIdx++);
                //for logging purposed
                lastDictionary.add(window.toString());

                window.setLength(0);
            }
        }
        //if last character in message is part of a match
        if (window.length() > 0)
            compressed.add(new Pair<>(matchIdx, null));

        return compressed;
    }
    public static List<String> getLastDictionary(){
        return lastDictionary;
    }
}
