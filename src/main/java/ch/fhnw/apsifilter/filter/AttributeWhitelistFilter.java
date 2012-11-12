package ch.fhnw.apsifilter.filter;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import ch.fhnw.apsifilter.DomFilter;

public class AttributeWhitelistFilter implements DomFilter {

	private final Map<String, String[]> whitelist;
	
	private AttributeWhitelistFilter(Map<String, String[]> whitelist) {
		this.whitelist = whitelist;
	}
	
	public void filter(Document node) {

	}

	
	public static AttributeWhitelistFilter createDefault() {
		Map<String, String[]> whitelist = new HashMap<String,String[]>();
		
		
		return new AttributeWhitelistFilter(whitelist);
	}
}
