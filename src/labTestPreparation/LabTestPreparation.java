package labTestPreparation;

/*
 * Name: Kaniel Koh Wei Zhe
 * Email ID: kaniel.koh.2020
 */

import java.io.File;
import java.util.Scanner;

public class LabTestPreparation {
    public static void main(String[] args) {
        formatString();
    }

    public static String formatString() {
        // Format to insert for String
        String string1 = String.format("%s", "hello"); // Expected "hello";
        // Format to insert two decimal point
        String string2 = String.format("%.2f", 1.223);
        // Format to insert integer into string
        String string3 = String.format("%d", 123);
        // Format to insert space into string
        String string4 = String.format("hello%n%s", "hello");
        return "";
    }

    public static void getScanner() {
        // Get user input
        Scanner scanner = new Scanner(System.in);
    }

    public static void fileRelated() {
        // Check if file exists
        File file = new File("file.txt");
        if (file.exists()) {
            System.out.println("Yes, file.txt exists");
        }

        // Read file
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        // Used to check the CWD of the program
        File fSecond = new File("./");
        System.out.println(fSecond.getAbsolutePath());
    }
}
