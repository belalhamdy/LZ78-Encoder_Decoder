package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to LZ78 Compression/Decompression");
        while (true) {
            System.out.println("\n1- Compress\n2- Decompress\n0- Exit");
            char choice = in.next().charAt(0);
            if (choice == '1') {
                System.out.println("Enter the message to compress:");
                in.nextLine();
                String line = in.nextLine();
                System.out.println("\nTags: ");
                List<Pair<Integer, Character>> compressed = LZW.LZW78.Compress(line);
                printTags(compressed);
                System.out.println("Dictionary : " + LZW.LZW78.getCompressDictionary());
                System.out.println("---------------------------------------");
            } else if (choice == '2') {
                System.out.println("Enter the tags to decompress in the following criteria \"Index character\" (Unsupported tags will be ignored), to insert null just type null ,insert \"0 0\" to stop the input.");
                List<Pair<Integer, Character>> data = inputTags();
                try {
                    String Decompressed = LZW.LZW78.Decompress(data);
                    System.out.println("\nDecompressed message : " + Decompressed);
                    System.out.println("---------------------------------------");
                    System.out.println("Dictionary : ");
                    for (Map.Entry<Integer, String> entry : LZW.LZW78.getDecompressDictionary().entrySet()) {
                        System.out.println(entry.getValue() + " -> " + entry.getKey().toString());
                    }
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage());
                }
                System.out.println("---------------------------------------");
            } else
                break;
        }
    }

    private static List<Pair<Integer, Character>> inputTags() {
        List<Pair<Integer, Character>> data = new ArrayList<>();
        int index;
        char character;
        while (true) {
            String tempIdx;
            String tempChar;
            tempIdx = in.next();
            tempChar = in.next();
            in.nextLine();
            try {
                index = Integer.parseInt(tempIdx);
            } catch (NumberFormatException e) {
                continue;
            }
            if ((tempChar.length() > 1 && !tempChar.equals("null")) || index < 0) continue;
            character = tempChar.charAt(0);
            if (tempChar.equals("null")) character = 0;
            if (index == 0 && character == '0') break;
            data.add(new Pair<>(index, character));
        }
        return data;
    }

    private static void printTags(List<Pair<Integer, Character>> data) {
        for (Pair<Integer, Character> current : data) {
            System.out.println(current.getKey().toString() + " , " + current.getValue());
        }
        System.out.println("---------------------------------------");
    }
}
