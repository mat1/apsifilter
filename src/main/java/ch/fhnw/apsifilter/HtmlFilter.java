package ch.fhnw.apsifilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.jsoup.nodes.Document;

import ch.fhnw.apsifilter.filter.AttributeWhitelistFilter;
import ch.fhnw.apsifilter.filter.ProtocolFilter;
import ch.fhnw.apsifilter.filter.TagWhitelistFilter;
import ch.fhnw.apsifilter.filter.css.CssInlineFilter;
import ch.fhnw.apsifilter.filter.css.CssLinkFilter;
import ch.fhnw.apsifilter.filter.css.CssStyleAttributeFilter;


public final class HtmlFilter {

	public static void main(String[] args) {
		for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
            System.out.println(e);
        }
		
		if(args.length != 1) {
			printUsage();
			return;
		}
		
		if(!checkAdmin()) {
			printAdminError();
			return;
		}
		
		final HtmlFilter htmlFilter = new HtmlFilter();
		final String cleanHtml = htmlFilter.filter(args[0]);
		
		System.out.println(cleanHtml);
	}
	
	private static boolean checkAdmin() {
		if(runningOnWindows()) {
			return tryNTLogin();
		} else {
			return tryUnixLogin();
		}
	}
	
	private static boolean tryUnixLogin() {
		try{
			LoginContext context = new LoginContext("Unix");
			context.login();

			Subject user = context.getSubject();
			for(Principal p : user.getPrincipals()) {
				if("0".equals(p.getName())) return true;
			}
			
			return false;
		} catch (LoginException ex ) {
			System.err.println("Error while authenticating: " + ex);
			return false;
		}
	}
	
	private static boolean tryNTLogin() {
		try{
			LoginContext context = new LoginContext("Windows");
			context.login();

			Subject user = context.getSubject();
			for(Principal p : user.getPrincipals()) {
				if("S-1-5-32-544".equals(p.getName())) return true;
			}
			
			return false;
		} catch (LoginException ex ) {
			System.err.println("Error while authenticating: " + ex);
			return false;
		}
	}
	
	private static boolean runningOnWindows() {
		final String os = System.getProperty("os.name");
		
		return os.toLowerCase().contains("windows");
	}
	
	private static void printUsage() {
		System.out.println("Usage: java HtmlFilter <inputfile> <outputfile>");
	}
	
	private static void printAdminError() {
		System.out.println("Authentication failed: This program has to run with administrator/root rights");
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
	
	@CheckReturnValue
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
		
		BufferedReader in = null;
		try{
			final StringBuilder builder = new StringBuilder();
			in = new BufferedReader(new FileReader(f));
			final char[] buf = new char[1024];
			
			int cnt;
			while((cnt = in.read(buf)) != -1)
				builder.append(buf, 0, cnt);
			
			in.close();
			return builder.toString();
		} catch(IOException ex) {
			throw new FileNotFoundException("Error reading file: " + ex.getMessage());
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					throw new FileNotFoundException("Error closing file: " + e.getMessage());
				}
			}
		}
	}
}
