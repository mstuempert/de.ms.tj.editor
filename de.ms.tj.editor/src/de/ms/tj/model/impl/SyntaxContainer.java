package de.ms.tj.model.impl;

import java.util.ArrayList;
import java.util.Collection;

import de.ms.tj.model.ISyntaxContainer;
import de.ms.tj.model.ISyntaxElement;

public class SyntaxContainer extends SyntaxElement implements ISyntaxContainer {

	private Collection<SyntaxElement> children;

	SyntaxContainer(SyntaxContainer parent, String id, String displayName) {
		super(parent, id, displayName);
	}
	
	@Override
	public ISyntaxElement[] getChildren() {
		return this.children.toArray(new ISyntaxElement[this.children.size()]);
	}
	
	@Override
	public boolean hasChildren() {
		return (this.children != null) && !this.children.isEmpty();
	}
	
	public boolean isLeaf() {
		return false;
	}
	
	void addChild(SyntaxElement e) {
		if (this.children == null) {
			this.children = new ArrayList<SyntaxElement>();
		}
		this.children.add(e);
	}

}
