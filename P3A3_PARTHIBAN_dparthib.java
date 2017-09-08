//Name: Dinesh Parthiban
//Original Created Date: 1st Aug 2017
//Modified Date: 5th Aug 2017
//Description: main program for the CocoPanther Project Activity 3

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class P3A3_PARTHIBAN_dparthib {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the file log name");
		String file=input.nextLine();
		P3A3_PARTHIBAN_dparthib_LogFileParser obj = new P3A3_PARTHIBAN_dparthib_LogFileParser(file);
		obj.readLogFile();
		String choice,cont="1";
		while(cont.equals("1")){
			System.out.println("Data analysis");
			System.out.println("1. Manually specify the date Range");
			System.out.println("2. Generate analysis for the full file");
			choice=input.nextLine();
			switch(choice){
			case "1": manualDataRange(obj);
			break;
			case "2": 	System.out.println("1. Yearly 2. Half Yearly 3. Quaterly");
						String choice1=input.nextLine();
						//String choice1="1";
						switch(choice1){
						case "1":obj.yearlyAnalysis();
						break;
						case "2":obj.halfYearlyAnalysis();
						break;
						case "3":obj.quaterlyAnalysis();
						break;
						default:System.out.println("Invalid choice");
						}
			break;
			default: System.out.println("Invalid choice");
			}
			System.out.println("To re-run the program press 1");
			cont=input.nextLine();
		}
		
	}
	
	public static void manualDataRange(P3A3_PARTHIBAN_dparthib_LogFileParser obj){
		Scanner input = new Scanner(System.in);
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = null,date2=null;
		System.out.println("Enter start date(dd-mm-yyyy):");
		String strtDate=input.nextLine();
		//String strtDate="01-01-2013";
		try{
			
			if(null != strtDate && strtDate.trim().length() > 0){
				date1 = myFormat.parse(strtDate);
			}
			System.out.println("Enter end date(dd-mm-yyyy):");
			String endtDate=input.nextLine();
			//String endtDate="31-12-2013";
			if(null != endtDate && endtDate.trim().length() > 0){
				 date2 = myFormat.parse(endtDate);
			}
			   //System.out.println(date1+" "+date2);
			if(date1.compareTo(date2)<0)
				obj.manualDataAnalysis(date1,date2);
			else
			System.out.println("Start Date should be greater then End date");
			//obj.writeLogToCSV();
		}
		catch(ParseException e){
			System.out.println("Invalid Date Format.. Re-Run program");
			//e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
