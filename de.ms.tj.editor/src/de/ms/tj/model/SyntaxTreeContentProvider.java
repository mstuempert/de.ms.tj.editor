package de.ms.tj.model;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SyntaxTreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return parentElement instanceof ISyntaxContainer
				? ((ISyntaxContainer)  parentElement).getChildren()
						: new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return element instanceof ISyntaxElement
				? ((ISyntaxElement)  element).getParent()
						: null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return (element instanceof ISyntaxContainer) && ((ISyntaxContainer) element).hasChildren();
	}

}
