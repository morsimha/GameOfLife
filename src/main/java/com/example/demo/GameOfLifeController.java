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
    Rectangle[][] matrix = new Rectangle[MAT_SIZE][MAT_SIZE];
    Rectangle[][] nextGeneration = new Rectangle[MAT_SIZE][MAT_SIZE];
    Random r = new Random();
    boolean firstRound = true;
    private GraphicsContext gc;
    private GraphicsContext outlineGc;


    @FXML
    private Canvas cnvs;

    public void initialize() {
        gc = cnvs.getGraphicsContext2D();
        outlineGc = cnvs.getGraphicsContext2D(); //this Graphics Context settings will define the outlines of each cell.
        outlineGc.setFill(Color.BLACK);
        outlineGc.setLineWidth(3);
    }

    @FXML
    protected void btnpressed() {
        int lifeCounter;

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (firstRound) {
                    matrix[i][j] = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    randomizeGame(i, j);
                    gc.fillRect(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWidth(), matrix[i][j].getHeight()); //inside color
                } else {
                    nextGeneration[i][j] = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    lifeCounter = calcNextGen(i, j);
                    System.out.println("this is lifeCounter for (" +i+","+j +"): "+lifeCounter);
                    paintNextGen(lifeCounter, i, j);
                    gc.fillRect(nextGeneration[i][j].getX(), nextGeneration[i][j].getY(), nextGeneration[i][j].getWidth(), nextGeneration[i][j].getHeight()); //inside color
                }
                outlineGc.strokeRect(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWidth(), matrix[i][j].getHeight()); //outline color
            }
        }
        if (!firstRound)
            for (int x = 0; x < SIDE; x++)
                System.arraycopy(nextGeneration[x], 0, matrix[x], 0, SIDE);
        firstRound = false;
    }

    //Randomizes a number (0,1) and sets the color cell color by it.
    // WHITE is dead and DIMGRAY is alive
    private void randomizeGame(int x, int y) {
        Color state;
        if (r.nextInt(2) == 1) //Dead
            state = Color.WHITE;
        else //Alive
            state = Color.DIMGRAY;

        gc.setFill(state); //setting the cell's color.
        matrix[x][y].setFill(state); //storing the color in the matrix
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
                            System.out.println("cord - (" + x + " , " + y + ") and check:  (" + newX + ", " + newY + ")");
                        }
            }
        return lifeCounter;
    }

    private void paintNextGen(int lifeCounter, int x, int y) {
        Paint currColor = matrix[x][y].getFill();

        if (lifeCounter == 3) { //3 siblings means a birth or existence
            gc.setFill(Color.DIMGRAY);
            nextGeneration[x][y].setFill(Color.DIMGRAY);
        } else if (lifeCounter == 2 && currColor == Color.DIMGRAY) { //2 siblings for a living cell means existence
            gc.setFill(Color.DIMGRAY);
            nextGeneration[x][y].setFill(Color.DIMGRAY);
        } else { //every other option means death
            gc.setFill(Color.WHITE);
            nextGeneration[x][y].setFill(Color.WHITE);
        }
    }
}

//
//        if (currColor == Color.DIMGRAY) { //alive
//            if (lifeCounter == 2 || lifeCounter == 3) {
//                gc.setFill(Color.DIMGRAY);
//                nextGeneration[x][y].setFill(Color.DIMGRAY);
//            } else {
//                gc.setFill(Color.WHITE);
//                nextGeneration[x][y].setFill(Color.WHITE);
//            }
//        } else { //dead
//            if (lifeCounter == 3) { //back to life
//                gc.setFill(Color.DIMGRAY);
//                nextGeneration[x][y].setFill(Color.DIMGRAY);
//            }
//            else {
//                gc.setFill(Color.WHITE);
//                nextGeneration[x][y].setFill(Color.WHITE);
//            }
//        }
//    }








