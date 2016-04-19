package de.ms.tj.model.impl;

import java.io.InputStream;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

import de.ms.tj.editor.internal.Activator;
import de.ms.tj.model.IKeyword;
import de.ms.tj.model.ISyntaxBrowser;
import de.ms.tj.model.ISyntaxContainer;
import de.ms.tj.model.ISyntaxElement;

public class SyntaxBrowser implements ISyntaxBrowser {
	
	private ISyntaxContainer syntaxRoot;
	
	private IKeyword[] keywords;
	
	public SyntaxBrowser() {
		this.syntaxRoot = new SyntaxFactory().createSyntaxModel();
		try {
			InputStream is = FileLocator.openStream(Activator.getDefault().getBundle(), new Path("resources/syntax.xml"), false);
			SyntaxParser parser = new SyntaxParser();
			parser.parse(is);
			this.keywords = parser.getKeywords();
		} catch (Exception e) {
			Activator.logException(e);
		}
	}
	
	public IKeyword getKeyword(String name) {
		Assert.isNotNull(name);
		for (IKeyword c : this.keywords) {
			if (name.equals(c.getName())) {
				return c;
			}
		}
		return null;
	}
	
	public IKeyword[] getKeywords() {
		return this.keywords;
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
