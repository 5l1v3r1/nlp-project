package com.homework.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SearchWebSite {

	public static void main(String[] args) {

		try {
			File file = new File("/home/myilmaz/Documents/Doktora/2017-8_I/NLP/HW-Deneme/dataSet/allwords.csv");
			File destfile = new File ("/home/myilmaz/Documents/Doktora/2017-8_I/NLP/HW-Deneme/destFile/allwords.csv");

			FileInputStream fis = new FileInputStream(file); 
			InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-9"); //türkçe karakterleri okuması için
			OutputStream outt = new FileOutputStream(destfile);
			String a, s, line, token;
			int say=0,c;   
			Scanner fip1 = new Scanner(isr); //file here f

			while ((c = isr.read()) != -1) {
				Document doc = null;

				token=fip1.nextLine().toString();
				String url = "https://tr.wiktionary.org/wiki/"+token;

				System.out.println("--------------");
				System.out.println(url.substring(0)); // aranacak tum link i yazar.
				System.out.print(token+"\t\t");
				String mesaj,ok;
				ok="OK";
				Connection connect = Jsoup.connect(url).ignoreHttpErrors(true);

				doc = Jsoup.connect(url).ignoreHttpErrors(true).get();

				mesaj=Jsoup.connect(url).ignoreHttpErrors(true).execute().statusMessage();
				if(mesaj.toString().equalsIgnoreCase(ok)){
					System.out.println("ok=ok");

					PrintStream p = new PrintStream(outt);
					p.print(token+"\t\t");
					say=say+1;    
					int sayi=0;
					Elements elements = doc.select("h3");
					sayi=elements.first().text().indexOf("[");
					System.out.println(elements.text().substring(0, sayi));
					PrintStream pp = new PrintStream(outt);
					pp.println(elements.text().substring(0, sayi));

					mesaj=null;

				}else {
					System.out.println("bilinmiyor");}//if sonu
			}
			isr.close(); 
			fis.close(); 
		} catch (IOException e) {
			e.printStackTrace();}
	}
}
