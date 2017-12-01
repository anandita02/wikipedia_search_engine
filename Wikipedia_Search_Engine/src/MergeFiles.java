package ire;
import java.io.BufferedReader;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;

import java.util.Comparator;
 class term
 {
     String term;
     String list;
     int doc_id;
 }
class Comp implements Comparator<term> 
{ 
    public int compare(term x, term y) 
    { 
        return x.term.compareTo(y.term);
    } 
}

public class MergeFiles {
		
	
	 public static void mergefiles() throws IndexOutOfBoundsException
	 {
		 try
	        {
	            System.out.println("Merging Files");
	          int isnull=0;
	          long charcount=0;
	        //no of files that are divided depends on count value
	      // int val=file1.count/5000;
	        int val=14;
	        Comparator<term> com = new Comp();
	        PriorityQueue<term> my_data =  new PriorityQueue<term>(val+2,com);
	         BufferedReader br2[] = new BufferedReader[val+2];
	        for(int j=1;j<=val;j++)
	            br2[j] = new BufferedReader(new FileReader("/home/anandita/workspace/ire/title_database/"+j));
	       // File file = new File("processing"+count);
	        
	        //Initialize variables, boolean array arr to mark completeness of a text file
	        boolean[] arr = new boolean[val+2];
	         int rec_read=0,rec_write=0,sec_entry=0;
	        File out_file = new File("/home/anandita/workspace/ire/title_database/SORT-BY-DOCID-PrimaryIndex.txt");
	        if (!out_file.exists()) 
	        {
	           out_file.createNewFile();
	        }
	        
	        FileWriter fw = new FileWriter(out_file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	            
	        //Secondary Index file
	        File out_file1 = new File("/home/anandita/workspace/ire/title_database/SORT-BY-DOCID-SecondaryIndex.txt");
	        if (!out_file1.exists()) 
	        {
	           out_file1.createNewFile();
	        }
	        
	        FileWriter fw1 = new FileWriter(out_file1.getAbsoluteFile());
	        BufferedWriter bw1 = new BufferedWriter(fw1);
	        File out_file2 = new File("/home/anandita/workspace/ire/title_database/SORT-BY-DOCID-TertiaryIndex.txt");
	        if (!out_file2.exists()) 
	        {
	           out_file2.createNewFile();
	        }
	        
	        FileWriter fw2 = new FileWriter(out_file2.getAbsoluteFile());
	        BufferedWriter bw2 = new BufferedWriter(fw2);
	        
	        
	        
	        //arr false:stores track of files which have been read completely
	        for(int k=1;k<=val+1;k++)
	            arr[k]=true;
	        int last;
	        String read;
	        System.out.println("Starting to read");
	        //read first element from k(val) no. of files store in heap
	        for(int j=1;j<=val;j++)
	        {
	         
	            if(arr[j])
	            {                  
	               
	               read = br2[j].readLine();
	               if(read==null)
	               {
	                    isnull++;
	                    System.out.println("Completed File"+j+" at: "+rec_read);
	                    arr[j]=false;    
	                    br2[j].close();

	               }
	               else
	               {
	                    String word = read.substring(0,read.indexOf(':'));
	                    read=read.substring(read.indexOf(':')+1);
	                    
	                    term t = new term();
	                    t.list=read;
	                    
	                    t.term=word;
	                    t.doc_id=j;
	                    my_data.add(t);
	                    rec_read++;
	               }
	         
	            }
	      
	        }
	        
	        
	        //Seek and Write top element
	        term top = my_data.poll();
	       
	        last=top.doc_id;
	        int sec_char_count_cuml=0;
	         String out_list = top.list,out_word = top.term;
	           String lout_list=null,lout_word=null;
	        while(isnull<val)
	        {
	          
	           
	            if(!arr[last])
	            {
	                term ltop = my_data.poll();
	                
	                last=ltop.doc_id;
	                lout_list=ltop.list;
	                lout_word=ltop.term;
	               
	                
	            }
	            else
	            {
	                
	               // System.out.println("in else"+last);
	                read = br2[last].readLine();
	                if(read==null)
	                {
	                    if(arr[last])
	                            isnull++;
	                    System.out.println("158Completed File"+last+"at "+rec_read);
	                    arr[last]=false;    
	                    br2[last].close();
	                   
	                }
	                else
	                {
	                    String word = read.substring(0,read.indexOf(':'));
	                    read=read.substring(read.indexOf(':')+1);
	                    
	                  //  System.out.print("\tRead word: "+word);
	                    term lt = new term();
	                    lt.list=read;
	                    lt.term=word;
	                    lt.doc_id=last;
	                    my_data.add(lt);
	                    rec_read++;
	                    
	                   //System.out.println("Finised entering");
	                    term ltop1 = my_data.poll();
	                    
	                    
	                    lout_list=ltop1.list;
	                    l=ltop1.term;
	                    last=ltop1.doc_id;
	                }
	              }
	             if(lout_word.equals(out_word)&&out_word!=null)
	             {
	                // System.out.print("\tEqual case");
	                 out_list=out_list+lout_list;
	             }
	             else
	             {
	                
	            	 
	            	 
	            	 
	                  
	            	if(rec_write%1000==0)
	                     {	  
	            		 
	            		 	
	                         //Write into secondary index
	            				
	                        
	                        
	                         if((sec_entry)%10==0)
	                         {  
	                        	 
	                        	 //write tertiary index
	                         	bw2.write(out_word+":");
	                        	
		                        bw2.write(""+(sec_char_count_cuml));
	                        		 
		                        bw2.write("\n");	
		                         
	                         }
	                        	 
	                         
	                         bw1.write(out_word+":");
	                         
	                         bw1.write(""+charcount);
	                     
	                         bw1.write("\n");
	                         sec_entry++;
	                         
	                        
	                         sec_char_count_cuml+=out_word.length()+(""+charcount).length()+2;
	                        
	                        // System.out.println(sec_char_count_cuml);
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                         
	                     } 
	                     
	                          
	                        bw.write(out_word+"-");
	                        bw.write(out_list);
	                        bw.write("\n");
	                       charcount+=out_word.length()+out_list.length()+2;
	                        rec_write++;
	                  
	                     out_word=lout_word;
	                     out_list=lout_list;
	                     lout_word="";
	                     lout_list="";
	                     

	             }
	                
	        }
	        System.out.println("Size of all_data="+my_data.size());
	       
	         bw.close();
	         bw1.close();
	         bw2.close();
	        System.out.println("No of records merged: "+rec_read+" Records Written into merged file: "+rec_write);

	    
	    }catch(Exception e)
	    {
	        System.out.println("Exception in merge_funciotn"+e.getLocalizedMessage()+":"+e.getMessage());
	    }
	    }
	    
	    
	    
	    

	 }
	
	
	
	
	
	


