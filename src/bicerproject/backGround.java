/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bicerproject;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class backGround {

    public static void createAccount(String email, String password) {

        try {
            Account account = null;

            if (email.charAt(0) == 's') {
                account = new Student(email, password);
            } else if (email.charAt(0) == 'l') {
                account = new Lecturer(email, password);
            } else if (email.charAt(0) == 'a') {
                account = new Admin(email, password);
            }

            addAccountToTxt(account);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void addAccountToTxt(Account account) {
        if (account.getEmail().charAt(0) == 's') {
            try {
                File file = new File(Database.getStudentDatabase());
                BufferedWriter bw = null;
                FileWriter fw = null;

              

                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                String encodedPass = Base64.getEncoder().encodeToString(account.getPassword().getBytes("utf-8"));
                String encodedEmail = Base64.getEncoder().encodeToString(account.getEmail().getBytes("utf-8"));
                bw.write(encodedEmail + " " + encodedPass);
                bw.newLine();
                bw.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                checkTheAccountType(account);

            }
        } else if ((account.getEmail().charAt(0) == 'a') || account.getEmail().charAt(0) == 'l') {
            try {
                File file = new File(Database.getUserDatabase());
                BufferedWriter bw = null;
                FileWriter fw = null;

                if (!file.exists()) {
                    file.createNewFile();
                }

                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                String encodedPass = Base64.getEncoder().encodeToString(account.getPassword().getBytes("utf-8"));
                String encodedEmail = Base64.getEncoder().encodeToString(account.getEmail().getBytes("utf-8"));
                bw.write(encodedEmail + " " + encodedPass);
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            } finally {
                checkTheAccountType(account);
            }
        }

    }

    public static boolean checkTheDatabase(String mail, String password) {
        boolean found = true;
        try {
            ArrayList<String> emailPasswordDuo = new ArrayList<String>();
            File file = null;
            if (mail.charAt(0) == 's') {
                file = new File(Database.getStudentDatabase());
            } else {
                file = new File(Database.getUserDatabase());
            }

            Scanner scan = new Scanner(file);
            Account account = null;
            String encodedMail = "", encodedPassword = "";
            byte[] decodedMail, decodedPassword;

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                emailPasswordDuo.add(line); // sifreli mail ve password eklendi
            }
            scan.close();

            for (int i = 0; i < emailPasswordDuo.size(); i++) {
                String line = emailPasswordDuo.get(i);
                encodedMail = line.substring(0, line.indexOf(" "));
                encodedPassword = line.substring(line.indexOf(" ") + 1, line.length());
                decodedMail = Base64.getDecoder().decode(encodedMail.getBytes());
                decodedPassword = Base64.getDecoder().decode(encodedPassword.getBytes());

                if (new String(decodedMail).equals(mail) && new String(decodedPassword).equals(password)) {
                    if (new String(decodedMail).charAt(0) == 's') {

                        checkTheAccountType(new Student(new String(decodedMail), new String(decodedPassword)));
                        found = true;
                        break;
                    } else if (new String(decodedMail).charAt(0) == 'a') {
                        checkTheAccountType(new Admin(new String(decodedMail), new String(decodedPassword)));
                        found = true;
                        break;
                    } else if (new String(decodedMail).charAt(0) == 'l') {
                        checkTheAccountType(new Lecturer(new String(decodedMail), new String(decodedPassword)));
                        found = true;
                        break;
                    }
                } else {
                    found = false;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "The account could not found");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return found;
    }

    public static void checkTheAccountType(Account account) {
        try {
            if (account instanceof Student) {
                new studentPage().setVisible(true);
            } else if (account instanceof Admin) {
                new adminPage().setVisible(true);
            } else if (account instanceof Lecturer) {
                new LecturerPage().setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
