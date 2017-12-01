package ire;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;




public class RunQuery {
	static long start;
	static RandomAccessFile read_primary_index,read_secondary_index,read_titleprimary_index,read_titlesecondary_index;
	
	//String term;
    public static int tertiary_limit=100,secondary_limit=1000;
    int lbcount=2,ltcount=10000,lecount=1,lccount=30,licount=25;
	public static HashMap<String,Integer> stopword=new HashMap<String,Integer>();
	//public static ArrayList<String> final_posting_lists=new ArrayList<>();
	public static ArrayList<TERM_OFFSET> primary_index=new ArrayList<>();
	public static ArrayList<TERM_OFFSET> secondary_index=new ArrayList<>();
	public static ArrayList<TERM_OFFSET> tertiary_index=new ArrayList<>();
	public static ArrayList<TERM_OFFSET> title_tertiary_index=new ArrayList<>();
	//public static Map<String,Integer> query_docids=new HashMap<String,Integer>();
	//static ArrayList<POSTING_OFFSET> tertiary_index=new ArrayList<>();
	public static HashMap<String,Integer> DOCID_WEIGHT=new HashMap<>();
	
	static Scanner read;
	
	
	
	public static void read_TertiaryIndex_to_memory() throws FileNotFoundException
	{
		
	
        
		
		FileReader read_tertiary_index = new FileReader("/home/anandita/workspace/ire/final_index/SORT-BY-RANK-TertiaryIndex.txt");//Index/SORT-BY-DOCID-TertiaryIndex.txt");
		
		BufferedReader buffered_reader = new BufferedReader(read_tertiary_index);
        String read_next_line;
        try {
			while((read_next_line=buffered_reader.readLine())!=null)
			{  	String term_offset[]=read_next_line.split(":");
				//System.out.println(read_next_line);
				
				tertiary_index.add(new TERM_OFFSET(term_offset[0],term_offset[1]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			buffered_reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Tertiary index created");
	    
	    
	    
		
	}
	
	
	
	public static String get_offset(String term, ArrayList<TERM_OFFSET> index)
	{
		
	Comparator<TERM_OFFSET> c = new Comparator<TERM_OFFSET>() {
		      public int compare(TERM_OFFSET u1, TERM_OFFSET u2) 
		      {
		        return u1.posting_term.compareTo(u2.posting_term);
		      }
		};
		int start= Collections.binarySearch(index, new TERM_OFFSET(term, "0"), c);
		//System.out.println("hi"+start);
		
		
		if(start<0)
        { 
			
            start*=-1;
    
            if(start>2&&(start-2)<index.size())
            { 
            	return index.get(start-2).posting_term+"-"+index.get(start-2).posting_offset;
            }
            else
                return "xx-1";
        }
       
        else
        	return  index.get(start).posting_term+"-"+index.get(start).posting_offset;

		
	}
	
	
	
	
	
	
	public static void get_docid(String term,int ltcount,int lbcount,int licount,int lccount) throws IOException
	{
		
		int i=0;
		
	
		long secondary_offset=0;
		String word_offset_secondary[]=get_offset(term,tertiary_index).split("-");
		
		System.out.println(word_offset_secondary[0]);
		if(word_offset_secondary[0].equals("xx"))
		secondary_offset=0;
		else
		secondary_offset=Long.parseLong(word_offset_secondary[1]);
		
		read_secondary_index.seek(secondary_offset);
		secondary_index.clear();	
		for( i=0;i<=RunQuery.tertiary_limit;i++)
		{
			String read_from_secondary_file=read_secondary_index.readLine();
			if(read_from_secondary_file==null)
			{
				break;
			}
			String temp[]=read_from_secondary_file.split(":");
			
			TERM_OFFSET temp_obj=new TERM_OFFSET(temp[0],temp[1]);
			secondary_index.add(temp_obj);
		}
		String word_offset_primary[]=get_offset(term,secondary_index).split("-");
		System.out.println(word_offset_primary[0]);
		long primary_offset=Long.parseLong(word_offset_primary[1]);
		read_primary_index.seek(primary_offset);
		primary_index.clear();
		for(i=0;i<=RunQuery.secondary_limit;i++)
		{
			String read_from_primary_file=read_primary_index.readLine();
			if(read_from_primary_file==null) break;
			String temp[]=read_from_primary_file.split("-");
		
			TERM_OFFSET temp_obj=new TERM_OFFSET(temp[0],temp[1]);
			primary_index.add(temp_obj);
			//read_from_primary_file=read_primary_index.readLine();
		}
		String result[]=get_offset(term,primary_index).split("-");
		System.out.println(result[0]);
		
		if(term.compareTo(result[0])==0)
		{
		
		String record;
		record="";
		
		record+=result[0]+"-"+result[1];
		System.out.println(record);
		System.out.println("record end");
		
		UpdateDOCIDWeight.update_docid_weight(record,ltcount,lbcount,licount,lccount);
		
		
		
		
		
		
		
		}
		else 
		{
			System.out.println("'"+term+"' not found");
		}
		
		
		
		
		
	}
	
	public static void read_title_mapping_to_memory() throws IOException
	{
		
		FileReader read_tertiary_index = new FileReader("/home/anandita/workspace/ire/title_database/DOCID-Title-TertiaryIndex.txt");//Index/SORT-BY-DOCID-TertiaryIndex.txt");
		
		BufferedReader buffered_reader = new BufferedReader(read_tertiary_index);
        String read_next_line;
        try {
			while((read_next_line=buffered_reader.readLine())!=null)
			{  	String term_offset[]=read_next_line.split(":");
				//System.out.println(read_next_line);
				
				title_tertiary_index.add(new TERM_OFFSET(term_offset[0],term_offset[1]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			buffered_reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Tertiary index for doc-id to title mapping created");
	       
		
		
	}
	
	
	
	
	public static void main(String[] args) throws Exception
	{    
		
		String term;
		STEMMER stemmer_object = new STEMMER();
		read= new Scanner(new File("/home/anandita/Downloads/stopword.txt"));
        read.useDelimiter("\n");
        read_primary_index = new RandomAccessFile("/home/anandita/workspace/ire/final_index/SORT-BY-RANK-PrimaryIndex.txt","r");//Index/SORT-BY-DOCID-PrimaryIndex.txt","r");
        read_secondary_index = new RandomAccessFile("/home/anandita/workspace/ire/final_index/SORT-BY-RANK-SecondaryIndex.txt","r");//SORT-BY-DOCID-SecondaryIndex.txt","r");
        read_titleprimary_index = new RandomAccessFile("/home/anandita/workspace/ire/title_database/DOCID-Title-PrimaryIndex.txt","r");
        read_titlesecondary_index = new RandomAccessFile("/home/anandita/workspace/ire/title_database/DOCID-Title-SecondaryIndex.txt","r");
		read_title_mapping_to_memory();
       read_TertiaryIndex_to_memory();
		
		String regex="[^a-zA-Z0-9:]";
		System.out.println("Enter search query, q to quit :");
		BufferedReader buffer_reader=new BufferedReader(new InputStreamReader(System.in));
		String search_query;
		while((search_query=buffer_reader.readLine())!="q")
		{
		start=0;
		start = System.currentTimeMillis();  // starting time calculation
		search_query=search_query.replaceAll(regex," ").toLowerCase();
		char is_field_query='f';
		//String term;
		String query_tokens[]=search_query.split(" ");
		for(String token: query_tokens)
		{
			 int lbcount=2,ltcount=10000,lecount=1,lccount=30,licount=25;
			read_primary_index.seek(0);
			read_secondary_index.seek(0);
			
			is_field_query='f';
		if(token.contains("t:") || token.contains("b:") || token.contains("i:") || token.contains("c:") || token.contains("r:") || token.contains("e:"))
				is_field_query='t';
		
		switch(is_field_query)
		{
		case 't':
			term=token.substring(2).trim();
			if(stopword.containsKey(term))
				break;
			stemmer_object.add(term.toCharArray(), term.length());
			term=stemmer_object.stem();
			if(token.contains("t:"))
				{
					//get_docid(term,"t");
					ltcount*=1000;
					
				}
			if(token.contains("b:") || token.contains("r:")||token.contains("e:"))
				{    lbcount*=1000;
				//get_docid(term,"b");
				}
			 if(token.contains("i:"))
				{
				licount*=1000;
				//get_docid(term,"i");
				
				}
			 if(token.contains("c:"))
				{
				lccount*=1000;
				//	get_docid(term,"c");
				}
			   get_docid(term,ltcount,lbcount,licount,lccount);
			break;
		case 'f':
			term=token.trim();
			if(stopword.containsKey(term))
				break;
			stemmer_object.add(term.toCharArray(), term.length());
			term=stemmer_object.stem();
			get_docid(term,ltcount,lbcount,licount,lccount);
			
			break;
		}
		
		
		
		}
		
		//for (Map.Entry<String,Integer> entry2 : DOCID_WEIGHT.entrySet()) {
	    	//		System.out.println("***"+entry2.getKey()+" "+entry2.getValue());
	    			//System.out.println(entry2.getVal());
	    	
	    	//}
		
		 Set<Entry<String, Integer>> set = DOCID_WEIGHT.entrySet();
	        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
	        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
	        {
	            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
	            {
	                return (o2.getValue()).compareTo( o1.getValue() );
	            }
	});
	int i;
//	        for(i=0;i<list.size();i++)
//	        	System.out.println(list.get(i).getKey()+" "+list.get(i).getValue());
//	        
//		//load tertiary to memory
	        //do lookup in three levels
	        //return the title
	       
	      
	  ///****************finally retrieve the relevant document title*************************       

			
	      
	    int j;
	        
	    for(Map.Entry<String, Integer> entry:list)
		{
			ArrayList<TERM_OFFSET> titleprimary_index=new ArrayList<>();
			ArrayList<TERM_OFFSET> titlesecondary_index=new ArrayList<>();
			read_titleprimary_index.seek(0);
			read_titlesecondary_index.seek(0);
			term=entry.getKey();
			//long docid=Long.parseLong(entry.getKey());
			System.out.println("***************"+term+"*************");
			// i=0;
			
			
			long secondary_offset=0;
			String word_offset_secondary[]=get_offset(term,title_tertiary_index).split("-");
			System.out.println(word_offset_secondary[0]);
			if(word_offset_secondary[0].equals("xx"))
			secondary_offset=0;
			else
			secondary_offset=Long.parseLong(word_offset_secondary[1]);
			
			read_titlesecondary_index.seek(secondary_offset);
			//titlesecondary_index.clear();	
			for( i=0;i<=200;i++)//how many to pick from sec index
			{
				String read_from_secondary_file=read_titlesecondary_index.readLine();
				if(read_from_secondary_file==null)
				{
					break;
				}
				String temp[]=read_from_secondary_file.split(":");
				if(temp.length<2) continue;
				TERM_OFFSET temp_obj=new TERM_OFFSET(temp[0],temp[1]);
				titlesecondary_index.add(temp_obj);
			}
			String word_offset_primary[]=get_offset(term,titlesecondary_index).split("-");
			System.out.println(word_offset_primary[1]);
			long primary_offset=Long.parseLong(word_offset_primary[1]);
			read_titleprimary_index.seek(primary_offset);
			titleprimary_index.clear();
			for(i=0;i<=200;i++)
			{
				String read_from_primary_file=read_titleprimary_index.readLine();
				if(read_from_primary_file==null) break;
				//System.out.println(read_from_primary_file);
				String temp[]=read_from_primary_file.split("-");
				if(temp.length<2) continue;
				TERM_OFFSET temp_obj=new TERM_OFFSET(temp[0],temp[1]);
				titleprimary_index.add(temp_obj);
				//read_from_primary_file=read_titleprimary_index.readLine();
			}
			String result[]=get_offset(term,titleprimary_index).split("-");
			System.out.println(result[1]);
			
			if(term.compareTo(result[0])==0)
			{
			
			String record;
			record="";
			record+=result[0]+"-"+result[1];
			System.out.println(record);
			//UpdateDOCIDWeight.update_docid_weight(record,ltcount,lbcount,licount,lccount);
			
			
			
			
			
			
			
			}
			else 
			{
				System.out.println("'"+term+"' not found");
			}
			
		
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
	
		
		
		
		
		DOCID_WEIGHT.clear();
		//primary_index.clear();
		//secondary_index.clear();
		}
		
		
		title_tertiary_index.clear();
		tertiary_index.clear();
	}
}
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	


