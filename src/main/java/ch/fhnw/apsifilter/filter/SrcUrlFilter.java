package ch.fhnw.apsifilter.filter;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.jsoup.nodes.Attribute;

public final class SrcUrlFilter extends AbstractAttributeFilter {
	
	private SrcUrlFilter() {	
	}
	
	@Override
	protected void filterAttribute(@Nonnull Attribute attr) {
		attr.setValue(makeValid(attr.getValue()));
	}

	private String makeValid(@Nonnull String value) {
		try{
			final URI uri = new URI(value);
			final String queryPart = uri.getQuery();
			
			if(queryPart == null) return value;
			
			return value.replace("?"+queryPart, "");
		}catch (URISyntaxException ex) {
			return "";			
		}
	}
	
	@CheckReturnValue
	public static SrcUrlFilter createDefault() {
		SrcUrlFilter filter = new SrcUrlFilter();
		filter.alwaysVisit("src");
		return filter;
	}
}
