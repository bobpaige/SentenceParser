package com.bobman.sentenceparser.strategy;

import java.util.List;

import com.bobman.sentenceparser.bo.InputSentence;
import com.bobman.sentenceparser.bo.Molecule;

/**
 * Base class for all parsers
 *
 */
public abstract class ParseStrategy {

  abstract  List<Molecule> parse(InputSentence input);

}
