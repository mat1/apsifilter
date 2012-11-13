package ch.fhnw.apsifilter.filter.css;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.jsoup.nodes.Attribute;

import ch.fhnw.apsifilter.filter.AbstractAttributeFilter;

public class CssStyleAttributeFilter extends AbstractAttributeFilter {
	
	private final CssCleaner cleaner;
	
	private CssStyleAttributeFilter(@Nonnull CssCleaner cleaner) {
		super();
		this.cleaner = cleaner;
	}
	
	@Override
	protected void filterAttribute(@Nonnull Attribute attr) {
		attr.setValue(cleaner.getCleanedCss(attr.getValue()));
	}
	
	@CheckReturnValue
	public static CssStyleAttributeFilter createDefault() {
		CssStyleAttributeFilter filter = new CssStyleAttributeFilter(CssCleaner.createDefault());
		filter.alwaysVisit("style");
		return filter;
	}
	
	@CheckReturnValue
	public static CssStyleAttributeFilter createLazy() {
		CssStyleAttributeFilter filter = new CssStyleAttributeFilter(CssCleaner.createLazy());
		filter.alwaysVisit("style");
		return filter;
	}
}
