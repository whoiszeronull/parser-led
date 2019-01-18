package com.hu.parser.ledparser.verifier;

import org.apache.commons.configuration2.ex.ConfigurationException;

public interface Verifier {
	
	/**
	 * To verify the text content having necessary information or not.
	 * @param content
	 * @return
	 * @throws  
	 */
	public boolean verifiy(String content);

}
