

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day7part1 {

    private static Map<String, Long> hands = new HashMap<>();
    private static Map<String, Long> fiveOfAKind = new HashMap<>();
    private static Map<String, Long> fourOfAKind = new HashMap<>();
    private static Map<String, Long> fullHouse = new HashMap<>();
    private static Map<String, Long> threeOfAKind = new HashMap<>();
    private static Map<String, Long> twoPair = new HashMap<>();
    private static Map<String, Long> onePair = new HashMap<>();
    private static Map<String, Long> highCard = new HashMap<>();

    private static Long errorMargins = 1l;
    

    public static void main(String[] args) {
        String filePath = "day7\\data\\test-input.txt";
        openFile(filePath);
        process();
    }

    private static void process() {
        /*
         *  1. Categorize Hand to Hand Type:
         *              Five of a Kind, 
         *              Four of a Kind, 
         *              Full House, 
         *              Three of a Kind, 
         *              Two Pair, 
         *              One Pair, 
         *              High Card
         *  2. Evaluate Hands with in Hand Type
         *  2. Rank Hands
         *  3. Compute total winings
         *
         */


        for (String hand : hands.keySet()) {
            categorizeHand(hand);
        }
        
        System.out.println("Five Of A Kind: " + fiveOfAKind);
        System.out.println("Four Of A Kind: " + fourOfAKind);
        System.out.println("Three Of A Kind: " + threeOfAKind);
        System.out.println("Full House: " + fullHouse);
        System.out.println("Two Pair: " + twoPair);
        System.out.println("One Pair: " + onePair);
        System.out.println("High Card: " + highCard);
        
        Long tw= computeTotalWinings();
        System.out.println("Total Winings: " + tw);
         
    }

    private static Long computeTotalWinings() {
        return 0L;
    }

    private static void categorizeHand(String hand) {

        int[] freq = new int[41];
        boolean threeSame = false;
        boolean twoSame = false;
        boolean twoDiff = false;
        boolean pairFound = false;
        boolean categorized = false;

        for (int i = 0; i < hand.length(); i++)
            freq[hand.charAt(i) - '2']++;            

        for(int i = 0; i < 41; i++) {
            //System.out.println("freq[" + i + "]: " + freq[i]);    
            if (freq[i] == 5)   {
                fiveOfAKind.put(hand, evaluateHand(hand));
                categorized = true;
            } else if (freq[i] == 4)    {
                fourOfAKind.put(hand, evaluateHand(hand));
                categorized = true;
            } else if (freq[i] == 3)    {
                threeSame = true;
                // Handle 3ofAK and FullHouse here
                if (twoSame && !twoDiff) {
                    threeOfAKind.put(hand, evaluateHand(hand));
                    categorized = true;
                    threeSame = false;
                    twoDiff = false;
                    twoSame = false;
                } else if (!twoSame && twoDiff) {
                    fullHouse.put(hand, evaluateHand(hand));
                    categorized = true;
                    threeSame = false;
                    twoDiff = false;
                    twoSame = false;
                }
            } else if (freq[i] == 2) {
                // Handle 3ofAK
                twoSame = true;
                if (threeSame) {
                    threeOfAKind.put(hand, evaluateHand(hand));
                    categorized = true;
                    threeSame = false;
                    twoDiff = false;
                    twoSame = false;
                }

                // Handle Two Pair
                if (pairFound) {
                    twoPair.put(hand, evaluateHand(hand));
                    categorized = true;
                    pairFound = false;
                } else {
                    pairFound = true;
                }
            } else if (freq[i] == 1) {
                // Handle One Pair
                if (pairFound) {
                    onePair.put(hand, evaluateHand(hand));
                    categorized = true;
                    pairFound = false;
                }

                if (!categorized && i == 40) {
                    highCard.put(hand, evaluateHand(hand));
                    categorized = true;
                }
            }
        }

    }

    private static Long evaluateHand(String hand) {
        return 0L;

    }

    private static void openFile(String filePath) {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            myReader.useDelimiter(" |\\n|\\r");

            while (myReader.hasNext()) {
                String hand = myReader.next();
                //System.out.println("hand:: " + hand);
                String bid = myReader.next();
                //System.out.println("bid:: " + bid);
                if (myReader.hasNext())
                    myReader.next(); // goto next line

                hands.put(hand, Long.parseLong(bid));
        
            }
            


            myReader.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}