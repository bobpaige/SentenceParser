package com.bobman.sentenceparser.repository;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.bobman.sentenceparser.bo.Molecule;

/**
 * Test the Molecule repository
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MoleculeRepositoryTest {
  @Autowired
  private MoleculeRepository mrepo;

  /**
   * Test querying by length
   */
  @Test
  public void testFindByLength() {
    List<Molecule> insertedTestValues = new ArrayList<>();
    if (mrepo.count() == 0) {
      String testValue = "test";
      for (int i = 0; i < 10; i++) {
        Molecule m = new Molecule(testValue);
        mrepo.save(m);
        insertedTestValues.add(m);
        testValue += String.valueOf(i);
      }
    }

    try {
      List<Molecule> values = mrepo.findAll(new Sort(Direction.DESC, "Length"));
      int prevLength = Integer.MAX_VALUE;
      for (Molecule m : values) {
        if (m.getLength() > prevLength) {
          fail("Length should be increasing: found " + m.getLength());
        }
        prevLength = m.getLength();
      }
    } finally {
      insertedTestValues.forEach(m -> mrepo.delete(m));
    }
  }
}
