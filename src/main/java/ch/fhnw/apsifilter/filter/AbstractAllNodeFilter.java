package ch.fhnw.apsifilter.filter;

import javax.annotation.Nonnull;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import ch.fhnw.apsifilter.DomFilter;

public abstract class AbstractAllNodeFilter implements DomFilter {
	protected AbstractAllNodeFilter() {	}
	
	public final void filter(Document node) {
		new NodeTraversor(new NodeVisitor() {
			public void tail(Node node, int depth) { }
			
			public void head(Node node, int depth) {
				filterNode(node);
			}
		}).traverse(node);
		visitCompleted();
	}
	
	protected void visitCompleted() {}
	
	protected abstract void filterNode(@Nonnull Node n);
	
}
