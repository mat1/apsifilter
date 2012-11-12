package ch.fhnw.apsifilter.filter.css;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import ch.fhnw.apsifilter.filter.AbstractNodeVisitor;

public class CssInlineFilter extends AbstractNodeVisitor {

	private final CssCleaner cleaner;
	
	private CssInlineFilter() {
		super();
		cleaner = CssCleaner.createDefault();
	}
	
	@Override
	protected void filterChosenNode(Node n) {
		final Element elem;
		if(n instanceof Element) {
			elem = (Element) n;
			
			if(cleaner.mayBeMalicious(elem.text())) {
				elem.remove();
			}
		}
	}
	
	public static CssInlineFilter createDefault() {
		final CssInlineFilter filter = new CssInlineFilter();
		filter.shouldVisit("style");
		
		return filter;
	}
	
}
