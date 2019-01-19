package com.hu.parser.ledparser.verifier.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.junit.platform.commons.util.StringUtils;

import com.hu.parser.ledparser.pojo.ParserConfig;
import com.hu.parser.ledparser.verifier.Verifier;

/**
 * HtmlTableSpecVerifier is to determin whether the passed string content having
 * specification informations in the html table format.
 * 1.There are tables.
 * 2.The specifications are listed in the tables
 * 3.The specifications listed in one talbe are more than 5 specs of the LedPanel
 * 
 * @author shunn
 *
 */
public class HtmlTableSpecsVerifier implements Verifier {
	
	private int specCountLimit = 5;

	@Override
	public boolean verifiy(String content) {
		
		//clean the string contet, to avoid the XSS attack
		String html = Jsoup.clean(content, new Whitelist());
		
		Document doc = Jsoup.parse(html);
		Elements tables = doc.getElementsByTag("table");
		for (Element t : tables) {
			String tableHtml = t.html();
			
			//verify the selcted table containing more than 5 specs or not.
			if(verifyOneTable(tableHtml)) {
				return true;
			}
		}
		
		
		return false;
	}

	private boolean verifyOneTable(String tableHtml) {
		//the actual spec count that found in this tablehtml fragment.
		int specCount = 0;
		
		Map<String, String> specMap = ParserConfig.getSpecMap();
		Collection<String> searchKeyWords = specMap.values();
		Iterator<String> it = searchKeyWords.iterator();
		while(it.hasNext()) {
			String specKeyWords = it.next();
			if(StringUtils.isNotBlank(specKeyWords)) {
				//to replace and the Chinese version "，" as english comma ",";
				specKeyWords = specKeyWords.replace("，",  ",");
				String[] specKeyWordArray = specKeyWords.split(",");
				for(int i=0; i< specKeyWordArray.length; i++) {
					if(tableHtml.contains(specKeyWordArray[i])) {
						specCount++;
						break;
					}
				}
			}
			
			//if the spec info found on this table html, then it means this is a table that has necessary product infomation, then can stop searching more and decide this is a product info talbeHtml;
			if(specCount > this.specCountLimit) {
				return true;
			}
		}
		return false;
	}
	
}
