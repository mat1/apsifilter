package ch.fhnw.apsifilter.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import ch.fhnw.apsifilter.DomFilter;

public class TagWhitelistFilter implements DomFilter {

	private final Set<String> whitelist;
	
	private TagWhitelistFilter() {
		this.whitelist = new HashSet<String>();
	}
	
	public void filter(Document node) {
		filterNode(node);
	}
	
	private void filterNode(Node node) {
		boolean deleted = false;
		if(!whitelist.contains(getLookupName(node))){
			if(node.parent() != null){
				node.remove();
				deleted = true;
			}
		}
		if(!deleted) {
			final List<Node> children = new ArrayList<Node>(node.childNodes());
			for(Node child : children) filterNode(child);
		}
	}
	
	public void add(String... tagNames){
		for(String tagName : tagNames){
			whitelist.add(tagName);
		}
	}
	
	private String getLookupName(Node node) {
		if (node instanceof Element) {
            Element sourceEl = (Element) node;
            return sourceEl.tagName();
        }
		
		return null;
	}
	
	public static TagWhitelistFilter createDefault() {
		TagWhitelistFilter filter = new TagWhitelistFilter();
	    filter.add("html", "head", "body", "a", "blockquote", "br", "caption", "col",
	               "colgroup", "div", "em", "h1", "h2", "h3", "h4", "h5", "h6",
	               "i", "img", "li", "ol", "ul", "p", "pre", "small", "strike", "strong",
	               "table", "tbody", "td", "tfoot", "th", "thead", "tr",
	               "div", "span");
		
		return filter;
	}

}