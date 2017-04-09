package com.bobman.sentenceparser.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bobman.sentenceparser.bo.InputSentence;
import com.bobman.sentenceparser.bo.Molecule;

/**
 * Parses a sentence into words
 *
 */
public class WordStrategy extends ParseStrategy {

  @Override
  List<Molecule> parse(InputSentence input) {

    List<Molecule> values = Arrays.stream(input.getText()
        .split(" "))
        .map(s -> new Molecule(s))
        .collect(Collectors.toList());

    return values;
  }

}
