import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Experiments {

    public static void main(String[] args) {
        // Generate a large sample of text
        String sampleText = generateSampleText(1000000);

        // Generate a SubstitutionCipher object
        SubstitutionCipher cipher = new SubstitutionCipher(System.currentTimeMillis());

        // Encrypt the sample text using the SubstitutionCipher
        List<Character> encryptedText = cipher.encrypt(stringToList(sampleText));

        // Generate a 256x256 table of character frequencies
        int[][] freqTable = generateFrequencyTable(encryptedText);

        // Calculate the percentage of times each character is mapped to each other character
        double[][] percentageTable = generatePercentageTable(freqTable, encryptedText.size());

        // Write the percentage table to a file
        writeTableToFile(percentageTable, "Table.txt");

        System.out.println("Table.txt generated successfully.");
    }

    // Generate a sample text of given length
    public static String generateSampleText(int length) {
        String text = "";
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            text += (char) (rand.nextInt(26) + 'a');
        }
        return text;
    }

    // Convert a String to a List of Characters
    public static List<Character> stringToList(String str) {
        List<Character> list = new ArrayList<Character>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list;
    }

    // Generate a 256x256 frequency table for the given List of Characters
    public static int[][] generateFrequencyTable(List<Character> text) {
        int[][] table = new int[256][256];
        for (int i = 0; i < text.size() - 1; i++) {
            char c1 = text.get(i);
            char c2 = text.get(i + 1);
            table[c1][c2]++;
        }
        return table;
    }

    // Generate a 256x256 table of percentages
    public static double[][] generatePercentageTable(int[][] freqTable, int total) {
        double[][] table = new double[256][256];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                table[i][j] = ((double) freqTable[i][j] / total) * 100;
            }
        }
        return table;
    }

    // Write the percentage table to a file
    public static void writeTableToFile(double[][] table, String filename) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(filename));
            for (int i = 0; i < 256; i++) {
                for (int j = 0; j < 256; j++) {
                    writer.print(table[i][j] + "\t");
                }
                writer.println();
            }
        } catch (Exception e) {
            System.out.println("Error writing table to file: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
