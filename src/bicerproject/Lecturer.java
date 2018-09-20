package bicerproject;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.NoSuchElementException;

public class Lecturer extends Account {

    public static final int PREPARE_THE_EXAMS = 1;
    public static final int GIVE_THE_GRADES = 2;
    public static final int PREPARE_THE_LECTURE_NOTES = 3;
    public static final int EXIT = 4;
    public static Scanner keyboard = new Scanner(System.in);

    public Lecturer(String email, String password) {
        super(email, password);

    }

    public static void displayTheExams() throws NoSuchElementException, IOException {
        FileReader fr = new FileReader(Database.getExamList());
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> examList = new ArrayList<String>();
        int choice;
        String sCurrentLine;

        while ((sCurrentLine = br.readLine()) != null) {

            System.out.println(sCurrentLine);
            sCurrentLine = sCurrentLine.substring(sCurrentLine.indexOf("-") + 2, sCurrentLine.length()) + ".txt";
            examList.add(sCurrentLine);
        }
        fr.close();
        br.close();

        System.out.println("Select the exam");
        choice = keyboard.nextInt();
        giveTheGrades(examList.get(choice - 1));
    }

    public static void giveTheGrades(String exam) throws NoSuchElementException, IOException {
        ArrayList<String> listOfStudentEmail = new ArrayList<String>();
        listOfStudentEmail = readStudentDataBase();
        createExamFolder("Results");

        File file = new File("C:\\Users\\User\\Documents\\NetBeansProjects\\BicerProject\\ExamResults\\" + exam);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        float grade = 0;
        for (int i = 0; i < listOfStudentEmail.size(); i++) {
            System.out.print(listOfStudentEmail.get(i) + "'s grade =");
            grade = keyboard.nextFloat();
            bw.write(listOfStudentEmail.get(i) + "=" + grade);
            bw.newLine();
            bw.flush();
        }
        fileWriter.close();
        bw.close();
    }

    public static void prepareTheExams() throws NoSuchElementException, IOException {

        FileReader fr = null;
        BufferedReader br = null;
        File file = new File(Database.getExamList());//base
        fr = new FileReader(file);
        br = new BufferedReader(fr);
        String choice = "";
        String nameOfExam;
        ArrayList<String> exams = new ArrayList<String>();

        while ((nameOfExam = br.readLine()) != null) {
            nameOfExam = nameOfExam.substring(nameOfExam.indexOf("-") + 2, nameOfExam.length());
            exams.add(nameOfExam);
        }

        System.out.println("Which exam do you want to submit\n" + exams.toString());

        choice = keyboard.nextLine();
        String lastChoice[] = choice.split(" ");
        int count = 0;
        while (count < lastChoice.length) {
            nameOfExam = exams.get((Integer.parseInt(lastChoice[count])) - 1) + ".txt";
            createExamFolder(nameOfExam);
            count++;
        }
        fr.close();
        br.close();

    }

    public static void assistanDuty(int dutyNum) throws NoSuchElementException, IOException {

        if (dutyNum == GIVE_THE_GRADES) {
            displayTheExams();
        } else if (dutyNum == PREPARE_THE_EXAMS) {
            prepareTheExams();
        } else if (dutyNum == EXIT) {
            new Login().setVisible(true);
        }
       

    }

    public static ArrayList<String> readStudentDataBase() throws IOException {
        String path = Database.getStudentDatabase();
        BufferedReader br = null;
        FileReader fr = null;
        ArrayList<String> listOfStudentEmail = new ArrayList<String>();

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

        br.close();
        fr.close();
        return listOfStudentEmail;

    }

    public static void createExamFolder(String exam) {
        if (exam.contains(".txt")) {
            String examFolderPath = "C:\\Users\\User\\Documents\\NetBeansProjects\\BicerProject\\Exam\\";
            try {
                File examFolder = new File(examFolderPath);
                if (!examFolder.exists()) {
                    if (examFolder.mkdir()) {
                        File examFile = new File(examFolderPath + exam);
                        if (!examFile.exists() && !examFile.isDirectory()) {
                            examFile.createNewFile();
                        }
                    }

                } else {
                    File examFile = new File(examFolderPath + exam);
                    if (!examFile.exists() && !examFile.isDirectory()) {
                        examFile.createNewFile();
                    }
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());;
            }
        } else {
            String examFolderPath = "C:\\Users\\User\\Documents\\NetBeansProjects\\BicerProject\\ExamResults\\";
            try {
                File examFolder = new File(examFolderPath);
                if (!examFolder.exists()) {
                    if (examFolder.mkdir()) {
                        File examFile = new File(examFolderPath);
                        if (!examFile.exists() && !examFile.isDirectory()) {
                            examFile.createNewFile();
                        }
                    }

                } else {
                    File examFile = new File(examFolderPath);
                    if (!examFile.exists() && !examFile.isDirectory()) {
                        examFile.createNewFile();
                    }
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }

}
