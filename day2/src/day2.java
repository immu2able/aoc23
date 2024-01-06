import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;


public class day2 {

    static int blueMax = 14;
    static int redMax = 12;
    static int greenMax = 13;
    
    public static void main(String[] args) {
        try {
            File myObj = new File("day2\\data\\input");
            Scanner myReader = new Scanner(myObj);

            int possibleGames = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();                
                // System.out.println(data);
                possibleGames += parseLine(data);
                
            }
            myReader.close();
            System.out.println("possible games: " + possibleGames);
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

        while (st.hasMoreTokens()) {
            String set = st.nextToken();
            //System.out.println("set: " + set);

            StringTokenizer st2 = new StringTokenizer(set, ",");
            //Map<String, Integer> cubes = new HashMap<>();
            while (st2.hasMoreTokens()) {
                String data = st2.nextToken();
                //System.out.println(data);

                StringTokenizer st3 = new StringTokenizer(data, " ");
                String v = st3.nextToken().trim();
                int val =  Integer.parseInt(v);
                String color = st3.nextToken().trim();
                

                // int count = 0;
                // if (cubes.containsKey(color)) {
                //     count = cubes.get(color);
                // }
                // count += val ;
                // cubes.put(color, count);
                if ((color.equals("blue") && val > blueMax) || 
                    (color.equals("red") && val > redMax) ||
                    (color.equals("green") && val > greenMax)) {
                    return 0;
                }
            }

        }

        return gameId;
    }
}