public class IntoNumbers {
    public static void main(String[] args){
        String word = "helloworld";

        int[] vector = new int[word.length()];
        for (int i = 0; i < vector.length; i++){
            char c = word.charAt(i);
            vector[i] = c - 'a' + 1;
            System.out.println(c);
            System.out.println(vector[i]);
        }

    }
}
