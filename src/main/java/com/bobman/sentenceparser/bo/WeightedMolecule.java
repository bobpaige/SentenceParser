package com.bobman.sentenceparser.bo;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class WeightedMolecule extends Molecule {
  private int count;
}
