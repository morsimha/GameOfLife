/**
 * Maman 11, question 1.
 * Mor Simha, 206029993.
 */

package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class GameOfLifeController {
    @FXML
    private Canvas cnvs;

    private final int MAT_SIZE = 100, SIDE = MAT_SIZE / 10, CELL_SIZE = 50;
    private boolean firstRound = true;
    private Rectangle[][] lastGenMat, nextGenMat;
    private GraphicsContext gc;

    public void initialize() {
        gc = cnvs.getGraphicsContext2D();
        gc.setLineWidth(6);
        lastGenMat = new Rectangle[MAT_SIZE][MAT_SIZE];
        nextGenMat = new Rectangle[MAT_SIZE][MAT_SIZE];
        drawMat();
        firstRound = false;
    }
    @FXML
    private void btnPressed() {
        drawMat();
        for (int x = 0; x < SIDE; x++)
            System.arraycopy(nextGenMat[x], 0, lastGenMat[x], 0, SIDE);
    }

    private void drawMat() {
        int lifeCounter;
        Paint newColor;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                Rectangle rect = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (firstRound) { //randomizing the first matrix
                    lastGenMat[i][j] = rect;
                    newColor = randomizeGame();
                    lastGenMat[i][j].setFill(newColor);
                } else { //Counting lives and calculating the next generation's matrix.
                    nextGenMat[i][j] = rect;
                    lifeCounter = countLives(i, j);
                    newColor = CalculateNextGen(lifeCounter, i, j);
                    nextGenMat[i][j].setFill(newColor);
                }
                gc.setFill(newColor); //choosing the right color for the cell
                gc.fillRect(lastGenMat[i][j].getX(), lastGenMat[i][j].getY(), lastGenMat[i][j].getWidth(), lastGenMat[i][j].getHeight()); //inside color
                gc.strokeRect(lastGenMat[i][j].getX(), lastGenMat[i][j].getY(), lastGenMat[i][j].getWidth(), lastGenMat[i][j].getHeight()); //outline color

            }
        }
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

    // checks cell's sibling color, to count lives around it.
    private int countLives(int x, int y) {
        int lifeCounter = 0, newX, newY;
        // checks all siblings options, beside the out of range ones.
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                newX = x + i; //new x coordinate to check
                newY = y + j;
                if (((newX >= 0 && newY >= 0) && (newX < SIDE && newY < SIDE)) && !(newX == x && newY == y)) // check positive and not the middle coordinate
                    if ((lastGenMat[newX][newY].getFill() == Color.DIMGRAY)) // avoid checking the middle one
                        lifeCounter++;
            }
        return lifeCounter;
    }

    private Paint CalculateNextGen(int lifeCounter, int x, int y) {
        Paint currColor = lastGenMat[x][y].getFill();
        //3 siblings means a birth or existence for all cells,
        //2 siblings for a living cell means existence.
        if (lifeCounter == 3 || (lifeCounter == 2 && currColor == Color.DIMGRAY))
            return Color.DIMGRAY;
        else  //every other option means death
            return Color.WHITE;
    }
}
