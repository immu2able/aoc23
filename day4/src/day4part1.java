package day4.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.*;

public class day4part1 {
    public static void main(String[] args) {

        try {
            File myObj = new File("day4\\data\\input");
            Scanner myReader = new Scanner(myObj);
            double points = 0;
            int lineNum = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                points += parseLine(data, lineNum++);
            }
            myReader.close();

            System.out.println("points: " + points);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static double parseLine(String line, int lineNum) {
        double res = 0d;
        StringTokenizer s = new StringTokenizer(line, ":");
        s.nextToken();
        String numStr = s.nextToken();

        StringTokenizer st = new StringTokenizer(numStr, "|");
        if (st.hasMoreTokens()) {
                String winNums  = st.nextToken();
                String elfsNums = st.nextToken();
                Set<Integer> wins = processNums(winNums);
                Set<Integer> elfs = processNums(elfsNums);
                elfs.retainAll(wins);
                if (!elfs.isEmpty()) {
                    double scr = 1;
                    for (int i = 1; i < elfs.size(); i++) {
                        scr *= 2;
                    }
                    res = scr;
                }
        }
        return res;
    }

    private static Set<Integer> processNums(String nums) {
        Set<Integer> set = new HashSet<>();
        StringTokenizer st = new StringTokenizer(nums, " ");
        while (st.hasMoreTokens()) {
            set.add(Integer.parseInt(st.nextToken()));
        }
        return set;
    }
}
