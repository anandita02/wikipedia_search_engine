package ire;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
public class ParseXML extends DefaultHandler{
	
	 boolean page=false;
	 boolean title=false;
	 boolean id=false;
	 boolean text=false;
	int counter=0;
	
	boolean rev=false;
	static String title_data,text_data,id_data,org_title_data;
    static boolean flag_info=false,flag_cat=false;

	
	@Override
    public void startElement(String uri,String localName,String qName,Attributes attri) throws SAXException{
        if(qName.equalsIgnoreCase("page")){
            page=true;
            counter++;
            if((DriverClass.count%5000==0)&&(DriverClass.count!=0))
				try {
					WriteIndexToFile.write_index_to_file();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            DriverClass.count++;
            
        }
       
        if(qName.equalsIgnoreCase("title")){
            title=true;
        }
        if(qName.equalsIgnoreCase("revision")){
            rev=true;
        }
        if(qName.equalsIgnoreCase("id")&&!rev){// && !revision){
            id=true;
        }
        if(qName.equalsIgnoreCase("text")){
            text=true;
        }
    }
	
	@Override
	public void endElement(String uri,String localName,String qName) throws SAXException{	
	

	if(qName.equalsIgnoreCase("page")){
        page=false;
//        if(counter==page_limit)
//        {
//        	try {
//				INDEXER_CREATE_INDEX.write_to_file();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//        	counter=0;
//        }
    }
    if(qName.equalsIgnoreCase("title")){
        title=false;
    }
    if(qName.equalsIgnoreCase("revision")){
        rev=false;
    }
    if(qName.equalsIgnoreCase("id")){
        id=false;
    }
    if(qName.equalsIgnoreCase("text")){
        text=false;
    }
//    if(qName.equalsIgnoreCase("rev")){
//        rev=false;
//    }
////    id_data.setLength(0);
//    title_data.setLength(0);
//    text_data.setLength(0);
//    //INDEXER_PARSER.cat.setLength(0);
//    
    
    
    
	
}
	
	
	
//@Override
	 public void characters(char ch[], int start, int length) throws SAXException
	    {//StringBuilder title_data = new StringBuilder();
	      
		 if(title)
		 {
			title_data=new String(ch,start,length);
			//org_title_data=title_data;
			
//			try{
//			//System.out.println("***"+title_data);
				//String lower=title_data.toString().toLowerCase();
//                lower=lower.trim();
//              
//			RefineTokensAndCreateIndex.handle_title_data(lower);
//			}
//			catch(Exception e) 
//			{
//				e.printStackTrace();
//			}
			
		 }
//		 if(text||title)
//		 {
//			 StringBuilder data= new StringBuilder(new String(ch,start,length));
//			 //System.out.println("***"+data);
//			 StringBuilder info=new StringBuilder();
//			 StringBuilder ttext=new StringBuilder();
//			 int len=data.length();
//			 int i;
//			 
//			 //System.out.println(data);
//			 //System.out.println(length);
//	            for( i=0;i<len;i++)
//	            {
//	                char cur=data.charAt(i);
//	                if(cur=='{'||flag_info)
//	                {
//	                    if((i+9)<len &&(flag_info|| "{infobox".contains(data.substring(i+1, i+9).toLowerCase())))
//	                    { 
//	                    	if(!flag_info) flag_info=true;
//	                    	i=i+9;
//	                    	int count=2;
//	                    	char current=data.charAt(i);
//	                    	//System.out.print(i);
//	                    	while((i<len)&&(count>=0))
//	                    	{
//	                    		current=data.charAt(i);
//	                    	//	System.out.print("**"+current);
//	                    		if(current=='<')
//	                    		{
//	                    			while((i<len)&&(current!='>'))
//	                    				{current=data.charAt(i);i++;}
//	                    			i++;
//	                    			continue;
//	                    		}
//	                    		if((i+1)<len&&current=='}'&&(data.charAt(i+1)=='}'))
//	                    		{
//	                    			count-=1;
//	                    			i+=1;
//	                    			if(count==0) flag_info=false;
//	                    		}
//	                    		if((i+1)<len&&current=='{'&&(data.charAt(i+1)=='{'))
//	                    		{
//	                    			count+=1;
//	                    			i+=1;
//	                    		}
//	                    		//if(current=='{'&&(data.charAt(i+1)=='}'))
//	                    		
//	                    		if(current=='|')
//	                    		{ //System.out.println("1");
//	                    			while((i<len)&&(current!='='))
//                    				{current=data.charAt(i);i++;}
//	                    			
//                    			i++;
//                    			//System.out.print("**"+current);
//                    			continue;
//	                    		}
//	                    		
//	                    		
//	                    		if((current>=65&&current<=90)||(current>=97&&current<=122)||(current>=48&&current<=57))
//	                            { 
//	                                info.append(current); 
//	                                if(i==len-1)
//	                                {
//	                                	String lower=info.toString().toLowerCase();
//	                                    lower=lower.trim();
//	                                  
//	                                    if(!(lower.length()==0||lower.isEmpty()))
//	                                    {  try{
//	                                        RefineTokensAndCreateIndex.handle_info(lower);
//	                                    	}
//	                                    	catch(Exception e)
//	                                    	{ e.printStackTrace();	}
//	                                    }
//	                                    info.setLength(0);
//	                                }
//	                            }
//	                            else
//	                            { 
////	                            	while((i<len)&&(current==' '))
////                    				{i++;current=data.charAt(i);}
//	                    			
//	                            	
//	                            	
//	                                String lower=info.toString().toLowerCase();
//	                                lower=lower.trim();
//	                                //System.out.println(lower);
//	                                if(!(lower.length()==0||lower.isEmpty())){
//	                                	try{
//	                                        RefineTokensAndCreateIndex.handle_info(lower);
//	                                    	}
//	                                    	catch(Exception e)
//	                                    	{ e.printStackTrace();	}
//	                                }
//	                                info.setLength(0);
//	                            }
//	                    		
//	                    		
//	                    		i++;
//	                    		
//	                    				
//	                    		
//	                    	}
//	                    	
//	                    }
//	                    else if((i+1<len&&(i+6<len))&&"{cite".contains(data.substring(i+1, i+6).toLowerCase())){
//	                    	boolean cite_box_parent=true;
//	                        i+=6;
//	                        int count=2;
//	                        //System.out.println(data);
//	                        while(cite_box_parent&&i<len){
//	                            char current=data.charAt(i);
//	                          //  System.out.print(current);
//	                            if(current=='}'){
//	                                count--;
//	                            }
//	                            else if(current=='{'){
//	                                count++;
//	                            }
//	                            if(count==0){
//	                                cite_box_parent=false;
//	                            }
//	                            i++;
//	                            
//	                        }
//	                    }
//					 }
//	               
// 				 else if(cur=='['||flag_cat)
//	                {
// 					 
// 					 
// 					  if((i+1<len&&(i+7<len))&&"[file:".contains(data.substring(i+1, i+7).toLowerCase())){
//                      	boolean cite_box_parent=true;
//                          i+=7;
//                          int count=2;
//                          //System.out.println(data);
//                          while(cite_box_parent&&i<len){
//                              char current=data.charAt(i);
//                           //  System.out.print(current);
//                              if(current==']'){
//                                  count--;
//                              }
//                              else if(current=='['){
//                                  count++;
//                              }
//                              if(count==0){
//                                  cite_box_parent=false;
//                                  continue;
//                              }
//                              i++;
//                              
//                          }
//                      }
// 	                else if((i+1<len&&(i+8<len))&&"[image:".contains(data.substring(i+1, i+8).toLowerCase())){
//                      	boolean cite_box_parent=true;
//                          i+=8;
//                          int count=2;
//                          //System.out.println(data);
//                          while(cite_box_parent&&i<len){
//                              char current=data.charAt(i);
//                          //    System.out.print(current);
//                              if(current==']'){
//                                  count--;
//                              }
//                              else if(current=='['){
//                                  count++;
//                              }
//                              if(count==0){
//                                  cite_box_parent=false;
//                                  continue;
//                              }
//                              i++;
//                              
//                          }
//                      }
// 					 
// 					 
// 					 
// 					 
//	                	 
//	                	ttext.setLength(0);
//	                	
//	                	
//	                	
//	                	if((i+11)<len &&(flag_cat|| "[category:".contains(data.substring(i+1, i+11).toLowerCase())))
//	                    { 
//	                		 //System.out.println("***"+data);
//	                    	if(!flag_cat) flag_cat=true;
//	                    	i=i+11;
//	                    	int count=2;
//	                    	char current=data.charAt(i);
//	                    	//System.out.print(i);
//	                    	while((i<len)&&(count>=0))
//	                    	{
//	                    		current=data.charAt(i);
//	                    		//System.out.print(i);
//	                    		if((i+1)<len&&current=='{'&&(data.charAt(i+1)=='{'))
//	                    			{i++; continue;}
//	                    		if((i+1)<len&&current=='}'&&(data.charAt(i+1)=='}'))
//	                    			{i++;continue;}
//	                    		if((i+1)<len&&current==']'&&(data.charAt(i+1)==']'))
//	                    		{
//	                    			count-=1;
//	                    			i+=1;
//	                    			if(count==0) flag_cat=false;
//	                    		}
//	                    		if((i+1)<len&&current=='['&&(data.charAt(i+1)=='['))
//	                    		{
//	                    			count+=1;
//	                    			i+=1;
//	                    		}
//	                    		
//	                    		if((current>=65&&current<=90)||(current>=97&&current<=122)||(current>=48&&current<=57))
//		                        {
//		                            ttext.append(""+current);
//		                            //System.out.println("---"+text);
//		                            if(i==(len-1))
//		                            { //System.out.println("//"+text);
//		                            	String lower=ttext.toString().toLowerCase();
//		                                lower=lower.trim();
//		                              // if(!isPunctuation(lower))
//		                                if(!(lower.length()==0||lower.isEmpty()))
//		                                { try{
//		                                    RefineTokensAndCreateIndex.handle_cat_data(lower);
//		                                	}
//		                                catch(Exception e){
//		                                e.printStackTrace();}
//		                                }
//		                                ttext.setLength(0);
//		                            }
//		                        }
//		                        else
//		                        { 
//		                            String lower=ttext.toString().toLowerCase();
//		                            lower=lower.trim();
//		                            
//		                            
//		                            if(!(lower.length()==0||lower.isEmpty()))
//		                            {  try{
//		                            	RefineTokensAndCreateIndex.handle_cat_data(lower);}
//		                               catch(Exception e)
//		                            {e.printStackTrace();}
//		                            }
//		                            ttext.setLength(0);
//		                        }
//	                    		
//	                    		
//	                    	  	i++;
//	                    	  	
//	                    		
//	                    		
//	                    	}
//	                    	
//	                    }
//	                	
//	                	
//	                }
//	                
//	                else
//	                {
//	                	
//	                	
//	                	char current = data.charAt(i);
//	                	//System.out.print("----"+current);
//	                    if(current=='#')
//	                    {
//	                        while(i<len && current!=' ')
//	                        {
//	                            current=data.charAt(i);
//	                            i++;
//	                        }
//	                    }
//	                    else
//	                    {
//	                    	
//	                        if((current>=65&&current<=90)||(current>=97&&current<=122))
//	                        {
//	                            ttext.append(""+current);
//	                            //System.out.println(text);
//	                            if(i==(len-1))
//	                            { //System.out.println("//"+text);
//	                            	String lower=ttext.toString().toLowerCase();
//	                                lower=lower.trim();
//	                              // if(!isPunctuation(lower))
//	                                if(!(lower.length()==0||lower.isEmpty()))
//	                                { try{
//	                                	if(text)
//	                                    RefineTokensAndCreateIndex.handle_text_data(lower);
//	                                	else if(title)
//	                                		RefineTokensAndCreateIndex.handle_title_data(lower);
//	                                	}
//	                                catch(Exception e){
//	                                e.printStackTrace();}
//	                                }
//	                                ttext.setLength(0);
//	                            }
//	                        }
//	                        else
//	                        { 
//	                            String lower=ttext.toString().toLowerCase();
//	                            lower=lower.trim();
//	                            
//	                            
//	                            if(!(lower.length()==0||lower.isEmpty()))
//	                            {  try{
//	                            	if(text)
//	                            	RefineTokensAndCreateIndex.handle_text_data(lower);
//	                            	
//	                            	else if(title)
//	                            		RefineTokensAndCreateIndex.handle_title_data(lower);	
//	                            	}
//	                               catch(Exception e)
//	                            {e.printStackTrace();}
//	                            }
//	                            ttext.setLength(0);
//	                        }
//	                    }
//	                }	
//	                
//	                
//	            }
//		 }
		 if(id)
		 {
			 id_data=new String(ch,start,length);
			 //System.out.println(id_data);
			 if(id_data.length()>0)
			 DriverClass.DOCID_TITLE.put(id_data,title_data);
			 //DriverClass.count_id++;
			 
			 
			 
		 }
	    
}

		 

}
