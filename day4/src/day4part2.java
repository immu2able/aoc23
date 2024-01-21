package day4.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day4part2 {

    private static Map<Integer, Integer> cardsCountsMap = new HashMap<>();
    private static Map<Integer, Integer> cardsWinsMap = new HashMap<>();
    public static void main(String[] args) {

        try {
            File myObj = new File("day4\\data\\input-part2");
            Scanner myReader = new Scanner(myObj);
            double points = 0;
            int lineNum = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                parseLine(data, lineNum++);
            }
            System.out.println("Card Wins map: " + cardsWinsMap);
            //System.out.println("card counts map: " + cardsCountsMap);
            int totals = 0;

            
            for (int cardNumber : cardsWinsMap.keySet()) {
                int wins = cardsWinsMap.get(cardNumber);

                int times = cardsCountsMap.get(cardNumber);

                while (times > 0) {
                    int tcn = cardNumber;

                    while (tcn < cardNumber+wins) {
                        tcn++;
                        processCardsCount(tcn);

                    }
                    times--;
                }
                //System.out.println("Cards Counts Map: " + cardsCountsMap);
            }

            int ans = 0;
            for (int cn : cardsCountsMap.keySet()) {
                ans += cardsCountsMap.get(cn);
            }
            System.out.println("Cards totals: " + ans);
            myReader.close();

            //System.out.println("points: " + points);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void parseLine(String line, int lineNum) {
        //double res = 0d;
        StringTokenizer s = new StringTokenizer(line, ":");
        s.nextToken();
        String numStr = s.nextToken();

        processCardsCount(lineNum);
        StringTokenizer st = new StringTokenizer(numStr, "|");
        if (st.hasMoreTokens()) {
                String winNums  = st.nextToken();
                String elfsNums = st.nextToken();
                Set<Integer> wins = processNums(winNums);
                Set<Integer> elfs = processNums(elfsNums);
                elfs.retainAll(wins);

                //System.out.println("Matching cards: " + elfs);
                cardsWinsMap.put(lineNum, elfs.size());
        }
        
    }

    private static void processCardsCount(int lineNum) {
        int cc = 1;
        if (cardsCountsMap.containsKey(lineNum)) {
            cc = cardsCountsMap.get(lineNum);
            cc++;
        }
        cardsCountsMap.put(lineNum, cc);
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
