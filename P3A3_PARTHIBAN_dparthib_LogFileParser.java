//Name: Dinesh Parthiban
//Original Created Date: 1st Aug 2017
//Modified Date: 5th Aug 2017
//Description: This program is used to parse the given file

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.opencsv.CSVWriter;


public class P3A3_PARTHIBAN_dparthib_LogFileParser {
	
	private String logFileName; //stores the log file name
	private ArrayList <P3A3_PARTHIBAN_dparthib_LogFileData> logDataList;
	
	//no arg constructor
	public P3A3_PARTHIBAN_dparthib_LogFileParser() {
		this("");
	}

	//parameterized constructor
	public P3A3_PARTHIBAN_dparthib_LogFileParser(String fn) {
		logFileName=fn;
		logDataList= new ArrayList<>();
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return logFileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.logFileName = fileName;
	}

	//used to read the log file
	public void readLogFile() throws IOException, ParseException{
		//this is used to indicate the end of file
		String eof="=============================================================================";	
		String thisLine=null; //hold the current line
		
	         // open input stream test.txt for reading purpose.
	         BufferedReader br = new BufferedReader(new FileReader(logFileName));
	         //read line 
	         while ((thisLine = br.readLine()) != null) {
	        	//separating by file
	        	 P3A3_PARTHIBAN_dparthib_LogFileData logData = new P3A3_PARTHIBAN_dparthib_LogFileData();
	        	while((thisLine.equals(eof))!= true){	   
	        		
	        		//check file names
	        		if(parseFileNames(thisLine)){
	        			//System.out.println(thisLine.substring(10,thisLine.length()-1));
	        			logData.setFilename(thisLine.substring(10,thisLine.length()));
	        		}
	        		//check author name
	        		if (thisLine.contains("author:")){
	        			//System.out.println(parseAuthorName(thisLine));
	        			//System.out.println(parseTime(thisLine));
	        			logData.setAuthor(parseAuthorName(thisLine));
	        			logData.setTime(parseTime(thisLine));
	        		}
	        		
	        		thisLine=br.readLine();
	        	}
	        	logDataList.add(logData);
	        	logData.loadHashMap();
	         } 
	         
	         br.close();
	         
	      
	}
	
	//used to check for file names in the log file
	public boolean parseFileNames(String thisLine){
		boolean result=false;
		if(thisLine.length()>9)
			if(thisLine.substring(0,9).equals("RCS file:"))
				result=true;
		return result;
	}
	
	//used to check for author names in the log file
	public String parseAuthorName(String thisLine){
		String name="-1";
		try{
		String [] splitBySemicolon= thisLine.split(";");
		String [] splitByColon= splitBySemicolon[1].split(":");
		name= splitByColon[1];
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage());
			System.out.println(thisLine);
		}
		return name;
	}
	
	//used to check for time of commit in the log file
		public Date parseTime(String thisLine) throws ParseException{
			String [] splitBySemicolon= thisLine.split(";");
			String [] splitByColon= splitBySemicolon[0].split(": ");
			Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(splitByColon[1].substring(0, 19)); 
			return date1;
		}
	
	//return the number of files
		public int numFiles(){
			return logDataList.size();
		}
		
	//display the file with most number of revisions
		public void mostRevisions(){

			int maxLogfiles=0;
			for(int i=0;i<=logDataList.size()-1;i++){
				if(logDataList.get(i).numCommits() > maxLogfiles){
					maxLogfiles=logDataList.get(i).numCommits();
					//index=i;
				}					
			}
			System.out.print("The file with most number of revisions: ");
			for(int i=0;i<=logDataList.size()-1;i++){
				if(logDataList.get(i).numCommits() == maxLogfiles)
					System.out.print(logDataList.get(i).getFilename()+" ");
			}
			System.out.println();
			
		}
	
		//display the file with most number of users
		public void mostUsers(){

			int maxUsers=0;
			for(int i=0;i<=logDataList.size()-1;i++){
				if(logDataList.get(i).numUsers() > maxUsers){
					maxUsers=logDataList.get(i).numUsers();
				}					
			}
			//System.out.println(maxUsers);
			System.out.print("The file with most number of users: ");
			for(int i=0;i<=logDataList.size()-1;i++){
				if(logDataList.get(i).numUsers() == maxUsers)
					System.out.print(logDataList.get(i).getFilename()+" ");
			}
			System.out.println();
		}
		
		//prints the file with earliest commit
		public void earliestCommit() throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date temp = sdf.parse("9999-12-31"); //higher date
			for(int i=0;i<=logDataList.size()-1;i++){
				if(logDataList.get(i).minTime().compareTo(temp) < 0){
					temp=logDataList.get(i).minTime();
					//index=i;
				}					
			}
			String mindate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp);
			System.out.println("Earliest commit to the entire log file:"+mindate);
			System.out.print("The file name of the earliest commit:");
			for(int i=0;i<=logDataList.size()-1;i++){
				if(logDataList.get(i).minTime().compareTo(temp)==0 )
					System.out.print(logDataList.get(i).getFilename()+" ");
			}
			System.out.println();
		}
		
		//returns the file with earliest commit
		public Date earliestCommitTime() throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date temp = sdf.parse("9999-12-31"); //higher date
			for(int i=0;i<=logDataList.size()-1;i++){
				if(logDataList.get(i).minTime().compareTo(temp) < 0){
					temp=logDataList.get(i).minTime();
					//index=i;
				}					
			}
			return temp;
		}
	
		//prints the individual log file details
		public void logFileDetails(){
			System.out.println("---------------------------------------------------------------------------------------------------------");
			System.out.println("The individual file details are print in the below format:");
			System.out.println("<file name>, <earliest commit of this file>, <user(s) with most commits>, <num of commits by this user>");
			for(int i=0;i<=logDataList.size()-1;i++)
				logDataList.get(i).printOutput();
		}
		
		//prints user details
		public void printUserDetatils(){
			P3A3_PARTHIBAN_dparthib_LogFileData entireLog = new P3A3_PARTHIBAN_dparthib_LogFileData();
			for(int i=0;i<=logDataList.size()-1;i++){
				entireLog.addAuthorList(logDataList.get(i).getAuthorList());
			}	
			entireLog.loadHashMap();
			int max=entireLog.maxUserCommits();
			System.out.println("Most number of commits:"+max);
			System.out.println("The user with most number of commits:"+entireLog.maxUserName(max));
		}
		
		//writes the log file data to CSV
		public void writeLogToCSV()throws Exception{
			BufferedWriter out = new BufferedWriter(new FileWriter("result.csv"));
	        CSVWriter writer = new CSVWriter(out);
	        String[] values = {"FileName","Number of commits","First date of commit","Last date of commit"};
	        writer.writeNext(values);
			for(int i=0;i<=logDataList.size()-1;i++){
				String file=logDataList.get(i).getFilename();
				String commits=Integer.toString(logDataList.get(i).numCommits());
				String mindate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logDataList.get(i).minTime());
				String maxdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logDataList.get(i).maxTime());
				values = new String [] {file,commits,mindate,maxdate};
				writer.writeNext(values);
				//System.out.println(logDataList.get(i).getFilename()+" "+logDataList.get(i).numCommits()+" "+logDataList.get(i).minTime()+" "+logDataList.get(i).maxTime());
			}
			writer.close();
			out.close();
		}
		
		//does analysis on the time specified time range
		public void manualDataAnalysis(Date startdate,Date enddate) throws IOException{
			P3A3_PARTHIBAN_dparthib_LogFileData entireLog = new P3A3_PARTHIBAN_dparthib_LogFileData();
			for(int i=0;i<=logDataList.size()-1;i++){
				entireLog.addAuthorList(logDataList.get(i).getAuthorList());
				entireLog.addTimeList(logDataList.get(i).getTimeList());
			}
			String usr=entireLog.getAuthorInterval(startdate, enddate);
			BufferedWriter out = new BufferedWriter(new FileWriter("result.csv"));
	        CSVWriter writer = new CSVWriter(out);
	        String[] values = {"Start Date","End Date","Top 20% Users"};
	        writer.writeNext(values);
	        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
			String end = myFormat.format(enddate);
			String strt = myFormat.format(startdate);
			//System.out.println(end+" "+strt);
			values =new String []{strt,end,usr};
			writer.writeNext(values);	
	        writer.close();
	        System.out.println("The output has been written in result.csv");
			out.close();	
		}
		
		//does analysis on the time specified time range
		public String logDataAnalysis(Date startdate,Date enddate){
			P3A3_PARTHIBAN_dparthib_LogFileData entireLog = new P3A3_PARTHIBAN_dparthib_LogFileData();
			for(int i=0;i<=logDataList.size()-1;i++){
				entireLog.addAuthorList(logDataList.get(i).getAuthorList());
				entireLog.addTimeList(logDataList.get(i).getTimeList());
			}
			String users=entireLog.getAuthorInterval(startdate, enddate);
			return users;
		}

		
		//does full file analysis based on yearly
		public void yearlyAnalysis() throws ParseException, IOException{
			Date start=earliestCommitTime();
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			//c.add(Calendar.YEAR, n);
			int year=c.get(Calendar.YEAR);
			String endtDate="31-12-"+c.get(Calendar.YEAR);
			SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date end = myFormat.parse(endtDate);
			//System.out.println(year+" "+c.get(Calendar.YEAR));
			Calendar now = Calendar.getInstance();   // Gets the current date and time
			int current = now.get(Calendar.YEAR);       // The current year
			String [][] val=new String[current-year][3];
			int i=0;
			while(year < current ){
				String usr=logDataAnalysis(start,end);
				year++;
				String strtDate="01-01-"+year;
				endtDate="31-12-"+year;
				start = myFormat.parse(strtDate);
				end = myFormat.parse(endtDate);
				val[i][0]=strtDate;
				val[i][1]=endtDate;
				val[i][2]=usr;
				i++;
			}

			BufferedWriter out = new BufferedWriter(new FileWriter("result.csv"));
	        CSVWriter writer = new CSVWriter(out);
	        String[] values = {"Start Date","End Date","Top 20% Users"};
	        writer.writeNext(values);
	        for(i=0;i<val.length;i++){
				values =new String []{val[i][0],val[i][1],val[i][2]};
				writer.writeNext(values);
			}		
	        writer.close();
	        System.out.println("The output has been written in result.csv");
			out.close();	
		}

		//does full file analysis based on half yearly
		public void halfYearlyAnalysis() throws ParseException, IOException{
			Date start=earliestCommitTime();
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			//c.add(Calendar.YEAR, n);
			int year=c.get(Calendar.YEAR);
			String endtDate="30-6-"+c.get(Calendar.YEAR);
			int mon_ind=0;// indicator which stores the 1st half or 2nd half of year 
			SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date end = myFormat.parse(endtDate);
			//System.out.println(year+" "+c.get(Calendar.YEAR));
			Calendar now = Calendar.getInstance();   // Gets the current date and time
			int current = now.get(Calendar.YEAR);       // The current year
			String [][] val=new String[(current-year)*2][3];
			int i=0;
			while(year < current ){
				if(mon_ind==0){ //2nd half
					String usr=logDataAnalysis(start,end);
					mon_ind=1;
					String strtDate="01-07-"+year;
					endtDate="31-12-"+year;
					start = myFormat.parse(strtDate);
					end = myFormat.parse(endtDate);
					val[i][0]=strtDate;
					val[i][1]=endtDate;
					val[i][2]=usr;
					i++;
				}
				else{ //1st half
					String usr=logDataAnalysis(start,end);
					year++;
					String strtDate="01-01-"+year;
					endtDate="30-6-"+year;
					start = myFormat.parse(strtDate);
					end = myFormat.parse(endtDate);
					val[i][0]=strtDate;
					val[i][1]=endtDate;
					val[i][2]=usr;
					i++;
					mon_ind=0;
				}
			}

			BufferedWriter out = new BufferedWriter(new FileWriter("result.csv"));
	        CSVWriter writer = new CSVWriter(out);
	        String[] values = {"Start Date","End Date","Top 20% Users"};
	        writer.writeNext(values);
	        for(i=0;i<val.length;i++){
				values =new String []{val[i][0],val[i][1],val[i][2]};
				writer.writeNext(values);
			}		
	        writer.close();
	        System.out.println("The output has been written in result.csv");
			out.close();	
		}

		//does full file analysis based on quarterly
		public void quaterlyAnalysis() throws ParseException, IOException{
			Date start=earliestCommitTime();
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			//c.add(Calendar.YEAR, n);
			int year=c.get(Calendar.YEAR);
			String endtDate="31-3-"+c.get(Calendar.YEAR);
			int mon_ind=0;// indicator which stores the quarter of year 
			SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date end = myFormat.parse(endtDate);
			//System.out.println(year+" "+c.get(Calendar.YEAR));
			Calendar now = Calendar.getInstance();   // Gets the current date and time
			int current = now.get(Calendar.YEAR);       // The current year
			String [][] val=new String[(current-year)*4][3];
			int i=0;
			while(year < current ){
				if(mon_ind==0){ //1st quarter
					String usr=logDataAnalysis(start,end);
					mon_ind=1;
					String strtDate="01-04-"+year;
					endtDate="30-6-"+year;
					start = myFormat.parse(strtDate);
					end = myFormat.parse(endtDate);
					val[i][0]=strtDate;
					val[i][1]=endtDate;
					val[i][2]=usr;
					i++;
				}
				else if(mon_ind==1){//2nd quarter
					String usr=logDataAnalysis(start,end);
					mon_ind=2;
					String strtDate="01-07-"+year;
					endtDate="30-9-"+year;
					start = myFormat.parse(strtDate);
					end = myFormat.parse(endtDate);
					val[i][0]=strtDate;
					val[i][1]=endtDate;
					val[i][2]=usr;
					i++;
				}
				if(mon_ind==2){//3rd quarter
					String usr=logDataAnalysis(start,end);
					mon_ind=3;
					String strtDate="01-10-"+year;
					endtDate="31-12-"+year;
					start = myFormat.parse(strtDate);
					end = myFormat.parse(endtDate);
					val[i][0]=strtDate;
					val[i][1]=endtDate;
					val[i][2]=usr;
					i++;
				}
				else if(mon_ind==3){//4th quarter
					String usr=logDataAnalysis(start,end);
					year++;
					String strtDate="01-01-"+year;
					endtDate="31-3-"+year;
					start = myFormat.parse(strtDate);
					end = myFormat.parse(endtDate);
					val[i][0]=strtDate;
					val[i][1]=endtDate;
					val[i][2]=usr;
					i++;
					mon_ind=0;
				}
			}

			BufferedWriter out = new BufferedWriter(new FileWriter("result.csv"));
	        CSVWriter writer = new CSVWriter(out);
	        String[] values = {"Start Date","End Date","Top 20% Users"};
	        writer.writeNext(values);
	        for(i=0;i<val.length;i++){
				values =new String []{val[i][0],val[i][1],val[i][2]};
				writer.writeNext(values);
			}		
	        writer.close();
	        System.out.println("The output has been written in result.csv");
			out.close();	
		}

		
}
