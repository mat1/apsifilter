package ch.fhnw.apsifilter.filter.css;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import ch.fhnw.apsifilter.filter.AbstractNodeVisitor;

public class CssInlineFilter extends AbstractNodeVisitor {

	private final CssCleaner cleaner;
	
	private CssInlineFilter(CssCleaner cleaner) {
		super();
		this.cleaner = cleaner;
	}
	
	@Override
	protected void filterChosenNode(Node n) {
		if(n instanceof Element) {
			for(Node cur : n.childNodes())
				filterChosenNode(cur);
		} else if(n instanceof DataNode) {
			DataNode cur = (DataNode) n;
			cur.setWholeData(cleaner.getCleanedCss(cur.getWholeData()));
		}
	}
	
	public static CssInlineFilter createDefault() {
		final CssInlineFilter filter = new CssInlineFilter(CssCleaner.createDefault());
		filter.shouldVisit("style");
		return filter;
	}
	
	public static CssInlineFilter createLazy() {
		final CssInlineFilter filter = new CssInlineFilter(CssCleaner.createLazy());
		filter.shouldVisit("style");
		return filter;
	}
	
}
