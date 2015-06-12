/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pagerank;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;
import java.net.*;

/**
 *
 * @author ishan
 */
public class PageRank {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PageRank obj = new PageRank(); //Creating class object
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        String namec = name; // Company name
        name = name.replaceAll("[-+.^:,]","");// to check page rank
        name = name.replaceAll(" ","").toLowerCase();
        System.out.println(name);
         String[] domains = new String[]{"com","in","net","io","org"};// more domains can be added
         int npr = 0; // stores the highest page rank
         int n=0;// stores the site with highest page rank
         for (int i=0;i<domains.length;i++){
       String name1 = name+"."+domains[i];
       if (obj.getPR(name1)>npr){
           npr = obj.getPR(name1);
           n=i;
       }
    }
	if (npr>2){
           System.out.println("www"+"."+name+"."+domains[n]);// if domain with highest page rank greater than equal to two
        }

        else { //if no site for that domain with page rank > 2
        
           String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
         //System.out.println("Enter company name");
        
    String search = namec;
        System.out.println(search);
String[] strs = search.split(" ");
System.out.println(strs);
        //String strs1 = Arrays.toString(strs);
    String charset = "UTF-8";
try {
    URL url = new URL(google + URLEncoder.encode(search, charset));
    Reader reader = new InputStreamReader(url.openStream(), charset);
    

    
GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);// Show title and URL of 1st result.
int i =0;
while(i<100){
   String check  = results.getResponseData().getResults().get(i).getTitle().toLowerCase();
   int flag = 0;
    
   for (String str: strs){
       if (!(check.contains(str))){
       flag=1;
       i++;
       continue;
       }
   }
   
    System.out.println(i);
    System.out.println(results.getResponseData().getResults().get(i).getTitle());
    System.out.println(results.getResponseData().getResults().get(i).getUrl());
    String hostname =  results.getResponseData().getResults().get(i).getUrl();
     URL aURL = new URL(hostname);
     System.out.println(aURL.getHost());
   break;
   
}    // TODO code application logic here
   } catch (Exception e){
}
        
        
        
        
        
        
        
        }

//System.out.println(obj.getPR("mkyong.com"));
    }
     public int getPR(String domain) {
 
	String result = "";
 
	JenkinsHash jenkinsHash = new JenkinsHash();
	long hash = jenkinsHash.hash(("info:" + domain).getBytes());
 
	//Append a 6 in front of the hashing value.
	String url = "http://toolbarqueries.google.com/tbr?client=navclient-auto&hl=en&"
	   + "ch=6" + hash + "&ie=UTF-8&oe=UTF-8&features=Rank&q=info:" + domain;
 
	System.out.println("Sending request to : " + url);
 
	try {
		URLConnection conn = new URL(url).openConnection();
 
		BufferedReader br = new BufferedReader(new InputStreamReader(
			conn.getInputStream()));
 
		String input;
		while ((input = br.readLine()) != null) {
 
			// What Google returned? Example : Rank_1:1:9, PR = 9
			System.out.println(input);
 
			result = input.substring(input.lastIndexOf(":") + 1);
		}
 
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
 
	if ("".equals(result)) {
		return 0;
	} else {
		return Integer.valueOf(result);
	}
 
  }
 

}
