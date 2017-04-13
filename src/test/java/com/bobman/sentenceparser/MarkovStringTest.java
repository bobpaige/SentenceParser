package com.bobman.sentenceparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.bobman.sentenceparser.bo.InputSentence;
import com.bobman.sentenceparser.strategy.MarkovStringStrategy;

public class MarkovStringTest {

  private MarkovStringStrategy strat = new MarkovStringStrategy();

  @Test
  public void testMarkovStringParser() {

    String content = "";
    String line = "";
    try (BufferedReader in = new BufferedReader(new FileReader("src/test/resources/file1.txt"))) {
      while ((line = in.readLine()) != null) {
        content += line + " ";
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    InputSentence ins = new InputSentence();
    ins.setText(content);

    Map<String, List<String>> results = strat.parse(ins);

    for (String key : results.keySet()) {
      System.out.println(key);
      List<String> phrases = results.get(key);
      for (String s : phrases) {
        System.out.println("\t" + s);
      }
    }

    // make some sample sentences
    for (int i = 0; i < 5; i++) {
      String nextWord = MarkovStringStrategy.START_TEXT;
      String sentence = "";
      while (true) {
        List<String> phrases = results.get(nextWord);
        if (phrases == null || phrases.size() == 0) {
          break;
        }
        String phrase = phrases.get((int) (Math.random() * phrases.size()));
        sentence += phrase + " ";
        String[] words = phrase.split(" ");
        nextWord = words[words.length - 1];
        if (nextWord.endsWith(MarkovStringStrategy.END_TEXT)) {
          break;
        }
      }
      System.out.println(sentence);

    }
  }

}
