import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Main {
    static void main() {
        boolean exit = false;
        while (!exit) {
            Scanner input = new Scanner(System.in);
            System.out.println("Please provide a word: ");
            String word = input.nextLine();

            if(word.equals("PLEASEEXIT")){
                exit = true;
            }
            int nSimilar = 10;
            String[] similar = new String[nSimilar];
            similar = similarWords(word, nSimilar);

            for (int i = 0; i < similar.length; i++){
                System.out.println(similar[i]);
            }
            System.out.println();
        }
    }

    public static String[] similarWords (String input, int nSimilar){
        String[] words = new String[10000];
        words = fileReader();
        double[] similarity = new double[10000];

        for (int i = 0; i < words.length; i++){
            if (input.length() < words[i].length()){
                similarity[i] = compareDifferentLen(input, words[i]);
            } else if (input.length() > words[i].length()) {
                similarity[i] = compareDifferentLen(words[i], input);
            } else {
                similarity[i] = compareSameLen(input, words[i]);
            }
        }
        for (int i = 0; i < words.length; i++){
            for (int j = 0; j < words.length - 1 - i; j++){
                if (similarity[j] > similarity[j+1]){
                    double tempD = similarity[j];
                    similarity[j] = similarity[j+1];
                    similarity[j+1] = tempD;
                    String tempS = words[j];
                    words[j] = words[j+1];
                    words[j+1] = tempS;
                }
            }
        }

        String[] result = new String[nSimilar];
        int i = 0;
        int j = 0;
        while (j < nSimilar){
            if (similarity[i] != 0) {
                double value = similarity[i];
                DecimalFormat df = new DecimalFormat("#.##");
                result[j] = words[i] + " with similarity: " + df.format(value);
                j++;
            }
            i++;
        }
        return result;
    }

    public static double compareSameLen (String word1, String word2){
        int len = word1.length();
        int[] vector1 = new int[len];
        int[] vector2 = new int[len];
        for (int i = 0; i < len; i++){
            char c1 = word1.charAt(i);
            char c2 = word2.charAt(i);
            vector1[i] = c1 - 'a' + 1;
            vector2[i] = c2 - 'a' + 1;
        }
        double result = 0;
        for (int i = 0; i < len; i++){
            double difference = (vector1[i] - vector2[i])*(vector1[i] - vector2[i]);
            result = result + difference;
        }
        result = Math.sqrt(result);
        return result;
    }

    public static double compareDifferentLen (String shorter, String longer){
        int lenL = longer.length();
        int lenS = shorter.length();
        int difference = lenL - lenS;
        double[] possibleDifferences = new double[difference];
        for (int i = 0; i < difference; i++){
            String part = longer.substring(i, i+lenS);
            possibleDifferences[i] = compareSameLen(part, shorter);
        }
        double max = 0;
        for (int i = 0; i < difference; i++){
            if (possibleDifferences[i] > max){
                max = possibleDifferences[i];
            }
        }
        double ratio = (double) lenL /lenS;
        return max*ratio;
    }

    public static String[] fileReader (){
        File myObj = new File("words.txt");
        String[] words = new String[10000];
        try (Scanner myReader = new Scanner(myObj)) {
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                words[i] = data;
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return words;
    }
}
