package encryptdecrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class Main {

    static void setUpCode()
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        HashMap<char, char> code = new HashMap<char, char>();
        // Traverse the string
        for (int i = 0; i < alphabet.length(); i++) {

            // Print current character
            code.put('a', 'z');
            System.out.print(str.charAt(i) + " ");
        }
    }

    public static void main(String[] args) {
    }
}
