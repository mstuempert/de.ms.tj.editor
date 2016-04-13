package de.ms.tj.model.impl;

import de.ms.tj.model.ISyntaxContainer;

public class SyntaxFactory {
	
	private static final String ID_PREFIX = "tj_syntax_";
	
	public ISyntaxContainer createSyntaxModel() {
		SyntaxContainer root = new SyntaxContainer(null, ID_PREFIX+"root", "The root of all evil");
		buildCommentSyntax(root);
		buildStringSyntax(root);
		buildCodeSyntax(root);
		return root;
	}
	
	private SyntaxElement buildCodeSyntax(SyntaxContainer parent) {
		SyntaxContainer codeRoot = new SyntaxContainer(parent, ID_PREFIX+"code", "Code");
		new SyntaxElement(codeRoot, ID_PREFIX+"command", "Command");
		buildTypeSyntax(codeRoot);
		return codeRoot;
	}
	
	private SyntaxElement buildCommentSyntax(SyntaxContainer parent) {
		SyntaxContainer commentRoot = new SyntaxContainer(parent, ID_PREFIX+"comment", "Comments");
		new SyntaxElement(commentRoot, ID_PREFIX+"slcomment", "Single Line Comment");
		new SyntaxElement(commentRoot, ID_PREFIX+"mlcomment", "Multi Line Comment");
		return commentRoot;
	}
	
	private SyntaxElement buildStringSyntax(SyntaxContainer parent) {
		SyntaxContainer stringRoot = new SyntaxContainer(parent, ID_PREFIX+"string", "String");
		new SyntaxElement(stringRoot, ID_PREFIX+"slstring", "Single Line String");
		new SyntaxElement(stringRoot, ID_PREFIX+"mlstring", "Multi Line String");
		return stringRoot;
	}
	
	private SyntaxElement buildTypeSyntax(SyntaxContainer parent) {
		SyntaxContainer typeRoot = new SyntaxContainer(parent, "tj_syntax_types", "Types");
		new SyntaxElement(typeRoot, ID_PREFIX+"integer", "Integer");
		new SyntaxElement(typeRoot, ID_PREFIX+"float", "Float");
		return typeRoot;
	}

}
