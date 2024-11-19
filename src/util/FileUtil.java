package util;

import java.io.*;
import java.util.ArrayList;

public class FileUtil {
    // Read lines from a file
    public static ArrayListADT<String> readLines(String filename) {
        ArrayListADT<String> lines = new ArrayListADT<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // Write lines to a file
    public static void writeLines(String filename, ArrayListADT<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}