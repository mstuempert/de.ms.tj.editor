package de.ms.tj.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.ms.tj.model.ICommand;

public class SyntaxXMLHandler extends DefaultHandler {
	
	private enum Tag {
		
		XML("xml", null), COMMANDS("commands", XML), COMMAND("command", COMMANDS);
		
		public String name;
		
		public Tag parent;
	
		Tag(String name, Tag parent) {
			this.name = name;
			this.parent = parent;
		}
		
		public static Tag getByName(String name) {
			for (Tag t : values()) {
				if (t.name.equals(name)) {
					return t;
				}
			}
			return null;
		}
		
	}
	
	private Tag currentTag;
	
	public List<ICommand> commands = new ArrayList<ICommand>();
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		Tag tag = Tag.getByName(qName);
		if (tag == null) {
			throw new SAXException("Unknown closing tag: " + qName);
		}
		
		this.currentTag = tag.parent;
		
	}
	
	@Override
	public void startDocument() throws SAXException {
		this.currentTag = null;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		Tag tag = Tag.getByName(qName);
		if (tag == null) {
			throw new SAXException("Unknown opening tag: " + qName);
		}
		
		if (((tag.parent == null) && (this.currentTag != null)) || ((tag.parent != null) && ((this.currentTag == null) || !tag.parent.equals(this.currentTag)))) {
			throw new SAXException("Opening tag is not valid at this position: " + qName);
		}
		
		if (Tag.COMMAND.equals(tag)) {
			String name = attributes.getValue("name");
			if (name == null) {
				throw new SAXException("Command-Tag is missing required name-attribute");
			}
			this.commands.add(new Command(name));
		}
		
		this.currentTag = tag;
		
	}

}
