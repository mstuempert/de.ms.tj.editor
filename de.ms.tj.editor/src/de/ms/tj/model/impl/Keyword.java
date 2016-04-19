package de.ms.tj.model.impl;

import de.ms.tj.model.IKeyword;

class Keyword implements IKeyword {

	private String name;
	
	Keyword(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "Property[name=\"" + this.name + "\"]";
	}

}
