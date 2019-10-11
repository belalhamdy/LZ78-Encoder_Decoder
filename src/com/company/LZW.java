package com.company;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

class LZW {
    static class LZW78 {
        private static Map<String, Integer> compressDic = new HashMap<>();
        private static Map<Integer, String> decompressDic = new HashMap<>();

        static List<Pair<Integer, Character>> Compress(String line) {
            List<Pair<Integer, Character>> data = new ArrayList<>();
            compressDic.clear();
            String match = "";
            int prevIdx = 0, currentIdx = 0;
            compressDic.put(null, currentIdx++);
            for (char current : line.toCharArray()) {
                match += current;
                if (compressDic.containsKey(match)) prevIdx = compressDic.get(match);
                else {
                    compressDic.put(match, currentIdx++);
                    data.add(new Pair<>(prevIdx, current));
                    match = "";
                    prevIdx = 0;
                }

            }
            if (match.length() > 0) data.add(new Pair<>(prevIdx, null));
            return data;
        }

        static String Decompress(List<Pair<Integer, Character>> data) throws Exception {
            StringBuilder line = new StringBuilder();
            String match;
            decompressDic.clear();
            decompressDic.put(0, "");
            int prevIdx = 1;
            for (Pair<Integer, Character> current : data) {
                if (!decompressDic.containsKey(current.getKey()))
                    throw new Exception("Error : tags are incorrect please double check them");
                match = decompressDic.get(current.getKey());
                if (current.getValue() != 0) match += current.getValue();
                if (!decompressDic.containsValue(match)) decompressDic.put(prevIdx++, match);
                line.append(match);
            }
            return line.toString();
        }

        static Map<String, Integer> getCompressDictionary() {
            return compressDic.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//            return compressDic;
        }

        static Map<Integer, String> getDecompressDictionary() {
            return decompressDic;
        }
    }
}
