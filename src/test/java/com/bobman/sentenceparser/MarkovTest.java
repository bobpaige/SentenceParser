package com.bobman.sentenceparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bobman.sentenceparser.bo.InputSentence;
import com.bobman.sentenceparser.bo.Molecule;
import com.bobman.sentenceparser.bo.WeightedMolecule;
import com.bobman.sentenceparser.repository.MoleculeRepository;
import com.bobman.sentenceparser.repository.WeightedMoleculeRepository;
import com.bobman.sentenceparser.strategy.MarkovStrategy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarkovTest {

  @Autowired
  private MarkovStrategy strat;

  @Autowired
  private MoleculeRepository mrepo;
  @Autowired
  private WeightedMoleculeRepository wmrepo;

  @Test
  public void testMarkovParser() {

    String content = "";
    String line = "";
    try (BufferedReader in = new BufferedReader(new FileReader("src/test/resources/file1.txt"))) {
      while ((line = in.readLine()) != null) {
        content += line;
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

    strat.parse(ins);

    Iterable<Molecule> mols = mrepo.findAll();
    int count = 0;
    for (Molecule m : mols) {
      count++;
      List<WeightedMolecule> wmols = wmrepo.findAllByParentWord(m);
      if (wmols != null && wmols.size() > 0) {
        System.out.println(m.getText());
        for (WeightedMolecule wm : wmols) {
          System.out.println("\t" + wm.getCount() + " " + wm.getText());
        }
      }
    }
    System.out.println(count + " molecules");
  }

}
