/**
 * Copyright (c) 2012, Van Ting <vanting@gmail.com>
 * All rights reserved. Unauthorized use and redistribution of this file is
 * strictly prohibited.
 */
package student.demo;

import game.v2.Console;
import game.v2.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Examples to demonstrate the use of Game. The Game class provides an
 * interactive game skeleton. It facilitates the frame rate (fps) control and
 * input interface.
 *
 * @author vanting
 */
public class GameDemo extends Game {

    /*
    You can declare any data fields here for your game as usual.
     */
    private GameState gameState = new GameState();

    private static enum State {
        MENU,
        RUNNING,
        INIT,
        PAUSE,
        WIN,
        LOSE,
        STAGE,
    }
    private static State state;

    /*
    Main method
     */
    public static void main(String[] args) {

        /*
        Customize the console window per your need but do not show it yet.
         */
        Console.getInstance()
                .setTitle("Game Demo")
                .setWidth(1000)
                .setHeight(600)
                .setTheme(Console.Theme.DARK);

        /*
        Similar to the Console class, use the chaining setters to configure the game. Call start() at the end of
        the chain to start the game loop.
         */
        new GameDemo()
                .setFps(50) // set frame rate
                .setShowFps(true) // set to display fps on screen
                .setBackground(Console.loadImage("/student/demo/img/bg.png")) // set background image
                .start();                                                   // start game loop

        state = state.MENU; //initiate default state 

    }

    /**
     * **********************************************************************************************
     * There are three abstract methods must be overridden: protected abstract
     * void cycle(); protected abstract void keyPressed(KeyEvent e); protected
     * abstract void mouseClicked(MouseEvent e);
     */
    /*
    Called regularly at predefined frame rate (fps).
    This callback is used to program what to do in each cycle, i.e. a particular frame. 
    For example, if you have set a frame rate at 50, this method will be invoked approximately 
    50 times every second. At the end of each cycle, Console.update() is called implicitly to 
    flush the drawings from buffer to screen.
     */
    @Override
    protected void cycle() {

        gameState.Frame();  //frame function that counts frames and time

        switch (state) {
            case MENU:
                gameState.startMenu();
                break;

            case PAUSE:
                gameState.pause();
                break;

            case STAGE:
                if (gameState.stage()) {
                    state = state.RUNNING;
                }
                break;

            case RUNNING:
                gameState.running();
                if (gameState.checkWin() == "NEXTSTAGE") {
                    state = state.STAGE;
                }
                if (gameState.checkWin() == "WIN") {
                    state = state.WIN;
                }
                if (gameState.checkLose()) {
                    state = state.LOSE;
                }
                break;

            case WIN:
                gameState.win();
                break;

            case LOSE:
                gameState.lose();
                break;
        }
    }

    /*
    Called when pressing a key.
    You can leave this method empty if you do not use it.
     */
    @Override
    protected void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (state == state.RUNNING) {
                    gameState.moveL();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (state == state.RUNNING) {
                    gameState.moveR();
                }
                break;
            case KeyEvent.VK_SPACE:
                if (state == state.RUNNING) {
                    gameState.shoot();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                switch (state) {
                    case RUNNING:
                        state = state.PAUSE;
                        break;
                    case PAUSE:
                        state = state.RUNNING;
                        break;
                }
                break;
            case KeyEvent.VK_F3:
                gameState.toggleDev();
                break;
            case KeyEvent.VK_L:
                if (state == state.RUNNING && gameState.getDev()) {
                    state = state.LOSE;
                }
                break;
            case KeyEvent.VK_W:
                if (state == state.RUNNING && gameState.getDev()) {
                    state = state.WIN;
                }
                break;
            case KeyEvent.VK_N:
                if (state == state.RUNNING && gameState.getDev()) {
                    gameState.nextStage();
                    state = state.STAGE;
                }
                break;
            case KeyEvent.VK_E:
                if (state == state.RUNNING && gameState.getDev()) {
                    gameState.loseLife();
                }
                break;
            case KeyEvent.VK_A:
                if (state == state.RUNNING && gameState.getDev()) {
                    gameState.addLife();
                }
                break;
            case KeyEvent.VK_H:
                if (state == state.RUNNING && gameState.getDev()) {
                    gameState.toggleHitbox();
                }
                break;
            case KeyEvent.VK_P:
                if (state == state.RUNNING && gameState.getDev()) {
                    gameState.ship.addShots(1);
                }
                break;
        }

    }


    /*
    Called when mouse-clicking on window.
    You can leave this method empty if you do not use it.
     */
    @Override
    protected void mouseClicked(MouseEvent e) {
        System.out.println("Click on (" + e.getX() + "," + e.getY() + ")");

        switch (state) {
            case MENU:
                if (checkMouse(e, 360, 205, 600, 260)) {    //Start button
                    gameState.retry();
                    state = State.STAGE;
                }
                break;
            case PAUSE:
                if (checkMouse(e, 360, 105, 600, 160)) {    //Resume
                    state = state.RUNNING;
                } else if (checkMouse(e, 360, 205, 600, 260)) {     //Retry
                    gameState.retry();
                    state = state.STAGE;
                } else if (checkMouse(e, 360, 305, 600, 360)) {     //Main Menu
                    state = state.MENU;
                }
                break;
            case WIN:
                if (checkMouse(e, 360, 205, 600, 260)) {     //New game
                    gameState.retry();
                    state = state.STAGE;
                } else if (checkMouse(e, 360, 305, 600, 360)) {     //Main Menu
                    state = state.MENU;
                }
                break;
            case LOSE:
                if (checkMouse(e, 360, 205, 600, 260)) {    //Retry
                    gameState.retry();
                    state = state.STAGE;
                } else if (checkMouse(e, 360, 305, 600, 360)) {     //Main Menu
                    state = state.MENU;
                }
                break;
        }

    }

    protected boolean checkMouse(MouseEvent e, int a, int b, int x, int y) {        //ab = cood of top left, xy = cood of bottom right
        return a < e.getX() && e.getX() < x && e.getY() > b && e.getY() < y;
    }

}
