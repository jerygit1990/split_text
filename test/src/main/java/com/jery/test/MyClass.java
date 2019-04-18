package com.jery.test;

import java.util.ArrayList;
import java.util.List;

public class MyClass {

    static final int MAX_CHAR = 50;

    public static void main(String[] args) {
//        System.out.print("hi baby\n");
        String text = "I ca n't be li eve Twe et er now su pp or ts ch un ki ng  sd         ahi ai hih con ga nha ku, at buc mi nh lam a anh my me ssa ges, so I d on't have to do it my se lf.";
        for (int i = 0; i < 10; i ++) {
            text += text;
        }
//        String[] split = text.replaceAll("\\s+", " ").split(" ");
//        String[] split = text.split(" ");
//        String tt = "";
//        for(String t : split) {
//            tt += " " + t;
//            System.out.println(t);
//        }

        long time = System.currentTimeMillis();
        List<String> rs = splitMsg(reduceMaxChar(MAX_CHAR), text);

        for(String t : rs) {
            System.out.println(t);
        }

        long delta = System.currentTimeMillis() - time;
        System.out.println("time: " + delta);
    }

    /**
     *
     * Split to sentences to the array to be sure that length of each element in array including 'part indicator'
     * will be less than or equal max length and message will be only split on the whitespace.
     *
     * This is a recursive function, if the length of the 'part indicator' not enough its will reduce max length of the message and re-run
     * util passed all the rules.
     *
     * First of all we estimate total indicator will be < 10, second is < 100, 3nd < 1000, => n < pow(10, n)
     *
     * @param maxChar - the max character length excluding 'part indicator'
     * @param text - The chat content that user input
     * @return List of message or after split
     * @return null if has message contains of non-whitespace characters more than max
     * @return 1 element excluding 'part indicator' if @text has character less than 'origin max'
     */
    private static List<String> splitMsg(int maxChar, String text) {
        if(text.length() <= MAX_CHAR) {
            List<String> rs = new ArrayList<>();
            rs.add(text);
            return rs;
        }
        double estMaxIndicator = Math.pow (10, (MAX_CHAR - maxChar - 2) / 2);
//        System.out.println("reducedMaxChar: " + maxChar);
//        System.out.println("estMaxIndicator: " + estMaxIndicator);

        String[] words = text.split(" ");
        List<String> rs = new ArrayList<>();

        String sentences = "";
        for(int i = 0; i < words.length; i++) {
            String word = words[i];
            if(word.length() > maxChar) {
                System.out.print("Invalid message");
                return null;
            }
            String tWord = sentences.length() > 0 ? " " + word : word;
            if(sentences.length() + tWord.length() > maxChar) {
                rs.add(sentences);

                // The length for the 'part indicator' not enough we need to reduce the max characters of the sentences
                // to increase size of the indicator
                if(rs.size() >= estMaxIndicator) {
                    return splitMsg(reduceMaxChar(maxChar), text);
                }
                sentences = word;
            } else {
                sentences += tWord;
            }
            if(i == words.length - 1 && sentences.length() > 0) {
                rs.add(sentences);
            }
        }

        // insert 'part indicator' because now we know exactly the total of the indicator
        List<String> t = new ArrayList<>();
        for(int i = 0; i < rs.size(); i++) {
            t.add((i + 1) + "/" + rs.size() + " " + rs.get(i));
        }
        return t;
    }

    /**
     * Calculate the max length excluding the length of the 'part indicator',
     * min length of indicator = 4 characters ('part + / + total + ')
     * Each time reduce the max character length, its mean we increase the indicator length from 1, 2, 3, ... n characters (1)
     * and in the prefix we have 'part' and 'indicator' => 2 elements (2)
     * From (1) and (2) each time reduce mean we will reduce 2 length for the 'slot of part indicator'
     * @param max - current max length
     * @return the max length each message (No added 'part indicator' yet)
     */
    private static int reduceMaxChar(int max) {
        int partIndicatorLength = MAX_CHAR - max;
        // first reduce will be 4 characters = 'part + / + total + '
        if(partIndicatorLength == 0)
            return MAX_CHAR - 4;
       return MAX_CHAR - (partIndicatorLength + 2);
    }
}
