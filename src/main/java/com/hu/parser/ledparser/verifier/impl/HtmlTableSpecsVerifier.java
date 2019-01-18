package com.hu.parser.ledparser.verifier.impl;

import java.util.List;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.hu.parser.ledparser.verifier.Verifier;

/**
 * HtmlTableSpecVerifier is to determin whether the passed string content having
 * specification infomations in the html table format.
 * 
 * @author shunn
 *
 */
public class HtmlTableSpecsVerifier implements Verifier {

	@Override
	public boolean verifiy(String content) {

		return false;
	}

	public List<String> getVerifiers() {
		Configurations configs = new Configurations();
		XMLConfiguration config;
		try {
			config = configs.xml("./config/parser-config.xml");
			return config.getList(String.class, "verifiers.verifier");
			
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

}
