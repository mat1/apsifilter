package ch.fhnw.apsifilter.filter;

import java.util.ArrayList;
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
	private final List<String> allwaysAllowed;
	
	private AttributeWhitelistFilter() {
		this.whitelist = new HashMap<String, String[]>();
		this.allwaysAllowed = new ArrayList<String>();
	}
	
	@Override
	protected void filterNode(Node node) {
		/* filter */
		final List<String> allowed = Arrays.asList(whitelist.get(getLookupName(node)) != null  
													? whitelist.get(getLookupName(node)) 
													: new String[0]);

		for(Attribute attr: node.attributes()) {
			if(allwaysAllowed.contains(attr.getKey())) continue;
			if(!allowed.contains(attr.getKey()))
				node.removeAttr(attr.getKey());
		}
	}
	
	public void setAlwaysAllowed(String...attributes) {
		allwaysAllowed.clear();
		allwaysAllowed.addAll(Arrays.asList(attributes));
	}
	
	public void allow(String element, String... attributes) {
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
		
		filter.setAlwaysAllowed("style", "class", "id", "name");
		
		filter.allow("a", "href");
		filter.allow("img", "align", "alt", "height", "src", "title", "width");
		filter.allow("link", "rel", "href", "type");
		
		/* table tag attributes */
		filter.allow("table", "summary", "width");
		filter.allow("td", "abbr", "axis", "colspan", "rowspan", "width");
        filter.allow("th", "abbr", "axis", "colspan", "rowspan", "scope", "width");
        
        filter.allow("ul", "type");
        filter.allow("text", "text");
		
		return filter;
	}
	
	public static AttributeWhitelistFilter createRemoveAllAttributes() {
		AttributeWhitelistFilter filter = new AttributeWhitelistFilter();
		return filter;
	}
}
