package com.bobman.sentenceparser.strategy;

/**
 * Base class for all parsers
 *
 */
public abstract class ParseStrategy<H, M> {

  abstract M parse(H input);

}
