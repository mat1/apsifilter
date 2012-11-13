package ch.fhnw.apsifilter.filter.css;

import java.util.regex.Pattern;

public final class CssCleaner {
	private static final String XBL_PATTERN = "-moz-binding:[\\s\\t]*url\\([\\s\\t0-9a-zA-Z]*.xml(#[a-zA-Z]*)?";
	private static final String HTC_PATTERN = "behavior:[\\s\\t]*url[\\s\\t]*\\([\\s\\t0-9a-zA-Z]*.htc";
	
	private final Pattern xblPattern;
	private final Pattern htcPattern;
	
	private CssCleaner() {
		xblPattern = Pattern.compile(XBL_PATTERN);
		htcPattern = Pattern.compile(HTC_PATTERN);
	}
	
	public boolean mayBeMalicious(final String css) {
		return xblPattern.matcher(css).matches() ||
			   htcPattern.matcher(css).matches();
	}
	
	public static CssCleaner createDefault() {
		return new CssCleaner();
	}
}
