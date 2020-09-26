package com.hackerearth.connectfourgameapi.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
@Data
public class Board {

    // dimensions for our board
    private static final int WIDTH=7, HEIGHT=6;

    @Id
    @Column(name = "player_id")
    private String playerId;

    @Column(name = "last_col")
    private int lastCol = -1;

    @Column(name = "last_top")
    private int lastTop = -1;

    @Column(name = "valid_move_count")
    private int validMoveCount = 0;

    @Column(name = "grid")
    private String grid = initializeGrid();

    //yellow(odd) goes first Red (even) goes second
    @Column(name = "chance_of")
    private int chanceOf=1;

    public Board() {
    }

    public Board(int lastCol, int lastTop, int validMoveCount, String grid, int chanceOf) {
        this.lastCol = lastCol;
        this.lastTop = lastTop;
        this.validMoveCount = validMoveCount;
        this.grid = grid;
        this.chanceOf = chanceOf;
    }

    private static String initializeGrid() {
        char[][] grid = new char[HEIGHT][WIDTH];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = '.';
            }
        }
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
