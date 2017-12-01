package ire;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
//import java.util.Enumeration;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.HashMap;
import java.lang.Object;


public class DriverClass {
	static Scanner read=null;
	static int count=0;//,count_id=0;
	public static Map<String,Integer> stopwords=new HashMap<String,Integer>();	
	public static Map<String,HashMap<String,IndexEntry> > Index=new TreeMap<String,HashMap<String,IndexEntry>>();
	public static Map<String,String> DOCID_TITLE=new TreeMap<String,String>(); 
		
	public static void main(String[] args) throws IOException{
		long start = System.currentTimeMillis();
		 
	try{
		
			read= new Scanner(new File("/home/anandita/Downloads/stopword.txt"));
			read.useDelimiter("\n");
			while(read.hasNext()){
            	String temp=read.next();
                stopwords.put(temp.trim(),1);
            }
			//String filename =""+args[0]+"";
			File inputFile = new File("/home/anandita/Downloads/dump2.xml-p9244803p9518046");
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			ParseXML userhandler = new ParseXML();
			saxParser.parse(inputFile, userhandler);
			MergeFiles.mergefiles();
			SortDocIdListByRank.sortbyrank(); 
			//long end = System.currentTimeMillis();
			
			
			long end = System.currentTimeMillis();
			System.out.println((end-start)/1000+"s");
			Index.clear();
			//DOCID_TITLE.clear();
	
			
		} 
	catch (Exception e) {
        
		e.printStackTrace();
        
     }
	
	}
}
