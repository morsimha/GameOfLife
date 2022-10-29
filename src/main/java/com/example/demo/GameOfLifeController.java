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
        drawMat(lastGenMat);
        firstRound = false;
    }
    @FXML
    private void btnPressed() {
        drawMat(nextGenMat);
        for (int x = 0; x < SIDE; x++)
            System.arraycopy(nextGenMat[x], 0, lastGenMat[x], 0, SIDE);
    }
    //Randomizes or calculates every generation's matrix.
    private void drawMat(Rectangle[][] matrix) {
        int lifeCounter;
        Paint newColor;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                Rectangle rect = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                matrix[i][j] = rect;
                if (firstRound) //randomizing the first matrix
                    newColor = randomizeGame();
                else { //Counting lives and calculating the next generation's matrix.
                    lifeCounter = countLives(i, j);
                    newColor = CalculateNextGen(lifeCounter, i, j);
                }
                matrix[i][j].setFill(newColor);
                fillRects(matrix,newColor,i,j);
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
    //returns the correct cell color, according to its siblings.
    private Paint CalculateNextGen(int lifeCounter, int x, int y) {
        Paint currColor = lastGenMat[x][y].getFill();
        //3 siblings means a birth or existence for all cells,
        //2 siblings for a living cell means existence.
        if (lifeCounter == 3 || (lifeCounter == 2 && currColor == Color.DIMGRAY))
            return Color.DIMGRAY;
        else  //every other option means death
            return Color.WHITE;
    }

    //filling the rectangles according to the right color.
    private void fillRects(Rectangle[][] matrix, Paint color,int i, int j){
        gc.setFill(color); //choosing the right color for the cell
        gc.fillRect(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWidth(), matrix[i][j].getHeight()); //inside color
        gc.strokeRect(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWidth(), matrix[i][j].getHeight()); //outline color
    }



    int counter = -1;

    private Paint randomizeGame2() {
        //   Random r = new Random();
        counter++;
        if (counter % 2 == 0)
            return Color.DIMGRAY;
        return Color.WHITE;
    }

    private int neighborCheck(int i, int j) {
        int neighbor_count = 0;
        for (int xx = -1; xx <= 1; xx++) {
            for (int yy = -1; yy <= 1; yy++) {
                if ((xx == 0 && yy == 0) || (xx + i < 0 && yy + j < 0 || !isOnMat(xx + i, yy + j))) {
                    continue;
                } //
                if (lastGenMat[i + xx][j + yy].getFill() == Color.DIMGRAY && isOnMat(xx + i, yy + j)) {
                    System.out.println(xx + " " + i + " " + yy + " " + j);
                    neighbor_count++;
                }
            }
        }
        return neighbor_count; // TODO: in i = 0 , j =1 i got count =3 ;
    }

    // Check if the index is in the Matrix between
    private boolean isOnMat(int x, int y) {
        return x >= 0 && y >= 0 && x <= 9 && y <= 9 && x != -1 && y != -1;
    }

}
