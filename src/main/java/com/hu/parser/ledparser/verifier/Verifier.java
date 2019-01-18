package com.hu.parser.ledparser.verifier;

public interface Verifier {
	
	/**
	 * To verify the text content having necessary information or not.
	 * @param content
	 * @return
	 */
	public boolean verifiy(String content);

}
