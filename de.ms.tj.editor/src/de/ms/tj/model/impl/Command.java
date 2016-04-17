package de.ms.tj.model.impl;

import de.ms.tj.model.ICommand;

class Command implements ICommand {

	private String name;
	
	Command(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "Command[name=\"" + this.name + "\"]";
	}

}
