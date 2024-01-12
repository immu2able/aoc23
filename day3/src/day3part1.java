
package day3.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class day3part1 {

    private static char[][] chars = new char[140][140];
    //private static char[][] chars = new char[10][10];
    public static void main(String[] args) {

        try {
            File myObj = new File("day3\\data\\input");
            Scanner myReader = new Scanner(myObj);

            int lineNum = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                parseLine(data, lineNum++);
            }
            myReader.close();

            System.out.println("ans: " + process());

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void parseLine(String line, int lineNum) {
        chars[lineNum] = line.toCharArray();
    }

    private static long process() {
        long accumulator = 0l;
        for (int i = 0; i < chars.length; i++) {
            StringBuilder sb = new StringBuilder();
            String symbolsString = "*/@&=#-+$%";

            boolean hasSymbolNeighbour = false;
            for (int j = 0; j < chars[i].length; j++) {
                char c = chars[i][j];
                if (Character.isDigit(c)) {
                    sb.append(c);
                    if (j != 0) {
                        char west = chars[i][j - 1];
                        if (symbolsString.contains("" + west)) {
                            hasSymbolNeighbour = true;
                            continue;
                        }
                    }
                    if (j != chars[i].length - 1) {
                        char east = chars[i][j + 1];
                        if (symbolsString.contains("" + east)) {
                            hasSymbolNeighbour = true;
                            continue;
                        }
                    }
                    if (i != chars.length - 1) {
                        char south = chars[i + 1][j];
                        if (symbolsString.contains("" + south)) {
                            hasSymbolNeighbour = true;
                            continue;
                        }
                    }
                    if (i != 0) {
                        char north = chars[i - 1][j];
                        if (symbolsString.contains("" + north)) {
                            hasSymbolNeighbour = true;
                            continue;
                        }
                    }
                    if ((i != 0) && (j != 0)) {
                        char northwest = chars[i - 1][j - 1];
                        if (symbolsString.contains("" + northwest)) {
                            hasSymbolNeighbour = true;
                            continue;
                        }
                    }
                    if ((i != 0) && (j != chars[i].length - 1)) {
                        char northeast = chars[i - 1][j + 1];
                        if (symbolsString.contains("" + northeast)) {
                            hasSymbolNeighbour = true;
                            continue;
                        }
                    }
                    if ((i != chars.length - 1) && (j != 0)) {
                        char southwest = chars[i + 1][j - 1];
                        if (symbolsString.contains("" + southwest)) {
                            hasSymbolNeighbour = true;
                            continue;
                        }
                    }
                    if ((i != chars.length - 1) && (j != chars[i].length - 1)) {
                        char southeast = chars[i + 1][j + 1];
                        if (symbolsString.contains("" + southeast)) {
                            hasSymbolNeighbour = true;
                            continue;
                        }
                    }
                } else {
                    if (hasSymbolNeighbour) {
                        accumulator += Integer.parseInt(sb.toString());
                        hasSymbolNeighbour = false;
                    }
                    sb = new StringBuilder();
                }
            }
            if (hasSymbolNeighbour) {
                accumulator += Integer.parseInt(sb.toString());
                hasSymbolNeighbour = false;
            }
            sb = new StringBuilder();
        }
        return accumulator;
    }
}