package de.ms.tj.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import de.ms.tj.editor.preferences.IPreferenceManager;
import de.ms.tj.model.ICommand;
import de.ms.tj.model.ISyntaxElement;
import de.ms.tj.model.ISyntaxElementLibrary;
import de.ms.tj.model.Syntax;

public class TjSourceViewerConfiguration extends SourceViewerConfiguration {

	private RuleBasedScanner codeScanner;

	private RuleBasedScanner commentScanner;

	private RuleBasedScanner stringScanner;
	
	private IPreferenceManager pManager;
	
	public TjSourceViewerConfiguration(IPreferenceManager pManager) {
		this.pManager = pManager;
	}
	
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return TjDocumentConfiguration.LEGAL_CONTENT_TYPES;
	}
	
	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		return TjDocumentConfiguration.TJ_PARTITIONING;
	}
	
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		
		PresentationReconciler reconciler = (PresentationReconciler) super.getPresentationReconciler(sourceViewer);
		
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getCodeScanner());
		reconciler.setDamager(dr, TjDocumentConfiguration.TJ_CODE_PARTITION);
		reconciler.setRepairer(dr, TjDocumentConfiguration.TJ_CODE_PARTITION);
		
		dr = new DefaultDamagerRepairer(getCommentScanner());
		reconciler.setDamager(dr, TjDocumentConfiguration.TJ_COMMENT_PARTITION);
		reconciler.setRepairer(dr, TjDocumentConfiguration.TJ_COMMENT_PARTITION);
		
		dr = new DefaultDamagerRepairer(getStringScanner());
		reconciler.setDamager(dr, TjDocumentConfiguration.TJ_STRING_PARTITION);
		reconciler.setRepairer(dr, TjDocumentConfiguration.TJ_STRING_PARTITION);
		
		return reconciler;
		
	}

	private ITokenScanner getCommentScanner() {
		if (this.commentScanner == null) {
			this.commentScanner = new RuleBasedScanner();
			ISyntaxElement sElement = Syntax.BROWSER.getElementById(ISyntaxElementLibrary.COMMENT);
			this.commentScanner.setDefaultReturnToken(new SyntaxConfigurationToken(this.pManager, sElement));
		}
		return this.commentScanner;
	}

	private ITokenScanner getCodeScanner() {
		if (this.codeScanner == null) {
			this.codeScanner = new RuleBasedScanner();
			List<IRule> rules = new ArrayList<IRule>();
			addCommandRules(rules);
			this.codeScanner.setRules(rules.toArray(new IRule[rules.size()]));
		}
		return this.codeScanner;
	}

	private ITokenScanner getStringScanner() {
		if (this.stringScanner == null) {
			this.stringScanner = new RuleBasedScanner();
			ISyntaxElement sElement = Syntax.BROWSER.getElementById(ISyntaxElementLibrary.STRING);
			this.stringScanner.setDefaultReturnToken(new SyntaxConfigurationToken(this.pManager, sElement));
		}
		return this.stringScanner;
	}
	
	private void addCommandRules(List<IRule> rules) {
		WordRule rule = new WordRule(new IWordDetector() {
			@Override
			public boolean isWordStart(char c) {
				return Character.isLetter(c);
			}
			
			@Override
			public boolean isWordPart(char c) {
				return Character.isLetter(c);
			}
		});
		SyntaxConfigurationToken token = new SyntaxConfigurationToken(pManager, Syntax.BROWSER.getElementById(ISyntaxElementLibrary.COMMAND));
		for (ICommand command : Syntax.BROWSER.getCommands()) {
			rule.addWord(command.getName(), token);
		}
		rules.add(rule);
	}
	
}
