package ch.fhnw.apsifilter.filter.css;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import ch.fhnw.apsifilter.filter.AbstractNodeVisitor;

public class CssInlineFilter extends AbstractNodeVisitor {

	private final CssCleaner cleaner;
	
	private CssInlineFilter(@Nonnull CssCleaner cleaner) {
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
	
	@CheckReturnValue
	public static CssInlineFilter createDefault() {
		final CssInlineFilter filter = new CssInlineFilter(CssCleaner.createDefault());
		filter.shouldVisit("style");
		return filter;
	}
	
	@CheckReturnValue
	public static CssInlineFilter createLazy() {
		final CssInlineFilter filter = new CssInlineFilter(CssCleaner.createLazy());
		filter.shouldVisit("style");
		return filter;
	}
	
}
