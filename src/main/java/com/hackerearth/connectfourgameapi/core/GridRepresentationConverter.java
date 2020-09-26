package com.hackerearth.connectfourgameapi.core;

public class GridRepresentationConverter {
    private static final int WIDTH = 7, HEIGHT = 6;

    public static char[][] stringToTwoDimensionalCharArray(String str){
        char[][] grid = new char[HEIGHT][WIDTH];
        for(int row = 0; row < grid.length; row++){
            grid[row]=str.split("\n")[row].toCharArray();
        }
        return grid;
    }

    public static String TwoDimensionalCharArrayToString(char[][] grid){
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                sb.append(grid[row][col]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
