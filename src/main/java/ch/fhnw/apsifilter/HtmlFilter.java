package ch.fhnw.apsifilter;

import org.jsoup.nodes.Document;

public class HtmlFilter {

	public static void main(String[] args) {
		if(args.length != 1) {
			printUsage();
			return;
		}
		
		System.out.println(new HtmlFilter().filter(args[0]));
	}
	
	private static void printUsage() {
		System.out.println("Usage: java HtmlFilter <filename>");
	}
	
	private final Pipe pipe;
	
	private HtmlFilter() {
		pipe = Pipe.createPipe();
	}
	
	private String filter(String filename) {
		String htmlText = readHtmlFromFile(filename);
		
		Document d = pipe.filter(htmlText);
		
		return d.html();
	}
	
	private String readHtmlFromFile(String filename) {
		return "";
	}
}
