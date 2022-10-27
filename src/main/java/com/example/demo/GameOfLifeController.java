package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class GameOfLifeController {
    final int SIZE = 100;
    final int CELL_SIZE = 50;
    Rectangle[][] matrix = new Rectangle[SIZE][SIZE];
    Rectangle[][] nextGeneration = new Rectangle[SIZE][SIZE];
    Random r = new Random();
    boolean firstRound = true;
    private GraphicsContext gc;
    private GraphicsContext outlineGc;


    @FXML
    private Canvas cnvs;

    public void initialize() {
        gc = cnvs.getGraphicsContext2D();
        outlineGc = cnvs.getGraphicsContext2D();
    }

    private void randomizeGame(int x, int y) {
        if (r.nextInt(2) == 1) {
            gc.setFill(Color.BLACK); //setting gc color
            matrix [x][y].setFill(Color.BLACK);
        } else {
            gc.setFill(Color.WHITE);
            matrix [x][y].setFill(Color.WHITE);
        }
    }

    private int calcNextGen(int x, int y) {
        int lifeCounter = 0;

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                int newx = x + i;
                int newy = y + j;
               // System.out.println("x is " + x + " y is " + y + " newx is " + newx + " newy is " + newy);

                if ((newx >= 0 && newy >= 0) && (newx <= 9 && newy <= 9)) {//positive
                    if (newx != x || newy != y) {
                        System.out.println("cord - (" + x + " , " + y + ") and check:  (" + newx + ", " + newy + ")");
                        if ((matrix[newx][newy].getFill() == Color.WHITE))
                            lifeCounter++;
                   }
                }
            }
        return lifeCounter;
    }

    private void paintNextGen (int lifeCounter, int x, int y) {
        Paint currColor = matrix[x][y].getFill();

        if (currColor == Color.WHITE) { //alive
            if (lifeCounter == 2 || lifeCounter == 3) {
                gc.setFill(Color.WHITE);
                nextGeneration[x][y].setFill(Color.WHITE);
            } else {
                gc.setFill(Color.BLACK);
                nextGeneration[x][y].setFill(Color.BLACK);
            }
        } else { //dead
            if (lifeCounter == 3) { //back to life
                gc.setFill(Color.WHITE);
                nextGeneration[x][y].setFill(Color.WHITE);
            }
            else {
                gc.setFill(Color.BLACK);
                nextGeneration[x][y].setFill(Color.BLACK);
            }
        }
    }


    @FXML
    protected void btnpressed() {
        Random r = new Random();
        outlineGc.setFill(Color.BLACK);
        outlineGc.setLineWidth(3);

        for (int i = 0; i < SIZE/10; i+=1) {
            for (int j = 0; j < SIZE / 10; j += 1) {
                if (firstRound) {
                    matrix[i][j] = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    randomizeGame(i, j);
                    gc.fillRect(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWidth(), matrix[i][j].getHeight()); //inside color
                    outlineGc.strokeRect(matrix[i][j].getX(), matrix[i][j].getY(), matrix[i][j].getWidth(), matrix[i][j].getHeight()); //outline color
                } else {
                    int lifeCounter = calcNextGen(i, j);
                    System.out.println("this is lifeCounter for (" +i+","+j +"): "+lifeCounter);
                    nextGeneration[i][j] = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    paintNextGen(lifeCounter, i, j);
                    gc.fillRect(nextGeneration[i][j].getX(), nextGeneration[i][j].getY(), nextGeneration[i][j].getWidth(), nextGeneration[i][j].getHeight()); //inside color
                    outlineGc.strokeRect(nextGeneration[i][j].getX(), nextGeneration[i][j].getY(), nextGeneration[i][j].getWidth(), nextGeneration[i][j].getHeight()); //outline color
                }

            }
        }
        if (!firstRound) {

            for (int x = 0; x < SIZE / 10; x++)
                System.arraycopy(nextGeneration[x], 0, matrix[x], 0, SIZE / 10);
        }

        firstRound = false;
    }
}








