/**
 * Maman 11, question 1.
 * Mor Simha, 206029993.
 */

package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameOfLifeController {
    @FXML
    private Canvas cnvs;

    private final int SIDE = 10;
    private GraphicsContext gc;
    GameOfLifeLogic game;

    public void initialize() {
        game = new GameOfLifeLogic();
        gc = cnvs.getGraphicsContext2D();
        gc.setLineWidth(6);
        game.createFirstGeneration();
        fillRects();
    }

    @FXML
    private void btnPressed() {
        game.updateGeneration();
        fillRects();
    }

    //Filling the rectangles according to the right color.
    private void fillRects() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                gc.setFill(game.getMat()[i][j].getFill()); //choosing the right color for the cell
                gc.fillRect(game.getMat()[i][j].getX(), game.getMat()[i][j].getY(), game.getMat()[i][j].getWidth(), game.getMat()[i][j].getHeight()); //filling inside color
                gc.strokeRect(game.getMat()[i][j].getX(), game.getMat()[i][j].getY(), game.getMat()[i][j].getWidth(), game.getMat()[i][j].getHeight()); //filling outline color
            }
        }
    }
}
