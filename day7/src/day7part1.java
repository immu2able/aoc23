

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

enum HAND {
    A("A", 14), K("K", 13), Q("Q", 12), J("J", 11), T("T", 10), NINE("9", 9), EIGHT("8", 8), SEVEN("7", 7), SIX("6", 6), FIVE("5", 5), FOUR("4", 4), THREE("3", 3), TWO("2", 2);

    private String m_name;
    private int m_value;

    HAND(String name, int value) {
        m_name = name;
        m_value = value;
    }

    public int getValue() {
        return this.m_value;
    }
}

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
        String testInputfilePath = "/home/user/aoc23/day7/data/test-input.txt";
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
        


        sortByValue((HashMap<String, Long>) fiveOfAKind);
        sortByValue((HashMap<String, Long>) fourOfAKind);
        sortByValue((HashMap<String, Long>) threeOfAKind);
        sortByValue((HashMap<String, Long>) fullHouse);
        sortByValue((HashMap<String, Long>) twoPair);
        sortByValue((HashMap<String, Long>) onePair);
        sortByValue((HashMap<String, Long>) highCard);

        System.out.println("Five Of A Kind: " + fiveOfAKind);
        System.out.println("Four Of A Kind: " + fourOfAKind);
        System.out.println("Three Of A Kind: " + threeOfAKind);
        System.out.println("Full House: " + fullHouse);
        System.out.println("Two Pair: " + twoPair);
        System.out.println("One Pair: " + onePair);
        System.out.println("High Card: " + highCard);
        
        fiveOfAKind = sort(fiveOfAKind);
        sort(fourOfAKind);
        sort(threeOfAKind);
        sort(fullHouse);
        sort(twoPair);
        sort(onePair);
        sort(highCard);

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

    private static HashMap<String, Long> sort(Map<String, Long> hands) {
        HashMap<String, Long> sorted = new LinkedHashMap<>();
        
        
        List<HAND> handList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : hands.entrySet()) {
            handList.add(HAND.valueOf(entry.getKey()));
        }

        
        Collections.sort(handList, new Comparator<HAND>() {
            @Override
            public int compare(HAND o1, HAND o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        System.out.println("handlist: " + handList);

        

        for (Map.Entry<String, Long> entry : hands.entrySet()) {
            sorted.put(entry.getKey(), entry.getValue());
        }
        System.out.println(sorted);

        return sorted;
    }

    private static Long computeTotalWinings() {
        int len = values.size();
        long result = 1l;
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
        boolean twoDiff = false;
        boolean pairFound = false;
        boolean categorized = false;

        for (int i = 0; i < freq.length; i++) {
            freq[i] = 0;
        }

        for (int i = 0; i < hand.length(); i++) {
            //System.out.println(hand.charAt(i) + " freq now " + freq[hand.charAt(i) - '2']);
            
            freq[hand.charAt(i) - '2']++;      
            //System.out.println(" freq after " + freq[hand.charAt(i) - '2']);

        }      

        // for(int i = 0; i < 41; i++) {
        //     System.out.println((char)(i + '2') + " " + "freq[" + i + "]: " + freq[i]);    
        // }

        for(int i = 0; i < 41; i++) {
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

                if (!categorized && i == 40) {
                    highCard.put(hand, evaluateHand(hand));
                    categorized = true;
                }
            } else if (i==40 && !categorized) {
                if (threeSame) {
                    threeOfAKind.put(hand, evaluateHand(hand));
                    categorized = true;
                    threeSame = false;
                }
                // Handle One Pair
                if (twoSame) {
                    onePair.put(hand, evaluateHand(hand));
                    categorized = true;
                    twoSame = false;
                }
            }
        }
    }

    public static HashMap<String, Long> sortByValue(HashMap<String, Long> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Long> > list =
               new LinkedList<Map.Entry<String, Long> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Long> >() {
            public int compare(Map.Entry<String, Long> o1, 
                               Map.Entry<String, Long> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        // put data from sorted list to hashmap 
        HashMap<String, Long> temp = new LinkedHashMap<String, Long>();
        for (Map.Entry<String, Long> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private static Long evaluateHand(String hand) {
            long score = 0;
            char[] chars = hand.toCharArray();
            for (char c : chars) {
                if (Character.isDigit(c)) {
                    int val = Integer.parseInt(c+"");
                    score += val;
                } else {
                    switch (c) {
                        case 'T':
                            score +=  10;
                            break;
                        case 'J':
                            score +=  11;
                            break;
                        case 'Q':
                            score +=  12;
                            break;
                        case 'K':
                            score +=  13;
                            break;
                        case 'A':
                            score +=  14;
                            break;
                        default:
                            break;
                    }

                }

        }
        return score;
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
                
                hands.put(hand, Long.parseLong(bid));
        
            }
            


            myReader.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}