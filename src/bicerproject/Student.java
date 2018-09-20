package bicerproject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
 

public class Student extends Account{


	public static final int DISPLAY_THE_EXAM_RESULT = 1;
	public static final int DISPLAY_THE_EXAM_LIST = 2;
	public static final int EXIT = 3;
	
	static Scanner keyboard = new Scanner(System.in);

	
	public Student(String email, String password) {
		super(email, password);
		
	}
	
	public static void studentScreen()
	{
		try
		{			
			int display;
			do
			{
				System.out.println("1 - Display the exam results");
				System.out.println("2 - Display the exam list");
				System.out.println("3 - Exit");
				display = keyboard.nextInt();
				
				if(display == DISPLAY_THE_EXAM_LIST)
				{			
					checkTheExamListDatabase();
				}
				
				else if(display == DISPLAY_THE_EXAM_RESULT)
				{	
					  checkTheResultsDatabase();	  
				}	
			}while(display != EXIT);
				 new Login().setVisible(true);
			
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
		
	
	public  static void checkTheExamListDatabase()
	{
		try
		{
			System.out.println("THE EXAM LIST");
			FileReader fr  = new FileReader(Database.getExamList());
			BufferedReader br= new BufferedReader(fr);
			ArrayList <String> examList = new ArrayList<String>();
			int choice;
			String sCurrentLine;
			
			while((sCurrentLine = br.readLine()) != null)
			{
				
				System.out.println(sCurrentLine);
				sCurrentLine = sCurrentLine.substring(sCurrentLine.indexOf("-") + 2, sCurrentLine.length())+".txt";
				examList.add(sCurrentLine);
			}
			
			fr.close();
			br.close();
			
			System.out.println("Select the exam");
			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
			startTheExam(choice,examList);
			
			
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void  startTheExam(int choice,ArrayList <String> list) throws Exception
	{
		 File dir = new File("C:\\Users\\User\\eclipse-workspace\\Proje\\Exam");
		 String exam = "C:\\Users\\User\\eclipse-workspace\\Proje\\Exam\\"+list.get(choice - 1);
		 File files[] = dir.listFiles();
		 
		 for(int i = 0; i < files.length; i++)
		 {
			 if(exam.equalsIgnoreCase(files[i].getAbsolutePath()))
			 {
				 FileReader fr = new FileReader(exam);
				 BufferedReader br = new BufferedReader(fr);
				 String s;
				 while((s = br.readLine() ) != null)
					 System.out.println(s);
				 br.close();
			 }
		 }
		
	}
		
	
	public static void checkTheResultsDatabase() throws IOException
	{ 
		String  examResults [] = examResults();
		System.out.println("EXAM RESULTS");
		for(int i = 0; i < examResults.length; i++)
			System.out.println((i + 1) + "-" + examResults[i]);
		
		System.out.print("Which exams do you want to be displayed:");
		int choice = keyboard.nextInt();
		File file = new File("C:\\Users\\User\\eclipse-workspace\\Proje\\ExamResults\\"+examResults[choice - 1]);//base
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String result = "";
				while((result = br.readLine()) != null)
				{
					System.out.println(result);
				}
				
				br.close();
				fr.close();
	}
	
	public static String [] examResults()
	{
		String directoryName = "C:\\Users\\User\\eclipse-workspace\\Proje\\ExamResults";
		File directory = new File(directoryName);;
		String [] exams = null;
		 
		if(directory.isDirectory() == false)
		{
			if(directory.exists() == false)
			{
				System.err.println("There is not ExamResults folder");
			}
			else
			{
				System.err.println("ExamResults folder is empty");
			}
		}
		else
		{
			exams = directory.list();
		}
							return exams;
	}
}


 