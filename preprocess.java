import java.io.*;
import java.util.*;

public class preprocess {
    public static void main(String[] args) {
        String inputFilePath = "/home/askf2/eclipse-workspace/PIG/movies.txt";
        String outputFilePath = "/home/askf2/eclipse-workspace/PIG/movies.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            Map<String, String> review = new LinkedHashMap<>();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (!review.isEmpty()) {
                        writeReviewToFile(writer, review);
                        review.clear();
                    }
                } else {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        review.put(parts[0].trim(), parts[1].trim().replaceAll(",", " ")); // Replace commas to avoid CSV format issues
                    }
                }
            }

            // Write the last review if it exists
            if (!review.isEmpty()) {
                writeReviewToFile(writer, review);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeReviewToFile(BufferedWriter writer, Map<String, String> review) throws IOException {
        // Format the review Map into a CSV line
        StringJoiner joiner = new StringJoiner(",");
        for (String key : review.keySet()) {
            String value = review.get(key);
            joiner.add("\"" + value + "\""); // Encapsulate values in quotes to handle commas and newlines within text
        }
        writer.write(joiner.toString() + "\n");
    }
}
