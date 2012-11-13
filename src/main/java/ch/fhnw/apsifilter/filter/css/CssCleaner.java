package ch.fhnw.apsifilter.filter.css;

import java.util.regex.Pattern;

public final class CssCleaner {
	private static final String XBL_PATTERN = "-moz-binding([\\s\\t]*:[0-9a-zA-z\\s\\t\\(\\)\\#.\\\\/\\-\\+]*(;)?)?";
	private static final String HTC_PATTERN = "behavior([\\s\\t]*:[0-9a-zA-z\\s\\t\\(\\)\\#.:\\\\/\\-\\+]*(;)?)?";
	private static final String IMPORT_PATTERN = "@import([0-9a-zA-z\\s\\t\\(\\)\\#.:\\\\/\\-\\+]*(;)?)?";
	
	private final Pattern xblPattern;
	private final Pattern htcPattern;
	private final Pattern importPattern;
	private final boolean strict;
	
	private CssCleaner(boolean strict) {
		xblPattern = Pattern.compile(XBL_PATTERN);
		htcPattern = Pattern.compile(HTC_PATTERN);
		importPattern = Pattern.compile(IMPORT_PATTERN);
		this.strict = strict;
	}
	
	private boolean mayBeMalicious(final String css) {
		return xblPattern.matcher(css).find() ||
			   htcPattern.matcher(css).find() ||
			   importPattern.matcher(css).find();
	}
	
	public String getCleanedCss(final String css) {
		if(mayBeMalicious(css)){
			if(strict) return "";
			else return css.replaceAll(XBL_PATTERN, "ignored: 0;")
						   .replaceAll(HTC_PATTERN, "ignored: 0;")
						   .replaceAll(IMPORT_PATTERN, "ignored: 0;");
		}
		
		return css;
	}
	
	public static CssCleaner createDefault() {
		return createStrict();
	}
	
	public static CssCleaner createStrict() {
		return new CssCleaner(true);
	}
	
	public static CssCleaner createLazy() {
		return new CssCleaner(false);
	}
}
