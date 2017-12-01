package ire;



import java.util.HashMap;


public class RefineTokensAndCreateIndex {

	public static void  handle_info(String str) throws Exception
	{
		String regex="[0-9]+";
		String regex2="[a-zA-Z]+";
		if(!DriverClass.stopwords.containsKey(str)&&(!str.matches(regex))&&(str.matches(regex2)))
    	{ 
            STEMMER stemmer_object = new STEMMER();
            stemmer_object.add(str.toCharArray(),str.length());
            str=stemmer_object.stem();
            //System.out.println("info "+str);
            if(DriverClass.Index.get(str)==null){
                HashMap<String,IndexEntry> sub_map=new HashMap<>();
                DriverClass.Index.put(str, sub_map);
            }
            if(DriverClass.Index.get(str)==null || !(DriverClass.Index.get(str).containsKey(ParseXML.id_data))){
                DriverClass.Index.get(str).put(ParseXML.id_data, new IndexEntry());
            }
            DriverClass.Index.get(str).get(ParseXML.id_data).infobox++;
        }	
	}
	
	public static void handle_title_data(String str) throws Exception
	{
		String regex="[0-9]+";
		String regex2="[a-zA-Z]+";
		//if(str.matches(regex))
		
		if(!DriverClass.stopwords.containsKey(str)&&(!str.matches(regex))&&(str.matches(regex2)))
		{ 
			STEMMER stemmer_object= new STEMMER();
			stemmer_object.add(str.toCharArray(),str.length());
			str=stemmer_object.stem();
			//System.out.println("title "+str);
			if(DriverClass.Index.get(str)==null)
			{
				HashMap<String,IndexEntry> sub_map=new HashMap<>();
				DriverClass.Index.put(str, sub_map);
				
			}
			if(DriverClass.Index.get(str)==null||!(DriverClass.Index.get(str).containsKey(ParseXML.id_data))){
				 DriverClass.Index.get(str).put(ParseXML.id_data, new IndexEntry());
				 
		}
			 DriverClass.Index.get(str).get(ParseXML.id_data).title++;
			
		
		
		}
	}
		
		
		public static void  handle_text_data(String str) throws Exception
		{
			String regex="[0-9]+";
			String regex2="[a-zA-Z]+";
			
			if(!DriverClass.stopwords.containsKey(str)&&(!str.matches(regex))&&(str.matches(regex2)))
	    	{ 
	            STEMMER stemmer_object = new STEMMER();
	            stemmer_object.add(str.toCharArray(),str.length());
	            str=stemmer_object.stem();
	            //System.out.println("body "+str);
	            if(DriverClass.Index.get(str)==null){
	                HashMap<String,IndexEntry> sub_map=new HashMap<>();
	                DriverClass.Index.put(str, sub_map);
	            }
	            if(DriverClass.Index.get(str)==null || !(DriverClass.Index.get(str).containsKey(ParseXML.id_data))){
	                DriverClass.Index.get(str).put(ParseXML.id_data, new IndexEntry());
	            }
	            DriverClass.Index.get(str).get(ParseXML.id_data).body++;
	        }	
		}
		
		public static void  handle_cat_data(String str) throws Exception
		{
			String regex="[0-9]+";
			String regex2="[a-zA-Z]+";
			
			if(!DriverClass.stopwords.containsKey(str)&&(!str.matches(regex))&&(str.matches(regex2)))
	    	{ 
	            STEMMER stemmer_object = new STEMMER();
	            stemmer_object.add(str.toCharArray(),str.length());
	            str=stemmer_object.stem();
	           // System.out.println("cat "+str);
	            if(DriverClass.Index.get(str)==null){
	                HashMap<String,IndexEntry> sub_map=new HashMap<>();
	                DriverClass.Index.put(str, sub_map);
	            }
	            if(DriverClass.Index.get(str)==null || !(DriverClass.Index.get(str).containsKey(ParseXML.id_data))){
	                DriverClass.Index.get(str).put(ParseXML.id_data, new IndexEntry());
	            }
	            DriverClass.Index.get(str).get(ParseXML.id_data).category++;
	        }	
		}
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
}
