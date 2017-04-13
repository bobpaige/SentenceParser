package com.bobman.sentenceparser.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bobman.sentenceparser.bo.Molecule;
import com.bobman.sentenceparser.bo.WeightedMolecule;

public interface WeightedMoleculeRepository extends PagingAndSortingRepository<WeightedMolecule, Long> {
  List<WeightedMolecule> findAllByParentWord(Molecule m);
}
