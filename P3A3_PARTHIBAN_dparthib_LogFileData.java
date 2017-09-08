//Name: Dinesh Parthiban
//Original Created Date: 1st Aug 2017
//Modified Date: 5th Aug 2017
//Description: This program is used to parse the given file

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class P3A3_PARTHIBAN_dparthib_LogFileData {
	private String filename; //stores the file name
	private ArrayList <Date>time; // stores the committed time values
	private ArrayList <String >author; //stores the author name
	private Map<String, Integer> authorFreqMap; //stores the author name and its frequency
	
	
	//no arg constructor
	public P3A3_PARTHIBAN_dparthib_LogFileData() {
		this("");
	}
	
	/**
	 * parametrized  constructor
	 * @param filename
	 */
	public P3A3_PARTHIBAN_dparthib_LogFileData(String filename) {
		this.filename = filename;
		time = new ArrayList<>();
		author = new ArrayList<>();
		authorFreqMap=new HashMap<String, Integer>();
	}

	/**
	 * getter for field filename
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * setter for field filename
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}



	/**
	 * setter for list time
	 * @param time the time to set
	 */
	public void setTime(Date value) {
		time.add(value);
	}


	/**
	 * setter for list author
	 * @param author the author to set
	 */
	public void setAuthor(String value) {
		 author.add(value);
	}
	
	//return the number of commits
	public int numCommits() {
		//System.out.println("Files"+ author.size());
		return time.size();
	}
	
	//return the number of unique users
	public int numUsers() {
		//HashSet uniqueValues = new HashSet(author);
		Set<String> uniqueValues = authorFreqMap.keySet();
		return uniqueValues.size();
	}
	
	//return the earliest commit
	public Date minTime(){
		return Collections.min(time);
	}
	
	//return the latest commit
		public Date maxTime(){
			
			return Collections.max(time);
		}
		
	//load the list into Hashmap 
	public void loadHashMap() {
		for (String obj : author) {
			authorFreqMap.put(obj, (authorFreqMap.get(obj) == null ? 1 : (authorFreqMap.get(obj) + 1)));
			}
		}
		
	//return the user with most commits
		public int maxUserCommits() {
		    return Collections.max(authorFreqMap.values());
		}
		
	//return user name with max commits
		public String maxUserName(int max){
			String name="";		
			for (Map.Entry entry : authorFreqMap.entrySet()) {
	            if ((int)entry.getValue() >= (max)) {
	                name=entry.getKey()+" "+name;
	            }
	        }
			return name;
		}
	
		//return user name with max commits
		public  String topUserNames(int top) {
			String name="";
	        // Converting Map to List of Map
	        List<Map.Entry<String, Integer>> list =
	                new LinkedList<Map.Entry<String, Integer>>(authorFreqMap.entrySet());

	        //  Sort list with Collections.sort(), provide a custom Comparator
	        //    Try switch the o1 o2 position for a different order
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	            public int compare(Map.Entry<String, Integer> o1,
	                               Map.Entry<String, Integer> o2) {
	                return (o2.getValue()).compareTo(o1.getValue());
	            }
	        });

	        // Loop the sorted list and put it into a new insertion order Map LinkedHashMap
	        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> entry : list) {
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }
	        int cnt=0;
	        for (Map.Entry entry : sortedMap.entrySet()) {
	        	 if(cnt>=top)
		            break;
	            //System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
	            name=entry.getKey()+" "+name;
	            cnt++;
	        }

	        return name;
	    }
		
		//returns an arraylist of the author
		public ArrayList<String> getAuthorList(){
			return author;
		}
		
		//adds an arraylist at the end
		public void addAuthorList(ArrayList<String> e){
			author.addAll(e);
		}
		
		//returns an arraylist of the time
		public ArrayList<Date> getTimeList(){
			return time;
		}
		
		//adds an arraylist at the end
		public void addTimeList(ArrayList<Date> e){
			time.addAll(e);
		}

		
	//prints the individual file details
	public void printOutput(){
		int max=maxUserCommits();
		String mindate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(minTime());
		System.out.println("<"+filename+">,<"+mindate+">,<"+maxUserName(max)+">,<"+max+">");
	}
	
	//returns the size of the author list
	public int getAuthorSize(){
		return author.size();
	}
	
	//returns the authors within specified time interval
	public String getAuthorInterval(Date start, Date end){
		//ArrayList<String> tempAuthor= new ArrayList<>();
		P3A3_PARTHIBAN_dparthib_LogFileData topUsers = new P3A3_PARTHIBAN_dparthib_LogFileData();
		for(int i=0;i<time.size();i++)
			if((time.get(i).compareTo(start)>=0)&(time.get(i).compareTo(end)<=0)){
				topUsers.setAuthor(author.get(i));
				//System.out.println(author.get(i));
			}
			topUsers.loadHashMap();
			int top= (int) Math.ceil(((double)topUsers.numUsers())*20.00/100.00);
			//System.out.println(top);
			//topUsers.topUserName(top);
			//System.out.println("The top 20% of the users within "+topUsers.topUserNames(top));
			return topUsers.topUserNames(top);
	}

}
