package bicerproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Admin extends Account {

    public static int OPEN_THE_EXAMS = 1;
    public static int DISPLAY_THE_STUDENTDATABASE = 2;
    public static int CONTROL_THE_SYSTEM = 3;
    public static final int EXIT = 4;

    public Admin(String email, String password) {
        super(email, password);

    }

    public static ArrayList<String> readStudentDataBase() //sifreli mailleri cozerek maillere ulasir bunu da arrayliste gomer
    {
        String path = Database.getStudentDatabase();
        BufferedReader br = null;
        FileReader fr = null;
        ArrayList<String> listOfStudentEmail = new ArrayList<String>();

        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                int spaceIndex = currentLine.indexOf(" ");
                listOfStudentEmail.add(currentLine.substring(0, spaceIndex));//sifreli emailleri arrayliste attik
            }

            for (int i = 0; i < listOfStudentEmail.size(); i++) {
                String encodedMail = listOfStudentEmail.get(i);
                String decodedMail = new String(Base64.getDecoder().decode(encodedMail.getBytes()));
                listOfStudentEmail.set(i, decodedMail);
            }
        } catch (IOException e) {
           JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listOfStudentEmail;

    }

    public static void makeTheExam() {
        try {

            File file = new File(Database.getExamList());
            FileWriter fileWriter = new FileWriter(file);
            Scanner keyboard = new Scanner(System.in);
            String examName;
            int numOfExams, count = 0;
            BufferedWriter bw = null;
            FileWriter fw = null;
            System.out.println("How many exams would you like to enter");
            numOfExams = keyboard.nextInt();
            System.out.println("Please enter the exams.");
            while (count < numOfExams) {
                if (!file.exists()) {
                    file.createNewFile();
                }
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                examName = new Scanner(System.in).nextLine();
                bw.write((count + 1) + " - " + examName);
                bw.newLine();
                bw.close();
                count++;
            }

            fileWriter.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        } finally {
            System.out.println("The exams that were entered are successfuly added into the database");
        }

    }

}
