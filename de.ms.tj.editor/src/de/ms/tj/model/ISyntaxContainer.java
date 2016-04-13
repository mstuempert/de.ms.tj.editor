package de.ms.tj.model;

public interface ISyntaxContainer extends ISyntaxElement {
	
	ISyntaxElement[] getChildren();
	
	boolean hasChildren();

}
