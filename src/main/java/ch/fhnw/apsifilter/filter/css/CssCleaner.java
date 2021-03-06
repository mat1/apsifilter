package ch.fhnw.apsifilter.filter.css;

import java.util.regex.Pattern;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public final class CssCleaner {
	private static final String XBL_PATTERN = "-moz-binding([\\s\\t]*:[0-9a-zA-z\\s\\t\\(\\)\\#.\\\\/\\-\\+]*(;)?)?";
	private static final String HTC_PATTERN = "behavior([\\s\\t]*:[0-9a-zA-z\\s\\t\\(\\)\\#.:\\\\/\\-\\+]*(;)?)?";
	private static final String IMPORT_PATTERN = "@import([0-9a-zA-z\\s\\t\\(\\)\\#.:\\\\/\\-\\+]*(;)?)?";
	private static final String EXPRESSION_PATTERN = "expression([\\s\\t]*:[0-9a-zA-z\\s\\t\\(\\)\\#.:\\\\/\\-\\+]*(;)?)?";
	private static final String URL_PATTERN = "u[\\s\\n\\t]*r[\\s\\n\\t]*l[\\s\\n\\t]*(\\()?";
	
	private final Pattern xblPattern;
	private final Pattern htcPattern;
	private final Pattern importPattern;
	private final Pattern expressionPattern;
	private final Pattern urlPattern;
	private final boolean strict;
	
	private CssCleaner(boolean strict) {
		xblPattern = Pattern.compile(XBL_PATTERN);
		htcPattern = Pattern.compile(HTC_PATTERN);
		importPattern = Pattern.compile(IMPORT_PATTERN);
		expressionPattern = Pattern.compile(EXPRESSION_PATTERN);
		urlPattern = Pattern.compile(URL_PATTERN);
		this.strict = strict;
	}
	
	@CheckReturnValue
	private boolean mayBeMalicious(@Nonnull final String css) {
		return xblPattern.matcher(css).find() ||
			   htcPattern.matcher(css).find() ||
			   expressionPattern.matcher(css).find() ||
			   importPattern.matcher(css).find() ||
			   urlPattern.matcher(css).find();
	}
	
	@CheckReturnValue
	public String getCleanedCss(@Nonnull final String css) {
		if(mayBeMalicious(css)){
			if(strict) return "";
			else return css.replaceAll(XBL_PATTERN, "ignored: 0;")
						   .replaceAll(HTC_PATTERN, "ignored: 0;")
						   .replaceAll(IMPORT_PATTERN, "ignored: 0;")
						   .replaceAll(EXPRESSION_PATTERN, "ignored: 0;")
						   .replaceAll(URL_PATTERN, "ignored: 0;");
		}
		
		return css;
	}
	
	@CheckReturnValue
	public static CssCleaner createDefault() {
		return createStrict();
	}
	
	@CheckReturnValue
	public static CssCleaner createStrict() {
		return new CssCleaner(true);
	}
	
	@CheckReturnValue
	public static CssCleaner createLazy() {
		return new CssCleaner(false);
	}
}
