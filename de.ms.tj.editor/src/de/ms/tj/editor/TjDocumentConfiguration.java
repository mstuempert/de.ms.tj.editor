package de.ms.tj.editor;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class TjDocumentConfiguration {
	
	public static final String TJ_PARTITIONING = "__tj_partitioning";
	
	public static final String TJ_CODE_PARTITION = "JD_CODE_PARTITION";
	
	public static final String TJ_COMMENT_PARTITION = "JD_COMMENT_PARTITION";
	
	public static final String TJ_STRING_PARTITION = "JD_STRING_PARTITION";
	
	private static final IToken TJ_CODE_TOKEN = new Token(TJ_CODE_PARTITION);
	
	private static final IToken TJ_COMMENT_TOKEN = new Token(TJ_COMMENT_PARTITION);
	
	private static final IToken TJ_STRING_TOKEN = new Token(TJ_STRING_PARTITION);

	public static final String[] LEGAL_CONTENT_TYPES = new String[] {
			TJ_CODE_PARTITION,
			TJ_COMMENT_PARTITION,
			TJ_STRING_PARTITION
	};
	
	public IToken getDefaultToken() {
		return getToken(TJ_CODE_PARTITION);
	}
	
	public IToken getToken(String partitionType) {
		IToken token = null;
		switch (partitionType) {
		case TJ_CODE_PARTITION:
			token = TJ_CODE_TOKEN;
			break;
		case TJ_COMMENT_PARTITION:
			token = TJ_COMMENT_TOKEN;
			break;
		case TJ_STRING_PARTITION:
			token = TJ_STRING_TOKEN;
			break;
		}
		return token;
	}

}
