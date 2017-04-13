package com.bobman.sentenceparser.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bobman.sentenceparser.bo.InputSentence;

public class MarkovStringStrategy extends ParseStrategy<InputSentence, Map<String, List<String>>> {
  public static final String START_TEXT = "_START";
  public static final String END_TEXT = ".";

  @Override
  public Map<String, List<String>> parse(InputSentence input) {
    String[] words = input.getText()
        .split(" ");
    Map<String, List<String>> phraseMap = new HashMap<>();

    String nextWord = START_TEXT;
    for (int i = 0; i < words.length; i++) {
      String nextPhrase = getNextNWords(words, i, 3);
      List<String> matches = phraseMap.get(nextWord);
      if (matches == null) {
        matches = new ArrayList<>();
        phraseMap.put(nextWord, matches);
      }
      matches.add(nextPhrase);
      if (nextPhrase.endsWith(END_TEXT)) {
        // skip remaining words in this phrase and restart
        i += nextPhrase.split(" ").length;
        nextWord = START_TEXT;
      } else {
        nextWord = words[i];
      }
    }

    return phraseMap;
  }

  /**
   * Returns the next N words (or less) in the array, concatenated with spaces.
   * 
   * 
   * @param words
   * @param start
   * @param count
   * @return returns END token if no more words
   */
  private String getNextNWords(String[] words, int start, int count) {
    String result = "";
    int end = start + count;
    for (int i = start; i < end; i++) {
      if (result.length() > 0) {
        result += " ";
      }
      if (i >= words.length) {
        result += END_TEXT;
        break;
      }
      result += words[i];
      if (result.endsWith(END_TEXT)) {
        result += END_TEXT;
        break;
      }
    }
    return result;
  }
}
