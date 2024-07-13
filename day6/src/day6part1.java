package day6.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day6part1 {

    private static Map<Long, Long> raceTimesMap = new HashMap<>();
    private static Long errorMargins = 1l;
    

    public static void main(String[] args) {
        String filePath = "day6\\data\\input-part2.txt";
        openFile(filePath);
        process();
    }

    private static void process() {
        /*
         * 1. For each race, compute all possible distances
         * 2. Identify and count the number of distances that are larger than the recorded distances 
         * 3. Multiply these and provide the answer
         * 
         * 
         * 
         * 
         */

         // Bifurcate available time into charging time and running time
         // The time spent on charging == speed of the car
         // The more time spent on charging, we have less running time
         // 

        for (long time : raceTimesMap.keySet()) {
            

            Long errorMargin = 0l;
            for (long i = 0; i <= time; i++) {
                //i second charged and time-i seconds run
                Long chargingTimeIsAlsoSpeed = i;
                Long runningTime = time - i;
                Long computedDistance = (runningTime * chargingTimeIsAlsoSpeed);
                if (computedDistance > raceTimesMap.get(time)) {
                    errorMargin ++;
                }
            }
            errorMargins *= errorMargin;
        }

        System.err.println(errorMargins);
         
    }

    private static void openFile(String filePath) {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            myReader.useDelimiter(":|\\n|\\r");


            myReader.next(); // ignore title
            String times = myReader.next();
            myReader.next(); // ignore next line
            myReader.next(); // ignore title
            String distances = myReader.next();
            Long time, distance;
            StringTokenizer st1 = new StringTokenizer(times, " ");
            StringTokenizer st2 = new StringTokenizer(distances, " ");
            while (st1.hasMoreTokens() && st2.hasMoreTokens()) {
                time = Long.parseLong(st1.nextToken().trim());
                distance = Long.parseLong(st2.nextToken().trim());
                raceTimesMap.put(time, distance);
            }
            myReader.close();
            System.out.println(raceTimesMap);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}