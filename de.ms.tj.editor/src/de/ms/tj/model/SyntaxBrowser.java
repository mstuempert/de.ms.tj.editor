package de.ms.tj.model;

import de.ms.tj.model.impl.SyntaxFactory;

public class SyntaxBrowser {
	
	private static SyntaxBrowser INSTANCE;

	private ISyntaxContainer syntaxRoot;
	
	private SyntaxBrowser() {
		this.syntaxRoot = new SyntaxFactory().createSyntaxModel();
	}
	
	public static final SyntaxBrowser getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SyntaxBrowser();
		}
		return INSTANCE;
	}
	
	public ISyntaxContainer getSyntaxRoot() {
		return this.syntaxRoot;
	}

}
