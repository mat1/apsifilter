package ch.fhnw.apsifilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.jsoup.nodes.Document;

import ch.fhnw.apsifilter.filter.AttributeWhitelistFilter;
import ch.fhnw.apsifilter.filter.ProtocolFilter;
import ch.fhnw.apsifilter.filter.TagWhitelistFilter;
import ch.fhnw.apsifilter.filter.css.CssInlineFilter;
import ch.fhnw.apsifilter.filter.css.CssLinkFilter;
import ch.fhnw.apsifilter.filter.css.CssStyleAttributeFilter;

public class HtmlFilter {

	public static void main(String[] args) {
		if(args.length != 2) {
			printUsage();
			return;
		}
		
		final HtmlFilter htmlFilter = new HtmlFilter();
		final String cleanHtml = htmlFilter.filter(args[0]);
		
		System.out.println(cleanHtml);
		htmlFilter.writeHtmlToFile(cleanHtml, args[1]);
	}
	
	private static void printUsage() {
		System.out.println("Usage: java HtmlFilter <inputfile> <outputfile>");
	}
	
	private final Pipe pipe;
	
	private HtmlFilter() {
		pipe = Pipe.createPipe();
		pipe.addFilter(TagWhitelistFilter.createDefault());
		pipe.addFilter(AttributeWhitelistFilter.createDefault());
		pipe.addFilter(ProtocolFilter.createDefault());
		pipe.addFilter(CssStyleAttributeFilter.createDefault());
		pipe.addFilter(CssInlineFilter.createLazy());
		pipe.addFilter(CssLinkFilter.createDefault());
	}
	
	private String filter(@Nonnull String filename) {
		try {
			String htmlText = readHtmlFromFile(filename);
			Document d = pipe.filter(htmlText);
			
			return d.html();
			
		} catch (FileNotFoundException ex) {
			System.err.println("Error accessing file: " + ex.getMessage());
			return "";
		}
	}
	
	@CheckReturnValue
	private String readHtmlFromFile(@Nonnull String filename) throws FileNotFoundException {
		final File f = new File(filename);
		if(!f.exists()) throw new FileNotFoundException("Unable to find file.");
		if(!f.canRead()) throw new FileNotFoundException("Could not read file.");
		
		try{
			final StringBuilder builder = new StringBuilder();
			final BufferedReader in = new BufferedReader(new FileReader(f));
			final char[] buf = new char[1024];
			
			int cnt;
			while((cnt = in.read(buf)) != -1)
				builder.append(buf, 0, cnt);
			
			in.close();
			return builder.toString();
		} catch(IOException ex) {
			throw new FileNotFoundException("Error reading file: " + ex.getMessage());
		}
	}
	
	private void writeHtmlToFile(@Nonnull String html, @Nonnull String filename) {
		final File file = new File(filename);

		try{
			final PrintWriter writer = new PrintWriter(file);
			writer.print(html);
			writer.close();
		} catch(IOException ex) {
			System.err.println("Error writing file: " + ex.getMessage());
		} 

	}
}
