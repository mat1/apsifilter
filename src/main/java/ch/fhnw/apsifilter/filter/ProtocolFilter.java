package ch.fhnw.apsifilter.filter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
	
	private boolean isValid(String value) {
		try{
			URI uri = new URI(value);
			if(!allowed.contains(uri.getScheme())) return false;
			
			return true;
		}catch (URISyntaxException ex) {
			return false;			
		}
	}
	
	public static ProtocolFilter createDefault() {
		final ProtocolFilter filter = new ProtocolFilter();
		
		filter.add("img", "src");
		filter.add("link", "href");
		filter.add("a", "href");
		
		filter.add("http");
		filter.add("https");
		filter.add("ftp");
		return filter;
	}

}
