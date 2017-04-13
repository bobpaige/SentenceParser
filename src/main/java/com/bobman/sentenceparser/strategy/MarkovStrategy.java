package com.bobman.sentenceparser.strategy;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bobman.sentenceparser.bo.InputSentence;
import com.bobman.sentenceparser.bo.Molecule;
import com.bobman.sentenceparser.bo.WeightedMolecule;
import com.bobman.sentenceparser.repository.MoleculeRepository;
import com.bobman.sentenceparser.repository.WeightedMoleculeRepository;

@Component
public class MarkovStrategy extends ParseStrategy<InputSentence, Map<Molecule, WeightedMolecule>> {
  public static Molecule START = null;
  public static Molecule END = null;
  public static final String START_TEXT = "_START";
  public static final String END_TEXT = ".";

  public static int phraseLength = 3;

  public MarkovStrategy() {
  }

  @Autowired
  private MoleculeRepository mrepo;
  @Autowired
  private WeightedMoleculeRepository wmrepo;

  /**
   * Initialize start and end tokens
   */
  private synchronized void init() {
    if (START == null) {
      START = mrepo.findByText(START_TEXT);
      if (START == null) {
        START = Molecule.builder()
            .text(START_TEXT)
            .build();
        mrepo.save(START);
      }
    }

    if (END == null) {
      END = mrepo.findByText(END_TEXT);
      if (END == null) {
        END = Molecule.builder()
            .text(END_TEXT)
            .build();
        mrepo.save(END);
      }
    }
  }

  @Override
  public Map<Molecule, WeightedMolecule> parse(InputSentence input) {
    init();
    String[] words = input.getText()
        .split(" ");
    Molecule m = START;
    for (int i = 0; i < words.length; i++) {
      String nextNWords = nextNWords(words, i, phraseLength);
      addNextPhrase(m, nextNWords);
      String nextWord = words[i];
      m = mrepo.findByText(nextWord);
      if (m == null) {
        m = Molecule.builder()
            .text(nextWord)
            .build();
        mrepo.save(m);
      }
      if (nextNWords.endsWith(END_TEXT)) {
        m = START;
      }
    }
    return null;
  }

  /**
   * Adds this nextNWords to the Markov map for this input word. If the
   * nextNWords already exist, increments the counter on that phrase.
   * 
   * @param m
   * @param nextNWords
   */
  private void addNextPhrase(Molecule m, String nextNWords) {
    List<WeightedMolecule> nextWords = wmrepo.findAllByParentWord(m);
    if (nextWords == null || nextWords.size() == 0) {
      WeightedMolecule n = new WeightedMolecule(nextNWords);
      n.setParentWord(m);
      wmrepo.save(n);
    } else {
      boolean found = false;
      for (WeightedMolecule n : nextWords) {
        if (n.getText()
            .equals(nextNWords)) {
          n.setCount(n.getCount() + 1);
          wmrepo.save(n);
          found = true;
          break;
        }
      }
      if (!found) {
        WeightedMolecule n = new WeightedMolecule(nextNWords);
        n.setParentWord(m);
        nextWords.add(n);
      }
    }
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
  private String nextNWords(String[] words, int start, int count) {
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
