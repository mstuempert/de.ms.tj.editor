package de.ms.tj.model;

import de.ms.tj.model.impl.SyntaxFactory;

public class SyntaxBrowser {
	
	public static SyntaxBrowser INSTANCE = new SyntaxBrowser();

	private ISyntaxContainer syntaxRoot;
	
	private SyntaxBrowser() {
		this.syntaxRoot = new SyntaxFactory().createSyntaxModel();
	}
	
	public ISyntaxContainer getSyntaxRoot() {
		return this.syntaxRoot;
	}
	
	public ISyntaxElement getElementById(String id) {
		return findElementById(getSyntaxRoot(), id);
	}
	
	private ISyntaxElement findElementById(ISyntaxElement e, String id) {
		if (e.getId().equals(id)) {
			return e;
		}
		if (e instanceof ISyntaxContainer) {
			ISyntaxElement[] children = ((ISyntaxContainer) e).getChildren();
			if (children != null) {
				for (ISyntaxElement c : children) {
					ISyntaxElement r = findElementById(c, id);
					if (r != null) {
						return r;
					}
				}
			}
		}
		return null;
	}

}
