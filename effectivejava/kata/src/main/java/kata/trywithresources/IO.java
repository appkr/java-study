package kata.trywithresources;

import java.io.*;

public class IO {

    public static void main(String[] args) {
        File file = new File("data.txt");
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            writer.println("FooBar");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            System.out.println("---");
            System.out.println(line);
            System.out.println("---");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
