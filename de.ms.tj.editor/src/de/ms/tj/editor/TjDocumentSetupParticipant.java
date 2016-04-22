package de.ms.tj.editor;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;

public class TjDocumentSetupParticipant implements IDocumentSetupParticipant {

	@Override
	public void setup(IDocument document) {
		TjDocumentConfiguration configuration = new TjDocumentConfiguration();
		RuleBasedPartitionScanner scanner = new RuleBasedPartitionScanner();
		//scanner.setDefaultReturnToken(configuration.getDefaultToken());
		IPredicateRule[] rules = createRules(configuration);
		scanner.setPredicateRules(rules);
		IDocumentPartitioner partitioner = new FastPartitioner(scanner, TjDocumentConfiguration.LEGAL_CONTENT_TYPES);
		if (document instanceof IDocumentExtension3) {
			((IDocumentExtension3) document).setDocumentPartitioner(TjDocumentConfiguration.TJ_PARTITIONING, partitioner);
		} else {
			document.setDocumentPartitioner(partitioner);
		}
		partitioner.connect(document);
	}
	
	protected IPredicateRule[] createRules(TjDocumentConfiguration configuration) {
		return new IPredicateRule[] {
				new EndOfLineRule("#", configuration.getToken(TjDocumentConfiguration.TJ_COMMENT_PARTITION)),
				new MultiLineRule("/*", "*/", configuration.getToken(TjDocumentConfiguration.TJ_COMMENT_PARTITION))
		};
	}

}
