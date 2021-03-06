package ch.fhnw.apsifilter.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public final class AttributeWhitelistFilter extends AbstractAllNodeFilter {

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
	
	public void allow(@Nonnull String element, String... attributes) {
		whitelist.put(element, attributes);
	}
	
	@CheckReturnValue
	private String getLookupName(@Nonnull Node node) {
		if (node instanceof Element) {
            Element sourceEl = (Element) node;
            return sourceEl.tagName();
        } else if (node instanceof TextNode){ 
        	return "text";
        } else if (node instanceof DataNode) {
        	return "data";
        }
		
		return null;
	}
	
	@CheckReturnValue
	public static AttributeWhitelistFilter createDefault() {
		AttributeWhitelistFilter filter = new AttributeWhitelistFilter();
		
		filter.setAlwaysAllowed("style", "class", "id", "name");
		
		filter.allow("a", "href");
		filter.allow("img", "align", "alt", "height", "src", "title", "width");
//		filter.allow("link", "rel", "href", "type");
		filter.allow("style", "type");
		
		/* table tag attributes */
		filter.allow("table", "summary", "width");
		filter.allow("td", "abbr", "axis", "colspan", "rowspan", "width");
        filter.allow("th", "abbr", "axis", "colspan", "rowspan", "scope", "width");
        
        filter.allow("ul", "type");
        filter.allow("text", "text");
        filter.allow("data", "data");
		
		return filter;
	}
	
	@CheckReturnValue
	public static AttributeWhitelistFilter createStrict() {
		AttributeWhitelistFilter filter = new AttributeWhitelistFilter();
		
		filter.setAlwaysAllowed("class", "id", "name");
		
		/* table tag attributes */
		filter.allow("table", "summary", "width");
		filter.allow("td", "abbr", "axis", "colspan", "rowspan", "width");
        filter.allow("th", "abbr", "axis", "colspan", "rowspan", "scope", "width");
        
        filter.allow("ul", "type");
        filter.allow("text", "text");
        filter.allow("data", "data");
		
		return filter;
	}
	
	@CheckReturnValue
	public static AttributeWhitelistFilter createRemoveAllAttributes() {
		AttributeWhitelistFilter filter = new AttributeWhitelistFilter();
		return filter;
	}
}
