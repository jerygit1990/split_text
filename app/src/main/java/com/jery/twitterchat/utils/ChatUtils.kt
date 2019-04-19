package com.jery.twitterchat.utils

import java.util.ArrayList

class ChatUtils {

    companion object {
        const val MAX_CHAR = 50


        /**
         * First time we need to reduce max character for 'part indicator' size
         * @param text - the chat content that user input
         * @return func below 'splitMsg(maxChar: Int, text: String)'
         */
        fun splitMsg(text: String) : List<String>? {
            return splitMsg(
                reduceMaxChar(
                    MAX_CHAR
                ), text
            )
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
        private fun splitMsg(maxChar: Int, text: String): List<String>? {
            if (text.length <= MAX_CHAR) {
                val rs = ArrayList<String>()
                rs.add(text)
                return rs
            }
            val estMaxIndicator = Math.pow(10.0, ((MAX_CHAR - maxChar - 2) / 2).toDouble())
            //        System.out.println("reducedMaxChar: " + maxChar);
            //        System.out.println("estMaxIndicator: " + estMaxIndicator);

            val words = text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val rs = ArrayList<String>()

            var sentences = ""
            for (i in words.indices) {
                val word = words[i]
                if (word.length > maxChar) {
                    print("Invalid message")
                    return null
                }
                val tWord = if (sentences.isNotEmpty()) " $word" else word
                if (sentences.length + tWord.length > maxChar) {
                    rs.add(sentences)

                    // The length for the 'part indicator' not enough we need to reduce the max characters of the sentences
                    // to increase size of the indicator
                    if (rs.size >= estMaxIndicator) {
                        return splitMsg(
                            reduceMaxChar(
                                maxChar
                            ), text
                        )
                    }
                    sentences = word
                } else {
                    sentences += tWord
                }

                // when finish loop, check if has 'sentences' we will add to result list
                if (i == words.size - 1 && sentences.isNotEmpty()) {
                    rs.add(sentences)
                }
            }

            // insert 'part indicator' because now we know exactly the total of the indicator
            val t = ArrayList<String>()
            for (i in rs.indices) {
                t.add((i + 1).toString() + "/" + rs.size + " " + rs[i])
            }
            return t
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
        private fun reduceMaxChar(max: Int): Int {
            val partIndicatorLength = MAX_CHAR - max
            // first reduce will be 4 characters = 'part + / + total + '
            return if (partIndicatorLength == 0) MAX_CHAR - 4 else MAX_CHAR - (partIndicatorLength + 2)
        }
    }
}