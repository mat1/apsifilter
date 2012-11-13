package ch.fhnw.apsifilter.filter.css;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import ch.fhnw.apsifilter.filter.AbstractNodeVisitor;

public class CssLinkFilter extends AbstractNodeVisitor {

	private final List<Node> toRemoveAfterVisit;
	
	private CssLinkFilter() {
		super();
		toRemoveAfterVisit = new ArrayList<Node>();
	}
	
	@Override
	protected void filterChosenNode(Node n) {
		if(n instanceof Element) {
			final Element elem = (Element) n;
			final String rel = elem.attr("rel");
			final String type = elem.attr("type");
			
			if(rel != null && rel.contains("stylesheet")
			||type != null && type.contains("css")) {
				toRemoveAfterVisit.add(n);
				return;
			}
		}
	}
	
	@Override
	public void visitCompleted() {
		for(Node n : toRemoveAfterVisit) n.remove();
	}
	
	public static CssLinkFilter createDefault() {
		CssLinkFilter filter = new CssLinkFilter();
		filter.shouldVisit("link");
		return filter;
	}
}
