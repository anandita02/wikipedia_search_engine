package ire;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
public class SortDocIdListByRank {
	
	public static void sortbyrank() throws IOException {
	
	 BufferedReader br = new BufferedReader(new FileReader("/home/anandita/workspace/ire/Index/SORT-BY-DOCID-PrimaryIndex.txt"));
     File file = new File("/home/anandita/workspace/ire/final_index/SORT-BY-RANK-PrimaryIndex");
	if (!file.exists()) {
		file.createNewFile();
	}

   
     
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
     
     //Secondary index for sorted_database
      File file1 = new File("/home/anandita/workspace/ire/final_index/SORT-BY-RANK-TertiaryIndex.txt");
	if (!file1.exists()) {
		file1.createNewFile();
	}

	FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
	BufferedWriter ter = new BufferedWriter(fw1);
     
     //Dense Index
       File file2 = new File("/home/anandita/workspace/ire/final_index/SORT-BY-RANK-SecondaryIndex.txt");
	if (!file2.exists()) {
		file2.createNewFile();
	}
       FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
	BufferedWriter sec = new BufferedWriter(fw2);
    
     long char_final=0;
     long char_sec=0;
    
     
     int i,l;
     int bcount=2,tcount=1000,ecount=1,ccount=20,icount=25,count_lines=0; 
     String next_record = br.readLine();
    System.out.println("sorting posting list for term-sorted index");
    System.out.println("generating final indexes");
     while(next_record!=null)
     {
         List<ListRank> data = new ArrayList<>();
         
         int count=0;
         String word_name="";
        // System.out.println(next_record);
         if(next_record.indexOf("-")>0)
         word_name = next_record.substring(0,next_record.indexOf("-"));
         
       if(word_name.isEmpty())
       {
          
           next_record = br.readLine();
            continue;
       }
         next_record = next_record.substring(next_record.indexOf("-")+1);
         StringTokenizer st1 = new StringTokenizer(next_record,";");
         while(st1.hasMoreElements())
         {
             count++;
             
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
//             
//             String one = next_doc.substring(next_doc.indexOf("t")+1,next_doc.indexOf(b));
//             next_doc = next_doc.substring(next_doc.indexOf(",")+1);
//             
//             String two = next_doc.substring(0,next_doc.indexOf("b"));
//             next_doc = next_doc.substring(next_doc.indexOf(",")+1);
//             
//             String three = next_doc.substring(0,next_doc.indexOf("c"));
//             next_doc = next_doc.substring(next_doc.indexOf(",")+1);
//             
//             String four = next_doc.substring(0,next_doc.indexOf("i"));
//             next_doc = next_doc.substring(next_doc.indexOf(",")+1);
//                     
//             String five = next_doc;
//             // System.out.println(one+":"+two+":"+three+":"+four+":"+five);
             
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
             
//             if(!five.isEmpty())
//             {
//                 ext=Integer.parseInt(five);
//             }
             int total = (body*bcount) +(title	*tcount)+(ext*ecount)+(cat*ccount)+(info*icount); 
             s.rank=total;
             data.add(s);
         }
         Collections.sort(data,new RankComp());
         //Now write this into file
         
         //Writing into tertiary index
         if(count_lines%100==0)
         {
             //System.out.println("writing in secondary: "+word_name+": "+char_written);
             ter.write(word_name+":"+char_sec+"\n");
         }
         
         //Writing into secondary index
         sec.write(word_name+":"+char_final+"\n");
         char_sec+=word_name.length()+(""+char_final).length()+2;
         
         //Writing into final index
         bw.write(word_name+"-"+count+"?");
         String temp_count=""+count;
         char_final = char_final+temp_count.length()+2;
         char_final+=word_name.length();
         for( i=0;i<data.size();i++)
         {
             bw.write(data.get(i).list);
             char_final+=data.get(i).list.length()+1;
             bw.write(";");
         }
         bw.write("\n");
         char_final++;
         
         count_lines++;
         next_record=br.readLine();
        // System.out.println("Read: "+next_record);
     }
     System.out.println("Normal Exit");
     bw.close();
     ter.close();
sec.close();
	
	}	

}
