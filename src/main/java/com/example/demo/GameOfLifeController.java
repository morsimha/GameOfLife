package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Random;


//use rectangle, in an array.
// use random to generate life
// use 2 2d arrays, for current and next
// change color than apply
//rectangle attributes

public class GameOfLifeController {
    final int SIZE = 100;
    Rectangle[][] matrix = new Rectangle[SIZE][SIZE];
    Rectangle[][] nextGeneration = new Rectangle[SIZE][SIZE];
    Random r = new Random();
    boolean firstGame = true;
    private GraphicsContext gc;
    private GraphicsContext outlineGc;


    @FXML
    private Canvas cnvs;

    public void initialize() {
        gc = cnvs.getGraphicsContext2D();
        outlineGc = cnvs.getGraphicsContext2D();

//        Rectangle [][] matrix = new Rectangle[SIZE][SIZE];

    }

    void randomizeGame(Rectangle rect) {
        if (r.nextInt(2) == 1) {
            gc.setFill(Color.BLACK);
            rect.setOpacity(1);
        } else {
            gc.setFill(Color.WHITE);
            rect.setOpacity(0);
        }
    }


    void calcNextGen(int x, int y, Rectangle rect) {
        int lifeCounter = 0;
        Paint currColor = matrix[x][y].getFill();
        for (int i = -10; i < 2*10; i+=10)
            for (int j = -10; j < 2*10; j+=10) {
                int newx = x + i;
                int newy = y + i;
             //   System.out.println(newx + " " + newy);

                if ((newx >= 0 && newy >= 0) && (newx <= 9 && newy <= 9)) //positive
                    if ((matrix[newx][newy].getOpacity() == 0))
                        lifeCounter++;
            }
        System.out.println("lifc "+lifeCounter);

        if (currColor == Color.WHITE) {
            if (lifeCounter == 2 || lifeCounter == 3) {
                gc.setFill(Color.BLUE);
                rect.setFill(Color.BLUE);
            }
        } else{// if (lifeCounter == 3) {
            gc.setFill(Color.RED);
            rect.setFill(Color.RED);
        }
        nextGeneration[x][y] = rect;
    }

    @FXML
    protected void btnpressed() {
        Random r = new Random();
        int count =0;
//        gc.strokeRect(3,3,50,50); //outline color
//        outlineGc.setFill(Color.WHITE);
//        if (!firstGame)
//            gc.fillRect(6,6,44,44); //inside color

        outlineGc.setFill(Color.BLACK);
        //outlineGc.setLineWidth(1);

        for (int i = 0; i < SIZE; i+=10) {
            for (int j = 0; j < SIZE; j += 10) {
                int x = r.nextInt(2);
                count++;
                System.out.println(i + j);

                //  gc.clearRect();
                Rectangle rect = new Rectangle(i * 5,j*5,50, 50);
                if (firstGame) {
                    randomizeGame(rect);
                }
                else{ //calculate next gen
                    calcNextGen(i,j,rect);
                }
                matrix [i][j] = rect;
                gc.fillRect(rect.getX(), rect.getY(),rect.getWidth(),rect.getHeight()); //inside color
                outlineGc.strokeRect(rect.getX(), rect.getY(),rect.getWidth(),rect.getHeight()); //outline color


            }
        }
        firstGame = false;

    }
}








