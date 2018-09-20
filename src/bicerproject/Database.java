 package bicerproject;
public class Database {

	 
	private static final String EXAM_LIST = "examListDatabase.txt";
	private static final String userDatabase = "userDatabase.txt";
	private static final String studentDatabase = "studentDatabase.txt";
	 
 
	 
	public static String getExamList() {
		return EXAM_LIST;
	}
	public static String getUserDatabase() {
		return userDatabase;
	}
	public static String getStudentDatabase() {
		return studentDatabase;
	}
 
	
}
