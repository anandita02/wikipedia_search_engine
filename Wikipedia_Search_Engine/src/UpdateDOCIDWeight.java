package ire;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class UpdateDOCIDWeight {

	
	public static void update_docid_weight(String next_record,int ltcount,int lbcount,int licount,int lccount) {
		 int lecount=0;
		 String word_name = next_record.substring(0,next_record.indexOf("-"));
         //  System.out.println("Word is: "+word_name);
           next_record = next_record.substring(next_record.indexOf("-")+1);
           int no_of_rec = Integer.parseInt(next_record.substring(0,next_record.indexOf("?")));
           next_record = next_record.substring(next_record.indexOf("?")+1);
           
               //Word Found 24162659
           
            //  System.out.println("Found: !! "+no_of_rec);
               int i,l,local_counter=0;
               StringTokenizer st1 = new StringTokenizer(next_record,";");
             //  System.out.println("count is: "+st1.countTokens());
               while(st1.hasMoreElements())
               {
                   
            	   local_counter++;
                   
                   ListRank s = new ListRank();
                   String next_doc = st1.nextToken();
                     s.list=next_doc;
                     String doc_id;
                     //System.out.println(next_doc);
                    if(next_doc.indexOf("|")>=0) 
                      doc_id  = next_doc.substring(0, next_doc.indexOf("|"));
                    else continue;
                   next_doc = next_doc.substring(next_doc.indexOf("|")+1);
                   //String onedoc=next_doc(0,next_doc.indexOf(";")+1);
                   l=next_doc.length();
                   String one="",two="",three="",four="";
                   for(i=0;i<l;)
                   {
                  	 if(next_doc.charAt(i)=='t')
                  	 { i++;
                  		 while(i<l&&Character.isDigit(next_doc.charAt(i)))
                  		 {one+=next_doc.charAt(i);
                  			 i++;
                  		 }
                  			 
                  	 }
                  	 
                  	 else if(next_doc.charAt(i)=='b')
                  	 { i++;
                  		 while(i<l&&Character.isDigit(next_doc.charAt(i)))
                  		 {two+=next_doc.charAt(i);
                  			 i++;
                  		 }
                  			 
                  	 }
                  	 else if(next_doc.charAt(i)=='c')
                  	 { i++;
                  		 while(i<l&&Character.isDigit(next_doc.charAt(i)))
                  		 {three+=next_doc.charAt(i);
                  			 i++;
                  		 }
                  			 
                  	 }
                  	 else if(next_doc.charAt(i)=='i')
                  	 { i++;
                  		 while(i<l&&Character.isDigit(next_doc.charAt(i)))
                  		 {four+=next_doc.charAt(i);
                  			 i++;
                  		 }
                  			 
                  	 }
                   }
                  	 
                  	 
                  	int body=0,title=0,info=0,cat=0,ext=0;
                    //title+","+cat+","+info+","+body+","+ref+";";
                    if(!one.isEmpty())
                    {
                        title=Integer.parseInt(one);
                    }
                    
                    if(!two.isEmpty())
                    {
                        body=Integer.parseInt(two);
                    }
                    
                    if(!three.isEmpty())
                    {
                        cat=Integer.parseInt(three);
                    }
                    
                    if(!four.isEmpty())
                    {
                        info=Integer.parseInt(four);
                    }
                    
//                    if(!five.isEmpty())
//                    {
//                        ext=Integer.parseInt(five);
//                    }
                    int total = (body*lbcount) +(title	*ltcount)+(ext*lecount)+(cat*lccount)+(info*licount);
                  	 
                  	 
                    if(RunQuery.DOCID_WEIGHT.containsKey(doc_id))
                    {
                        int cur = RunQuery.DOCID_WEIGHT.get(doc_id);
                        RunQuery.DOCID_WEIGHT.remove(doc_id);
                        total+=cur*15;
                       
                        
                    }
                    double log_data = (double)15000000/no_of_rec;
                    int log_val = (int)Math.log(log_data);
                    total = (int)total *log_val; 
                    RunQuery.DOCID_WEIGHT.put(doc_id,total);
                    
                  	 
                  	 
                  	 
                  	 
                  	 
                  	 
                  	 
                  	 
                  	 
                  	 
                  	 
                  	 
                   
            	   
            	   
            	   
            	   
                 
            	   
            	   
            	   
                   
                   
               }
               
        
               
           
          
    }
}
