package ch.fhnw.apsifilter.filter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.jsoup.nodes.Attribute;

public class ProtocolFilter extends AbstractAttributeFilter {

	private final List<String> allowed;
	
	private ProtocolFilter() {
		allowed = new ArrayList<String>();
	}
	
	private void add(String protocol) {
		allowed.add(protocol);
	}
	
	@Override
	protected void filterAttribute(Attribute attr) {
		if(!isValid(attr.getValue())) {
			attr.setValue("");
		}
	}
	
	@CheckReturnValue
	private boolean isValid(@Nonnull String value) {
		try{
			URI uri = new URI(value);
			if(!allowed.contains(uri.getScheme())) return false;
			
			return true;
		}catch (URISyntaxException ex) {
			return false;			
		}
	}
	
	@CheckReturnValue
	public static ProtocolFilter createDefault() {
		final ProtocolFilter filter = new ProtocolFilter();
		
		filter.toVisit("img", "src");
		filter.toVisit("link", "href");
		filter.toVisit("a", "href");
		
		filter.add("http");
		filter.add("https");
		filter.add("ftp");
		return filter;
	}

}
