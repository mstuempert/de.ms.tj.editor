package de.ms.tj.model.impl;

import java.io.InputStream;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

import de.ms.tj.editor.internal.Activator;
import de.ms.tj.model.ICommand;
import de.ms.tj.model.ISyntaxBrowser;
import de.ms.tj.model.ISyntaxContainer;
import de.ms.tj.model.ISyntaxElement;

public class SyntaxBrowser implements ISyntaxBrowser {
	
	private ISyntaxContainer syntaxRoot;
	
	private ICommand[] commands;
	
	public SyntaxBrowser() {
		this.syntaxRoot = new SyntaxFactory().createSyntaxModel();
		try {
			InputStream is = FileLocator.openStream(Activator.getDefault().getBundle(), new Path("resources/syntax.xml"), false);
			SyntaxParser parser = new SyntaxParser();
			parser.parse(is);
			this.commands = parser.getCommands();
		} catch (Exception e) {
			Activator.logException(e);
		}
	}
	
	public ICommand getCommand(String name) {
		Assert.isNotNull(name);
		for (ICommand c : this.commands) {
			if (name.equals(c.getName())) {
				return c;
			}
		}
		return null;
	}
	
	public ICommand[] getCommands() {
		return this.commands;
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
