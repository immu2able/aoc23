import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;


public class day2part2 {

    static int blueMax = 14;
    static int redMax = 12;
    static int greenMax = 13;
    
    public static void main(String[] args) {
        try {
            File myObj = new File("day2\\data\\input");
            Scanner myReader = new Scanner(myObj);

            int powersAdded = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();                
                // System.out.println(data);
                powersAdded += parseLine(data);
                
            }
            myReader.close();
            System.out.println("powersAdded: " + powersAdded);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static int parseLine(String line) {
        
        int idx = line.indexOf(":", 0);
        String game = line.substring(0, idx);
        int gameId = Integer.parseInt(game.split(" ")[1].trim());
        String record = line.substring(idx);

        StringTokenizer  st = new StringTokenizer (record, ":;");
        Map<String, List<Integer>> cubes = new HashMap<>();

        while (st.hasMoreTokens()) {
            String set = st.nextToken();
            //System.out.println("set: " + set);

            StringTokenizer st2 = new StringTokenizer(set, ",");
            
            while (st2.hasMoreTokens()) {
                String data = st2.nextToken();
                //System.out.println(data);

                StringTokenizer st3 = new StringTokenizer(data, " ");
                String v = st3.nextToken().trim();
                int val =  Integer.parseInt(v);
                String color = st3.nextToken().trim();
                

                List<Integer> cubeCounts = new ArrayList<>();

                if (cubes.containsKey(color)) {
                     cubeCounts = cubes.get(color);
                }
                cubeCounts.add(val);
                cubes.put(color, cubeCounts);
            }
        }

        // After parsing each line, check for the largest cubes for each game to determine the powers
        int pow = 1;

        for (String color : cubes.keySet()) {
            List<Integer> cc = cubes.get(color);
            pow *= Collections.max(cc);
        }



        return pow;
    }
}