package de.ms.tj.model.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import de.ms.tj.model.IKeyword;

public class SyntaxParser {
	
	private SyntaxXMLHandler handler = new SyntaxXMLHandler();
	
	IKeyword[] getKeywords() {
		return this.handler.keywords.toArray(new IKeyword[this.handler.keywords.size()]);
	}
	
	void parse(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
		parser.parse(is, handler);
	}
	
}
