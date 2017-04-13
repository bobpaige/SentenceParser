package com.bobman.sentenceparser.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bobman.sentenceparser.bo.Molecule;

/**
 * Base repository for Molecules
 *
 */
public interface MoleculeRepository extends PagingAndSortingRepository<Molecule, Long> {

  /**
   * From
   * https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-six-sorting/
   * 
   * @return
   */
  List<Molecule> findAll(Sort sort);

  Molecule findByText(String startText);
}
