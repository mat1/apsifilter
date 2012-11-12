package ch.fhnw.apsifilter;


import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class Pipe {

	private final List<DomFilter> filters;
	
	private Pipe() {
		filters = new ArrayList<DomFilter>();
	}
	
	public void addFilter(DomFilter f) {
		filters.add(f);
	}
	
	public Document filter(String html) {
		final Document doc = Jsoup.parse(html);
		
		for(DomFilter f : filters) {
			f.filter(doc);
		}
		
		return doc;
	}
	
	public static Pipe createPipe() {
		return new Pipe();
	}
}
