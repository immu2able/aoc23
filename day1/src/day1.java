package day1.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class day1 {

    private static Map<String, String> digitsMap = new HashMap<>(); 
    public static void main(String[] args) {
        try {
            File myObj = new File("day1\\data\\input");
            Scanner myReader = new Scanner(myObj);
            int counter = 0;
            
            digitsMap.put("one", "1");
            digitsMap.put("two", "2");
            digitsMap.put("three", "3");
            digitsMap.put("four", "4");
            digitsMap.put("five", "5");
            digitsMap.put("six", "6");
            digitsMap.put("seven", "7");
            digitsMap.put("eight", "8");
            digitsMap.put("nine", "9");

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                int val = getDigits(data);
                System.out.print(data);
                System.out.println(" --->> 6val: " + val);
                counter += val;
            }
            System.out.println("final val: " + counter);
        myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } 
    }


    private static String replaceDigits(String data) {
        
        for (String str : digitsMap.keySet())
        if (data.indexOf(str) >= 0) {
            data.replace(str, digitsMap.get(str));
            break;
        }

        if (data.lastIndexOf(str) >= 0) {
            data.replace(str, digitsMap.get(str));
            break;
        }

        return "";
    }

    private static int getDigits(String data) {
        
        char[] chars = data.toCharArray();

        char first = '\0';
        char second = '\0';

        for (int i = 0; i < data.length(); i++) {
            if (Character.isDigit(chars[i])) {
                first = chars[i];
                break;
            }
        }

        for (int j = data.length() - 1; j >= 0; j-- ) {
            if (Character.isDigit(chars[j])) {
                second = chars[j];
                break;
            }
        }
        String str = "" + first + second;
        return Integer.parseInt(str);
    }
}
