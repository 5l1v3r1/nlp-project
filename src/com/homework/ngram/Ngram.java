package com.homework.ngram;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Ngram {

	private static final String KAYNAK  = "/home/myilmaz/Documents/Doktora/2017-8_I/NLP/mycHW-corpus/kaynak/";
	private static final String HEDEF   = "/home/myilmaz/Documents/Doktora/2017-8_I/NLP/mycHW-corpus/hedef/";
	private static final String DURUM   = " defa bulunmaktadır";
	private static final String KONTROL = "Dosya mevcut";
	private static final String UNIGRAM = "uniGram";
	private static final String BIGRAM  = "biGram";
	private static final String TRIGRAM = "triGram";
	private static final String ESIT	= ": ";
	private static final String BOS		= " ";

	public static void main(String[] args) throws IOException {

		Ngram gram = new Ngram();

		gram.uniGramCalc();
		gram.biGramCalc();
		gram.triGramCalc();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void operate(File srcFolder, File destFolder, String nGram) {

		if(srcFolder.isDirectory()){
			String files[] = srcFolder.list();

			for(String file : files){
				
				ArrayList wordsCount = new ArrayList();
				ArrayList arrList = new ArrayList();
				ArrayList words = new ArrayList();		
				BufferedReader reader = null;
				StringTokenizer st;
				OutputStream os;
				PrintStream p;
				File destFile;
				File srcFile;
				String line;
				int s=0;

				srcFile = new File(srcFolder, file);
				destFile = new File(destFolder, file);

				if (destFile.length() == 0){
					try {
						reader = new BufferedReader(new FileReader(srcFile));
						line = reader.readLine();
						os = new FileOutputStream(destFile);

						while (line != null) {

							st = new StringTokenizer(line);

							while(st.hasMoreTokens())
							{
								words.add(st.nextToken());
							}

							line = reader.readLine();
						}
					} catch (IOException e1) {
						continue;
					}

					if (nGram == UNIGRAM){
						arrList = words;

					} else if (nGram == BIGRAM){
						arrList = new ArrayList();

						for (int i = 0; i < words.size()-1; i++){
							System.out.println((words.get(i) + BOS + words.get(i+1)).toString());
							arrList.add(i, (words.get(i) + BOS + words.get(i+1).toString()));
						}

					} else if (nGram == TRIGRAM){
						arrList = new ArrayList();
						for (int i = 0; i < words.size()-1; i++){

							try {
								System.out.println((words.get(i) + BOS + words.get(i+1) + BOS + words.get(i+2)).toString());
								arrList.add(i, ((words.get(i) + BOS + words.get(i+1) + BOS + words.get(i+2)).toString()));

							} catch (IndexOutOfBoundsException e) {
								continue;
							}
						}
					}

					for (int i = 0; i < arrList.size(); i++){
						for (int j = 0; j < arrList.size(); j++){
							if(arrList.get(i).equals(arrList.get(j))){
								s++;
							}
						}
						wordsCount.add(i, s); 
						s=0;
					}

					p = new PrintStream(os);  

					for (int i = 0; i <arrList.size(); i++){
						System.out.println(arrList.get(i) + ESIT + wordsCount.get(i) + DURUM);
						p.println(arrList.get(i) + ESIT + wordsCount.get(i) + DURUM);
					}
					try {
						reader.reset();
						reader.close();
						os.flush();
						os.close();
					} catch (IOException e) {
						continue;
					}
					p.flush();
					p.close();
					words = null;
					wordsCount = null;
					arrList = null;
					st = null;
					srcFile = null;
					destFile = null;

					System.gc();

				} else {
					System.out.println(KONTROL);
				}
				break;
			}
		}

	}

	public void uniGramCalc() throws IOException{

		File srcFolder = new File(KAYNAK);
		File destFolder = new File(HEDEF + UNIGRAM);

		operate(srcFolder, destFolder, UNIGRAM);

	}

	public void biGramCalc() throws IOException{

		File srcFolder = new File(KAYNAK);
		File destFolder = new File(HEDEF + BIGRAM);

		operate(srcFolder, destFolder, BIGRAM);
	}

	public void triGramCalc() throws IOException{

		File srcFolder = new File(KAYNAK);
		File destFolder = new File(HEDEF + TRIGRAM);

		operate(srcFolder, destFolder, TRIGRAM);

	}

}