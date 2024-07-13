package day5.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day5part2 {

    private static Map<Integer, List<String>> almanacMap = new HashMap<>();
    private static List<Long> seeds = new ArrayList<>();
    private static long minLoc = Long.MAX_VALUE;

    public static void main(String[] args) {
        String filePath = "day5\\data\\input-part1.txt";
        openFile(filePath);
        reverseProcess();
        //process();
    }

    private static void reverseProcess() {
        // TBD yet to implement this fully
        Long minLoc = 1;

        while (true) {
            if (hasMatchingSeedsInInput(minLoc++));
                break;
        }
        
        System.out.println(minLoc);
    }

    


    private static boolean hasMatchingSeedsInInput(Long loc) {
        // TBD yet to implement this fully
        return true;
    }

    private static void process() {
        List<Long> locations = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i = i+2) {
            long seed = seeds.get(i);
            long range = seeds.get(i+1);
            int step = 1;
            System.out.println("i val: " + i);
            while (range > 0) {
                long location = processStep(step, seed++) ;
                System.out.println(range);
                //locations.add(location);
                if (location < minLoc) {
                    minLoc = location;
                }
                range--;
            }
        }

        System.out.println(minLoc);
    }

    private static long processStep(int step, long seed) {
        List<String> rows = almanacMap.get(step);
        long mDest = seed;

        for (String row : rows) {
            String[] splits = row.split(" ");
            Long dest = Long.parseLong(splits[0]);
            Long source = Long.parseLong(splits[1]);
            Long range = Long.parseLong(splits[2]);

            if (seed >= source && seed <= source + range) {
                mDest = dest + (seed - source);
                break;
            }
        }

        if (step == 7) {
            return mDest;
        } else {
            return processStep(++step, mDest);
        }    
    }

    private static void openFile(String filePath) {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            myReader.useDelimiter(":|\\n|\\r");
            int lineNumber = 1;
            myReader.next();
            int mapCount = 0;
            while (myReader.hasNext()) {
                String seedString = myReader.next();
                if (!seedString.isEmpty()) {
                    if (lineNumber == 1) {
                        StringTokenizer st = new StringTokenizer(seedString, " ");
                        while (st.hasMoreTokens()) {
                            seeds.add(Long.parseLong(st.nextToken().trim()));
                            
                        }
                    } else {
                        if (seedString.contains("map")) {
                            mapCount++;
                            continue;
                        } else {
                            List<String> rows = new ArrayList<>();
                            if (almanacMap.containsKey(mapCount)) {
                                rows = almanacMap.get(mapCount);
                            }
                            rows.add(seedString);
                            almanacMap.put(mapCount, rows);   
                        }
                    }
                }
                lineNumber++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}