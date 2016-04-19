package de.ms.tj.model;

public interface ISyntaxBrowser {
	
	IKeyword getKeyword(String name);
	
	IKeyword[] getKeywords();
	
	ISyntaxContainer getSyntaxRoot();
	
	ISyntaxElement getElementById(String id);

}
