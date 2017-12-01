package ire;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class WriteIndexToFile {
	static void write_index_to_file() throws Exception{
	int val=DriverClass.count/5000;
	File file=new File("/home/anandita/workspace/ire/title_database/"+val);
	if(!file.exists())
			file.createNewFile();
	
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
   // String append="";
  for (Map.Entry<String,String> entry2 : DriverClass.DOCID_TITLE.entrySet()) {
		//	System.out.println("***"+entry2.getKey()+" "+entry2.getValue());
			//System.out.println(entry2.getVal());
	     bw.write(entry2.getKey()+":"+entry2.getValue()+"\n");
	
	}
//	for (Map.Entry<String, HashMap<String,IndexEntry>> entry : DriverClass.Index.entrySet()) {
//	    String key = entry.getKey().toString();
//	    
//	    bw.write(key+"-");
//	    HashMap<String,IndexEntry> value = entry.getValue();
//	    for (Map.Entry<String,IndexEntry> entry2 : value.entrySet()) {
//	    	String append=entry2.getKey()+"|";
//	   
//	    	IndexEntry e1 =entry2.getValue();
//	    	if(e1.title!=0)
//                append+="t"+e1.title;
//            if(e1.body!=0)
//                append+="b"+e1.body;
//            if(e1.category!=0)
//                append+="c"+e1.category;
//            if(e1.infobox!=0)
//               append+="i"+e1.infobox;
//           append+=";";
//            bw.write(append);	
//	}
//	    bw.write('\n');
//	    
//	}
		bw.close();
		DriverClass.DOCID_TITLE.clear();
	 //  DriverClass.Index.clear();
	}
	

}
