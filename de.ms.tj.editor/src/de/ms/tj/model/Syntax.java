package de.ms.tj.model;

import de.ms.tj.model.impl.SyntaxBrowser;

public class Syntax implements ISyntaxElementLibrary {
	
	public static final ISyntaxBrowser BROWSER = new SyntaxBrowser();

}
