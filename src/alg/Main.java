package alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static int[][] junctions;
    private static int yDimension;
    private static int XDimension;
    private static int minimalDistance;
    private static int numberOfStations;
    private static int currentMinimalValue;
    private static int lowestCostCrossroad;

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        yDimension = Integer.parseInt(tokenizer.nextToken());
        XDimension = Integer.parseInt(tokenizer.nextToken());
        minimalDistance = Integer.parseInt(tokenizer.nextToken());
        numberOfStations = Integer.parseInt(tokenizer.nextToken());
        junctions = new int[XDimension][yDimension];
        boolean[][] takenCrossroadsBools = new boolean[XDimension][yDimension];
        lowestCostCrossroad = Integer.MAX_VALUE;
        for (int y = 0; y < yDimension; y++) {
            tokenizer = new StringTokenizer(reader.readLine());

            for (int x = 0; x < XDimension; x++) {
                junctions[x][y] = Integer.parseInt(tokenizer.nextToken());
                if (lowestCostCrossroad > junctions[x][y]) {
                    lowestCostCrossroad = junctions[x][y];
                }
            }
        }
        currentMinimalValue = Integer.MAX_VALUE;
        //Star of calculation
        for (int x = 0; x < XDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                calculateCrossroad(takenCrossroadsBools, x, y, 1, 0);

            }

        }
        System.out.println(currentMinimalValue);

    }

    public static void calculateCrossroad(boolean[][] takenCrossroads, int xPos, int yPos, int level, int currentValue) {
        currentValue += junctions[xPos][yPos];
        if (currentValue > currentMinimalValue) {
            return;
        }
        if (level == numberOfStations) {
            currentMinimalValue = currentValue;
            System.out.println(currentMinimalValue);
            return;
        } else if (currentValue + (numberOfStations - level) * lowestCostCrossroad > currentMinimalValue) {
            return;
        }

        boolean[][] nextLevelCrossroads = setOccupied(takenCrossroads, xPos, yPos);
        for (int x = 0; x < XDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                if (!nextLevelCrossroads[x][y]) {
                    calculateCrossroad(nextLevelCrossroads, x, y, level + 1, currentValue);
                }
            }
        }

    }

    public static boolean[][] setOccupied(boolean[][] takenCrossroads, int xPos, int yPos) {
        for (int x = 0; x < XDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                if (takenCrossroads[x][y]) {
                    continue;
                }
                if (x == xPos) {
                    takenCrossroads[x][y] = true;
                } else if (y == yPos) {
                    takenCrossroads[x][y] = true;
                } else if (Math.abs(x - xPos) + Math.abs(y - yPos) < minimalDistance) {
                    takenCrossroads[x][y] = true;
                }
            }
        }
        return takenCrossroads;
    }
}
