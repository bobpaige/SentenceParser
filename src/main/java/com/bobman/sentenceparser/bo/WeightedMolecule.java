package com.bobman.sentenceparser.bo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class WeightedMolecule extends Molecule {
  private Integer count;

  @ManyToOne
  private Molecule parentWord;

  public WeightedMolecule(String text) {
    super(null, text, text.length());
    setCount(1);
  }
}
