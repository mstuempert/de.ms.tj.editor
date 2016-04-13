package de.ms.tj.model;

public interface ISyntaxElement {
	
	String getDisplayName();
	
	String getId();
	
	ISyntaxContainer getParent();
	
	boolean isLeaf();

}
