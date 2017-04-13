package com.bobman.sentenceparser.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Smallest, reusable parsed string
 *
 */
@Data
@Entity
@NoArgsConstructor(force = true)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Molecule {

  /**
   * Constructor
   * 
   * @param s
   */
  public Molecule(String s) {
    setText(s);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String text;

  private Integer length;

  /**
   * Set the length when setting text
   * 
   * @param s
   */
  public void setText(String s) {
    this.text = s;
    setLength(s.length());
  }

  /**
   * length of the text in this molecule
   * 
   * @return
   */
  public Integer getLength() {
    return text == null
        ? 0
        : text.length();
  }
}
