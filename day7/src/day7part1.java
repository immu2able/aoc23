

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// enum HAND {
//     A("A", 14), K("K", 13), Q("Q", 12), J("J", 11), T("T", 10), NINE("9", 9), EIGHT("8", 8), SEVEN("7", 7), SIX("6", 6), FIVE("5", 5), FOUR("4", 4), THREE("3", 3), TWO("2", 2);

//     private String m_name;
//     private int m_value;

//     HAND(String name, int value) {
//         m_name = name;
//         m_value = value;
//     }

//     public int getValue() {
//         return this.m_value;
//     }
// }

public class day7part1 {

    private static Map<String, Long> hands = new HashMap<>();
    private static Map<String, Long> fiveOfAKind = new HashMap<>();
    private static Map<String, Long> fourOfAKind = new HashMap<>();
    private static Map<String, Long> fullHouse = new HashMap<>();
    private static Map<String, Long> threeOfAKind = new HashMap<>();
    private static Map<String, Long> twoPair = new HashMap<>();
    private static Map<String, Long> onePair = new HashMap<>();
    private static Map<String, Long> highCard = new HashMap<>();
    private static Map<String, Long> values = new LinkedHashMap<>();

    private static Long errorMargins = 1l;
    

    public static void main(String[] args) {
        String inputfilePath = "/home/user/aoc23/day7/data/input.txt";
        String testInputfilePath = "/home/user/aoc23/day7/data/test2.txt";
        openFile(testInputfilePath);
        process();
    }

    private static void process() {
        /*
         *  1. Categorize the given Hand to one of the following Hand Type:
         *              a. Five of a Kind, 
         *              b. Four of a Kind, 
         *              c. Full House, 
         *              d. Three of a Kind, 
         *              e. Two Pair, 
         *              f. One Pair, 
         *              g. High Card
         *  2. Evaluate Hands with in Hand Type
         *  2. Rank Hands
         *  3. Compute total winings
         *
         */


        for (String hand : hands.keySet()) {
            categorizeHand(hand);
        }
        


        fiveOfAKind = sortByValue((HashMap<String, Long>) fiveOfAKind);
        fourOfAKind = sortByValue((HashMap<String, Long>) fourOfAKind);
        threeOfAKind = sortByValue((HashMap<String, Long>) threeOfAKind);
        fullHouse = sortByValue((HashMap<String, Long>) fullHouse);
        twoPair = sortByValue((HashMap<String, Long>) twoPair);
        onePair = sortByValue((HashMap<String, Long>) onePair);
        highCard = sortByValue((HashMap<String, Long>) highCard);

        System.out.println("Five Of A Kind: " + fiveOfAKind);
        System.out.println("Four Of A Kind: " + fourOfAKind);
        System.out.println("Three Of A Kind: " + threeOfAKind);
        System.out.println("Full House: " + fullHouse);
        System.out.println("Two Pair: " + twoPair);
        System.out.println("One Pair: " + onePair);
        System.out.println("High Card: " + highCard);


        values.putAll(fiveOfAKind);
        values.putAll(fourOfAKind);
        values.putAll(threeOfAKind);
        values.putAll(fullHouse);
        values.putAll(twoPair);
        values.putAll(onePair);
        values.putAll(highCard);
        

        Long tw= computeTotalWinings();
        System.out.println("Total Winings: " + tw);
         
    }

    private static Long computeTotalWinings() {
        int len = values.size();
        long result = 0l;
        for(String hand : values.keySet()) {
            long bid = hands.get(hand);
            System.out.println("Hand: " + hand + " Bid: " + bid + " Len: " + len);
            result = result + (bid * len);
            len--;
        }
        return result;
    }

    private static void categorizeHand(String hand) {

        int[] freq = new int[41];
        boolean threeSame = false;
        boolean twoSame = false;
        boolean pairFound = false;
        boolean categorized = false;

        for (int i = 0; i < freq.length; i++) {
            freq[i] = 0;
        }

        for (int i = 0; i < hand.length(); i++) {            
            freq[hand.charAt(i) - '2']++;      
        }      


        for(int i = 0; i < 41; i++) {
            if (freq[i] == 5)   {
                fiveOfAKind.put(hand, 0l);
                categorized = true;
            } else if (freq[i] == 4)    {
                fourOfAKind.put(hand, 0l);
                categorized = true;
            } else if (freq[i] == 3)    {
                
                threeSame = true;

            } else if (freq[i] == 2) {
                twoSame = true;
                // Handle Two Pair
                if (pairFound) {
                    twoPair.put(hand, 0l);
                    categorized = true;
                    pairFound = false;
                } else {
                    pairFound = true;
                }
            } else if (i==40 && !categorized) {
                if (threeSame && twoSame) {
                    fullHouse.put(hand, 0l);
                    categorized = true;
                    threeSame = false;
                    twoSame = false;
                }
                else if (threeSame) {
                    threeOfAKind.put(hand, 0l);
                    categorized = true;
                    threeSame = false;
                }
                // Handle One Pair
                else if (twoSame) {
                    onePair.put(hand, 0l);
                    categorized = true;
                    twoSame = false;
                } else {   
                    highCard.put(hand, 0l);
                    categorized = true;
                }
            }
        }
    }


    private static int compareHands(String hand1, String hand2) {
        String cardOrder = "AKQJT98765432"; // Define your desired card order
        for (int i = 0; i < hand1.length(); i++) {
            int index1 = cardOrder.indexOf(hand1.charAt(i));
            int index2 = cardOrder.indexOf(hand2.charAt(i));
            if (index1 != index2) {
                return Integer.compare(index1, index2);
            }
        }
        return 0; // Hands are equal
    }

    private static HashMap<String, Long> sortByValue(HashMap<String, Long> hm)
    {

        List<String> handsList = new ArrayList<String>(hm.keySet());
        
        Collections.sort(handsList, day7part1::compareHands);
         
        // put data from sorted list to hashmap 
        HashMap<String, Long> temp = new LinkedHashMap<String, Long>();
        for (String key : handsList) {
            temp.put(key, hm.get(key));
        }
        return temp;
    }

    // private static Long evaluateHand(String hand) {
    //     long score = 0;
    //     char[] chars = hand.toCharArray();
    //     int len = 5;
    //     for (char c : chars) {
    //         if (Character.isDigit(c)) {
    //             int val = Integer.parseInt(c+"");
    //             score += (val * len );
    //         } else {
    //             switch (c) {
    //                 case 'T':
    //                     score +=  (10*len);
    //                     break;
    //                 case 'J':
    //                     score +=  (100*len);
    //                     break;
    //                 case 'Q':
    //                     score +=  (1000*len);
    //                     break;
    //                 case 'K':
    //                     score +=  (10000*len);
    //                     break;
    //                 case 'A':
    //                     score +=  (100000*len);
    //                     break;
    //                 default:
    //                     break;
    //             }

    //         }
    //         len--;
    //     }
    //     return score;
    // }

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
                
                hands.put(hand, Long.parseLong(bid));
        
            }
            


            myReader.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}