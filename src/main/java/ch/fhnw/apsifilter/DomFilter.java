package ch.fhnw.apsifilter;

import org.jsoup.nodes.Document;

public interface DomFilter {
	public void filter(Document node);
}
