package com.bobman.sentenceparser.strategy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bobman.sentenceparser.bo.InputSentence;
import com.bobman.sentenceparser.bo.Molecule;
import com.bobman.sentenceparser.bo.WeightedMolecule;
import com.bobman.sentenceparser.repository.MoleculeRepository;

public class MarkovStrategy extends ParseStrategy<InputSentence, Map<Molecule, WeightedMolecule>> {
  public static Molecule START = null;
  public static Molecule END = null;
  public static final String START_TEXT = "_START";
  public static final String END_TEXT = "_END";

  public MarkovStrategy() {
    init();
  }

  @Autowired
  private MoleculeRepository mrepo;

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
      END = mrepo.findByText(START_TEXT);
      if (START == null) {
        START = Molecule.builder()
            .text(START_TEXT)
            .build();
        mrepo.save(START);
      }
    }
  }

  @Override
  Map<Molecule, WeightedMolecule> parse(InputSentence input) {

    return null;
  }

}
