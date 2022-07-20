package logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class GameBoard {

    private Position[][] gameGrid;

    private int size;

    private GameBoard(int size) {
        this.size = size;
        gameGrid = new Position[size][size];
    }

    private Integer compute(int[][] grid) {
        return null;
    }

    public Map<Integer, Integer> calculateLine(int[] line) {
        Map<Integer,Integer> map = new HashMap<>();
        Arrays.stream(line).forEach(x -> map.put(x , map.computeIfAbsent(x, s -> 0) + 1));
        System.out.println(map);
        return map;
    }

    public Map<Integer, Map<Integer, Integer>> getCurrentPoints(int[][] grid) {
        Map<Integer, Map<Integer, Integer>> endResult = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            endResult.put(i+1, calculateLine(grid[i]));
        }
        int[][] reverseArray = swapMatrix(grid);

        for (int i = 0; i < reverseArray.length; i++) {
            endResult.put(-i-1, calculateLine(reverseArray[i]));
        }
        return endResult;
    }

    public int[][] swapMatrix(int[][] input) {
        int[][] swap = new int[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                swap[j][i] = input[i][j];
            }
        }
        return swap;
    }












    public Map<Integer, Integer> trash(int[][] grid) {
        Map<Integer, Integer> result = new HashMap<>();
        for (int j = 1; j <= grid.length; j++) {
           result.put(j, 0);
           result.put(-j, 0);
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(result.get(i+1) < grid[i][j]) {
                    result.put(i+1, grid[i][j]);
                }
                if(result.get(-j-1) < grid[i][j]) {
                    result.put(-j-1, grid[i][j]);
                }
            }
        }
        return result;
    }








}
