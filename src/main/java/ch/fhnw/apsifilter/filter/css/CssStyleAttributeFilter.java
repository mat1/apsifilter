package ch.fhnw.apsifilter.filter.css;

import org.jsoup.nodes.Attribute;

import ch.fhnw.apsifilter.filter.AbstractAttributeFilter;

public class CssStyleAttributeFilter extends AbstractAttributeFilter {
	
	private final CssCleaner cleaner;
	
	private CssStyleAttributeFilter() {
		super();
		super.alwaysVisit("style");
		
		cleaner = CssCleaner.createDefault();
	}
	
	@Override
	protected void filterAttribute(Attribute attr) {
		final String value = attr.getValue();
		
		if(cleaner.mayBeMalicious(value)) {
			attr.setValue("");
		}
	}

	public static CssStyleAttributeFilter createDefault() {
		return new CssStyleAttributeFilter();
	}
}
