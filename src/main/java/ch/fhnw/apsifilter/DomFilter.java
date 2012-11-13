package ch.fhnw.apsifilter;

import javax.annotation.Nonnull;

import org.jsoup.nodes.Document;

public interface DomFilter {
	public void filter(@Nonnull Document node);
}
