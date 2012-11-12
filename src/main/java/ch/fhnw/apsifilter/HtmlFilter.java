package ch.fhnw.apsifilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
		try {
			String htmlText = readHtmlFromFile(filename);
			Document d = pipe.filter(htmlText);
			
			return d.html();
			
		} catch (FileNotFoundException ex) {
			System.err.println("Error accessing file: " + ex.getMessage());
			return "";
		}
	}
	
	private String readHtmlFromFile(String filename) throws FileNotFoundException {
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
}
