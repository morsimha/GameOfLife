/**
 * GameOfLifeLogic class is responsible for the game logic calculations and the matrix's building.
 */

package com.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class GameOfLifeLogic {

    private final int MAT_SIZE = 100, SIDE = (MAT_SIZE / 10), CELL_SIZE = 50;
    private Rectangle[][] historyMat, GenerationMat; //history will always hold last generation's matrix.

    public GameOfLifeLogic() {
        this.historyMat = new Rectangle[MAT_SIZE][MAT_SIZE];
        this.GenerationMat = new Rectangle[MAT_SIZE][MAT_SIZE];
    }

    public Rectangle[][] getMat() {
        return GenerationMat;
    }

    //Randomizes the first generation's matrix.
    public void createFirstGeneration() {
        Paint newColor;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                Rectangle rect = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                GenerationMat[i][j] = rect;
                newColor = randomizeGame();
                GenerationMat[i][j].setFill(newColor);
            }
        }
        copyMat();
    }

    //Updates the next generation's matrix by the game rules.
    public void updateGeneration() {
        int lifeCounter;
        Paint newColor;
        for (int i = 0; i < SIDE; i++) {  //Counting lives and calculating the next generation's matrix.
            for (int j = 0; j < SIDE; j++) {
                Rectangle rect = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                GenerationMat[i][j] = rect;
                lifeCounter = countLives(i, j);
                newColor = CalculateNextGen(lifeCounter, i, j);
                GenerationMat[i][j].setFill(newColor);
            }
        }
        copyMat();
    }
    //Copies the current matrix to the history matrix.
    private void copyMat() {
        for (int i = 0; i < SIDE; i++)
            for (int j = 0; j < SIDE; j++)
                historyMat[i][j] = GenerationMat[i][j];
    }

    //Randomizes a number (0,1) and sets the color cell color by it.
    // WHITE is dead and DIMGRAY is alive
    private Paint randomizeGame() {
        Random r = new Random();
        if (r.nextInt(2) == 0) //Alive
            return Color.DIMGRAY;
        else //Dead
            return Color.WHITE;
    }

    // Checks cell's sibling color, to count lives around it.
    private int countLives(int x, int y) {
        int lifeCounter = 0, newX, newY;
        // checks all siblings options, beside the out of range ones.
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                newX = x + i; //new x coordinate to check
                newY = y + j;
                if (((newX >= 0 && newY >= 0) && (newX < SIDE && newY < SIDE)) && !(newX == x && newY == y)) // check positive and not the middle coordinate
                    if ((historyMat[newX][newY].getFill() == Color.DIMGRAY)) // avoid checking the middle one
                        lifeCounter++;
            }
        return lifeCounter;
    }

    //Returns the correct cell color, according to its siblings.
    private Paint CalculateNextGen(int lifeCounter, int x, int y) {
        Paint currColor = historyMat[x][y].getFill();
        //3 siblings means a birth or existence for all cells,
        //2 siblings for a living cell means existence.
        if (lifeCounter == 3 || (lifeCounter == 2 && currColor == Color.DIMGRAY))
            return Color.DIMGRAY;
        else  //every other option means death
            return Color.WHITE;
    }

}
