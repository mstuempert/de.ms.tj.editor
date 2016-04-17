package de.ms.tj.model.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import de.ms.tj.model.ICommand;

public class SyntaxParser {
	
	private SyntaxXMLHandler handler = new SyntaxXMLHandler();
	
	ICommand[] getCommands() {
		return this.handler.commands.toArray(new ICommand[this.handler.commands.size()]);
	}
	
	void parse(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
		parser.parse(is, handler);
	}
	
}
