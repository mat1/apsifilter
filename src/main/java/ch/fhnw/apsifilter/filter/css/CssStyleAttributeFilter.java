package ch.fhnw.apsifilter.filter.css;

import org.jsoup.nodes.Attribute;

import ch.fhnw.apsifilter.filter.AbstractAttributeFilter;

public class CssStyleAttributeFilter extends AbstractAttributeFilter {
	
	private final CssCleaner cleaner;
	
	private CssStyleAttributeFilter(CssCleaner cleaner) {
		super();
		this.cleaner = cleaner;
	}
	
	@Override
	protected void filterAttribute(Attribute attr) {
		attr.setValue(cleaner.getCleanedCss(attr.getValue()));
	}

	public static CssStyleAttributeFilter createDefault() {
		CssStyleAttributeFilter filter = new CssStyleAttributeFilter(CssCleaner.createDefault());
		filter.alwaysVisit("style");
		return filter;
	}
	
	public static CssStyleAttributeFilter createLazy() {
		CssStyleAttributeFilter filter = new CssStyleAttributeFilter(CssCleaner.createLazy());
		filter.alwaysVisit("style");
		return filter;
	}
}
