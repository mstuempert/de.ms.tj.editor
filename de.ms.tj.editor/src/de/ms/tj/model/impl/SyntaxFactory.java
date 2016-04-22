package de.ms.tj.model.impl;

import de.ms.tj.model.ISyntaxContainer;
import de.ms.tj.model.ISyntaxElementLibrary;

public class SyntaxFactory implements ISyntaxElementLibrary {
	
	public ISyntaxContainer createSyntaxModel() {
		SyntaxContainer root = new SyntaxContainer(null, ROOT, "The root of all evil");
		buildCommentSyntax(root);
		buildCodeSyntax(root);
		return root;
	}
	
	private SyntaxElement buildCodeSyntax(SyntaxContainer parent) {
		SyntaxContainer codeRoot = new SyntaxContainer(parent, CODE, "Code");
		new SyntaxElement(codeRoot, COMMAND, "Command");
		buildStringSyntax(codeRoot);
		buildTypeSyntax(codeRoot);
		return codeRoot;
	}
	
	private SyntaxElement buildCommentSyntax(SyntaxContainer parent) {
		SyntaxContainer commentRoot = new SyntaxContainer(parent, COMMENT, "Comments");
		new SyntaxElement(commentRoot, COMMENT_SINGLE_LINE, "Single Line Comment");
		new SyntaxElement(commentRoot, COMMENT_MULTI_LINE+"mlcomment", "Multi Line Comment");
		return commentRoot;
	}
	
	private SyntaxElement buildStringSyntax(SyntaxContainer parent) {
		SyntaxContainer stringRoot = new SyntaxContainer(parent, STRING, "String");
		new SyntaxElement(stringRoot, STRING_SINGLE_LINE, "Single Line String");
		new SyntaxElement(stringRoot, STRING_MULTI_LINE, "Multi Line String");
		return stringRoot;
	}
	
	private SyntaxElement buildTypeSyntax(SyntaxContainer parent) {
		SyntaxContainer typeRoot = new SyntaxContainer(parent, TYPE, "Types");
		new SyntaxElement(typeRoot, TYPE_INTEGER, "Integer");
		new SyntaxElement(typeRoot, TYPE_FLOAT, "Float");
		return typeRoot;
	}

}
