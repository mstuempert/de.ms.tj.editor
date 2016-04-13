package de.ms.tj.model.impl;

import de.ms.tj.model.ISyntaxContainer;
import de.ms.tj.model.ISyntaxElement;

class SyntaxElement implements ISyntaxElement {
	
	private SyntaxContainer parent;
	
	private String id;
	
	private String displayName;
	
	SyntaxElement(SyntaxContainer parent, String id, String displayName) {
		this.parent = parent;
		this.id = id;
		this.displayName = displayName;
		if (this.parent != null) {
			this.parent.addChild(this);
		}
	}

	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public ISyntaxContainer getParent() {
		return this.parent;
	}
	
	public boolean isLeaf() {
		return true;
	}

}
