package ch.fhnw.apsifilter;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class Pipe {

	private final List<DomFilter> filters;
	
	private Pipe() {
		filters = new ArrayList<DomFilter>();
	}
	
	public void addFilter(@Nonnull DomFilter f) {
		filters.add(f);
	}
	
	public Document filter(@Nonnull String html) {
		final Document doc = Jsoup.parse(html);
		
		for(DomFilter f : filters) {
			f.filter(doc);
		}
		
		return doc;
	}
	
	@CheckReturnValue
	public static Pipe createPipe() {
		return new Pipe();
	}
}
