package com.homework.main;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Types {
	
	private static final String TARGET_FILE 	= "/home/myilmaz/Documents/Doktora/2017-8_I/NLP/HW-Deneme/destFile/allwords.csv";
	private static final String SEARCH_FILE 	= "/home/myilmaz/Documents/Doktora/2017-8_I/NLP/HW-Deneme/dataSet/allwords.csv";
	private static final String WIKI_DICT 		= "https://tr.wiktionary.org/wiki/";
	private static final String ERR_MSG_WORD 	= "Bu kelime yazdirilamadi";
	private static final String ERR_MSG_WORD_2 	= "Bu kelime bulunamadi";
	private static final String ERR_MSG_CONN 	= "baglanti saglanamadi";
	private static final String ERR_MSG_URL 	= "Siteye baglanamadi";
	private static final String CONN_CHECK 		= "OK";
	private static final String TAG 			= "h3";
	private static int count 					= 0;
	private static Document doc 				= null;
	private static PrintStream ps;
	private static OutputStream os;
	private static Elements elements;
	private static String url, check, type;
	
	public static void main(String[] args) throws IOException {
		
		os = new FileOutputStream(TARGET_FILE);
		
		try (BufferedReader br = new BufferedReader(new FileReader(SEARCH_FILE))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	System.out.print(line + " : ");
		    	
		    	url = WIKI_DICT + line;
		    	
		    	try {
					doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
				} catch (SocketTimeoutException e1) {
					System.out.println(ERR_MSG_URL);
					continue;
				}
		    	
		    	try {
					check=Jsoup .connect(url)
								.timeout(3000)
								.ignoreHttpErrors(true)
								.execute()
								.statusMessage();
					
			    	if (check.toString().equals(CONN_CHECK)) {
			    		
			    		elements = doc.select(TAG);
			    		count = elements.first().text().indexOf("[");
			    		
			    		try {
			    			type = elements.text().substring(0, count);
			    			
			    			if (!type.isEmpty()) {
			    				System.out.println(type);
			    			} else {
			    				System.out.println(ERR_MSG_WORD);
			    			}
			    			
						} catch (Exception e) {
							
							System.out.println(ERR_MSG_WORD);
							continue;
						}
			    		ps = new PrintStream(os);
			    		ps.println(line + "\t\t" + elements.text().substring(0, count));
			    		
			    	} else {
			    		System.out.println(ERR_MSG_WORD_2);
			    	}
					
				} catch (SocketTimeoutException e) {
					System.out.println(url + ERR_MSG_CONN);
					continue;
				}
		    	
		    	check = null;
		    	System.gc();
		    }
		}
		os.close();
	}
}
