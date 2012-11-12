package ch.fhnw.apsifilter.filter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public class AttributeWhitelistFilter extends AbstractAllNodeFilter {

	private final Map<String, String[]> whitelist;
	
	private AttributeWhitelistFilter() {
		this.whitelist = new HashMap<String, String[]>();
	}
	
	@Override
	protected void filterNode(Node node) {
		/* filter */
		final List<String> allowed = Arrays.asList(whitelist.get(getLookupName(node)) != null  
																	? whitelist.get(getLookupName(node)) 
																	: new String[0]);
		
		for(Attribute attr: node.attributes()) {
			if(!allowed.contains(attr.getKey()))
				node.removeAttr(attr.getKey());
		}
	}
	
	public void add(String element, String... attributes) {
		whitelist.put(element, attributes);
	}
	
	private String getLookupName(Node node) {
		if (node instanceof Element) {
            Element sourceEl = (Element) node;
            return sourceEl.tagName();
        } else { 
        	if(node instanceof TextNode) {
	        	return "text";
        	}
        }
		
		return null;
	}
	
	public static AttributeWhitelistFilter createDefault() {
		AttributeWhitelistFilter filter = new AttributeWhitelistFilter();
		
//		filter.add("body", "onload");
		
		return filter;
	}
}
