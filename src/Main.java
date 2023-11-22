import java.io.*;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int menu;

        do {
            menu = scanner.nextInt();
            scanner.nextLine();

            if (menu == -1) {
                break;
            } else if (menu == 1) {
                String fileName = scanner.nextLine();
                output(fileName);
            } else if (menu == 2) {
                String fileName = scanner.nextLine();
                reWriteFile(fileName);
                output(fileName);
            } else if (menu == 3) {
                String fileName = scanner.nextLine();
                int key = scanner.nextInt();
                String fileContent = readFile(fileName);
                String fileRewrite = encrypt(fileContent, key);
                reWrite(fileName, fileRewrite);
                output(fileName);
            } else {
                System.out.println("n/a");
            }
        } while (menu != -1);
    }

    public static void output(String expression) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(expression));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File " + expression + " not found");
        }
    }

    public static void reWriteFile(String expression) {
        Scanner scanner = new Scanner(System.in);

        Path path = Paths.get(expression);
        String text = scanner.nextLine();

        try {
            Files.write(path, text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Unable to write information to a file");
        }
    }

    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            System.out.println("Can't read file");
        }

        return content.toString();
    }

    private static String encrypt(String expression, int key) {
        char[] suggestion = new char[expression.length()];
        for (int i = 0; i < expression.length(); i++) {
            suggestion[i] = (char) ((int) (expression.charAt(i)) + key);
        }
        System.out.println(suggestion);
        return String.valueOf(suggestion);
    }

    private static void reWrite(String expression, String encryptedContent) {
        try {
            FileWriter fileWriter = new FileWriter(expression);
            fileWriter.write(encryptedContent + "\n");
            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            System.out.println("Can't rewrite file");
        }
    }
}
