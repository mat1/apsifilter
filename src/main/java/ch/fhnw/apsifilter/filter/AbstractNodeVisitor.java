package ch.fhnw.apsifilter.filter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public abstract class AbstractNodeVisitor extends AbstractAllNodeFilter {

	private final List<String> nodesToVisit;
	
	protected AbstractNodeVisitor() {
		nodesToVisit = new ArrayList<String>();
	}

	protected abstract void filterChosenNode(@Nonnull Node n);
	
	
	public final void shouldVisit(@Nonnull String name) {
		nodesToVisit.add(name);
	}
	
	@Override
	protected final void filterNode(@Nonnull Node n) {
		if(shouldVisit(n))
			filterChosenNode(n);
	}
	
	private boolean shouldVisit(@Nonnull Node n) {
		if(n instanceof Element) {
			Element e = (Element) n;
			return nodesToVisit.contains(e.tagName());
		}
		return false;
	}

}
