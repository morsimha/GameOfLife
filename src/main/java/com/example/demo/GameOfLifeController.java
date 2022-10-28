package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class GameOfLifeController {
    final int MAT_SIZE = 100;
    final int SIDE = MAT_SIZE / 10;
    final int CELL_SIZE = 50;
    boolean firstRound = true;
    Rectangle[][] matrix = new Rectangle[MAT_SIZE][MAT_SIZE];
    Rectangle[][] nextGen = new Rectangle[MAT_SIZE][MAT_SIZE];
    private GraphicsContext gc;


    @FXML
    private Canvas cnvs;

    public void initialize() {
        gc = cnvs.getGraphicsContext2D();
        gc.setLineWidth(6);
    }

    //Randomizes a number (0,1) and sets the color cell color by it.
    // WHITE is dead and DIMGRAY is alive
    private Paint randomizeGame(int x, int y) {
        Random r = new Random();
        if (r.nextInt(2) == 1) //Dead
            return Color.WHITE;
        else //Alive
            return Color.DIMGRAY;
    }

    // checks cell's sibling color, to count lives around it.
    private int calcNextGen(int x, int y) {
        int lifeCounter = 0, newX, newY;
        // checks all siblings options, beside the out of range ones.
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                newX = x + i; //new x coordinate to check
                newY = y + j;
                if ((newX >= 0 && newY >= 0) && (newX < SIDE && newY < SIDE)) // check positive coordinate
                    if (!(newX == x && newY == y)) // avoid check the middle one
                        if ((matrix[newX][newY].getFill() == Color.DIMGRAY)) {
                            lifeCounter++;
                        }
            }
        return lifeCounter;
    }

    private Paint paintNextGen(int lifeCounter, int x, int y) {
        Paint currColor = matrix[x][y].getFill();
        //3 siblings means a birth or existence for all cells,
        //2 siblings for a living cell means existence.
        if (lifeCounter == 3 || (lifeCounter == 2 && currColor == Color.DIMGRAY))
            return Color.DIMGRAY;
        else  //every other option means death
            return Color.WHITE;

    }

    @FXML
    protected void btnpressed() {
        int lifeCounter;
        Paint newColor;

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                Rectangle rect =  new Rectangle(i*CELL_SIZE, j*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (firstRound) { //
                    matrix[i][j] = rect;
                //    matrix[i][j] = rect;
                    newColor = randomizeGame(i, j);
                    matrix[i][j].setFill(newColor);
                } else {
                    nextGen[i][j] = rect;
                    lifeCounter = calcNextGen(i, j);
                    newColor = paintNextGen(lifeCounter, i, j);
                    nextGen[i][j].setFill(newColor);
                }
                gc.setFill(newColor);
                gc.fillRect(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWidth(), matrix[i][j].getHeight()); //inside color
                gc.strokeRect(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWidth(), matrix[i][j].getHeight()); //outline color
            }
        }
        if (!firstRound)
            for (int x = 0; x < SIDE; x++) //copy the new matrix to old one.
                System.arraycopy(nextGen[x], 0, matrix[x], 0, SIDE);
        firstRound = false;
    }

}





