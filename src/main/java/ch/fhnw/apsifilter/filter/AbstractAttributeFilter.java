package ch.fhnw.apsifilter.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public abstract class AbstractAttributeFilter extends AbstractAllNodeFilter {
	private final Map<String, String[]> attributes;
	private final List<String> alwaysVisit;
	
	protected AbstractAttributeFilter() {
		attributes = new HashMap<String, String[]>();
		alwaysVisit = new ArrayList<String>();
	}
	
	protected final void alwaysVisit(String... attributes) {
		this.alwaysVisit.addAll(Arrays.asList(attributes));
	}
	
	protected final void toVisit(String elem, String... attributes) {
		this.attributes.put(elem, attributes);
	}
	
	@Override
	public final void filterNode(Node node) {
		if(node instanceof Element ) {
			final Element cur = (Element) node;
			final List<String> toConsider = attributes.get(cur.tagName()) != null
											? Arrays.asList(attributes.get(cur.tagName()))
											: new ArrayList<String>(0);
					
			for(Attribute attr : cur.attributes()) {
				if(alwaysVisit.contains(attr.getKey()) || 
				   toConsider.contains(attr.getKey()))
					
					filterAttribute(attr);
			}
		}
	}
	
	protected abstract void filterAttribute(Attribute attr);
	
}
