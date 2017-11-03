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
    private Image logo = Console.loadImage("/student/demo/img/logo.png");
    private Image img2 = Console.loadImage("/student/demo/img/shiplife.png");
    private int i = 0;
    private boolean start;
    private boolean pause = false;
    private boolean lose = false;
    private boolean win = false;
    private Alien alien = new Alien();
    private Ship ship = new Ship();
    private Laser laser = new Laser();
    private UFO ufo = new UFO();
    private int timer;
    private Music music = new Music();
    private boolean isExplosion = false;
    private Image[] lifeArray;

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

        i = ++i % 20;       // 0 to 19

        if (!start) {
            music.stopBGM();
            Console.getInstance()
                    .drawImage(280, 10, logo)
                    .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                    .drawText(380, 250, "New Game", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN);

        } else if (pause) {
            Console.getInstance()
                    .drawRectangle(360, 105, 250, 60, Color.DARK_GRAY, 20)
                    .drawText(380, 150, "Resume", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN)
                    .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                    .drawText(380, 250, "Retry", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN);
        } else {
            music.playBGM();

            if (alien.reachBottom()) {
                lose = true;
            }
            if (alien.allDead()) {
                win = true;
            }
            if (!win && !lose) {

                if (i == 19) {
                    timer += 1;
                    if (timer >= 30) {
                        ufo.init();
                        timer = 0;
                    }
                }
                alien.move();
                ufo.move();
                laser.move();
            }
            if (laser.gety() < 0 && laser.gety() > -100) {
                ship.loseCombo();
            }

            if (alien.collision(laser.getx(), laser.gety())) {
                music.playInvaderKilled();
                laser.destroyLaser();
                ship.addScore(1000);
                if (ship.score > ship.highestScore) {
                    ship.setHighestScore(ship.score);
                }
                ship.addCombo(1);
            }
            if (ufo.collision(laser.getx(), laser.gety())) {
                music.playInvaderKilled();
                laser.destroyLaser();
                ship.addScore(10000);
                if (ship.score > ship.highestScore) {
                    ship.setHighestScore(ship.score);
                }
                ship.addCombo(10);
            }
            if (isExplosion) {
                ship.loseLife();
                if (ship.getLife() == 0) {
                    lose = true;
                }
                isExplosion = false;
            }

            for (int j = 0; j < 55; j++) {
                if (alien.getAlive(j) == true) {
                    console.drawImage(alien.getx(j), alien.gety(j), alien.getimg(i / 10));
                }
            }
            if (ufo.getAlive()) {
                console.drawImage(ufo.getx(), ufo.gety(), ufo.getimg(i / 10));
            }

            console.drawImage(laser.getx(), laser.gety(), laser.getimg());
            console.drawImage(ship.getx(), ship.gety(), ship.getimg());

            Console.getInstance()
                    .drawText(540, 590, "Score:", new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                    .drawText(600, 590, String.format("%09d", ship.getScore()), new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                    .drawText(400, 590, "Combo:", new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                    .drawText(465, 590, String.valueOf(ship.getCombo() + "X"), new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                    .drawText(730, 590, "HighestScore:", new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                    .drawText(860, 590, String.format("%09d", ship.getHighestScore()), new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE);

            if (ship.getLife() == 3) {
                console.drawImage(12, 570, ship.getimg2());
                console.drawImage(40, 570, ship.getimg2());
            }
            if (ship.getLife() == 2) {
                console.drawImage(12, 570, ship.getimg2());
            }

            if (win) {
                music.stopBGM();
                ufo.init();
                Console.getInstance()
                        .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                        .drawText(380, 250, "New Game", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN)
                        .drawText(350, 100, "You Win!", new Font("Comic Sans MS", Font.BOLD, 60), Color.YELLOW)
                        .drawText(300, 170, String.format("Your Score: " + ship.getScore()), new Font("Comic Sans MS", Font.BOLD, 40), Color.WHITE);
            }
            if (lose) {
                music.stopBGM();
                Console.getInstance()
                        .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                        .drawText(380, 250, "Retry", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN)
                        .drawText(300, 100, "You're Dead", new Font("Comic Sans MS", Font.BOLD, 60), Color.RED)
                        .drawText(300, 170, String.format("Your Score: " + ship.getScore()), new Font("Comic Sans MS", Font.BOLD, 40), Color.WHITE);
            }
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
                if (!pause && !win && !lose) {
                    music.playMove();
                    ship.move('L');
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!pause && !win && !lose) {
                    music.playMove();
                    ship.move('R');
                }
                break;
            case KeyEvent.VK_L:
                lose = true;
                win = false;

                break;
            case KeyEvent.VK_W:
                win = true;
                lose = false;
                alien.killAll();
                break;
            case KeyEvent.VK_SPACE:
                if (!laser.onScreen()) {
                    if (!pause && !win && !lose) {
                        music.playShoot();
                        laser.init(ship.getx(), ship.gety());
                    }
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if (start && !win && !lose) {
                    pause = !pause;
                }
                break;
            case KeyEvent.VK_E:
                if (!isExplosion) {
                    isExplosion = true;
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
        if (!start) {
            if (checkMouse(e, 360, 205, 600, 260)) {
                alien.init();
                ship.init();
                timer = 15;
                start = true;
                win = false;
                lose = false;
            }
        } else if (pause) {     //pause menu
            if (checkMouse(e, 360, 105, 600, 160)) {  //resume
                pause = false;
            }
            if (checkMouse(e, 360, 205, 600, 260)) {  //retry
                music.stopBGM();
                music.playBGM();
                alien.init();
                ship.init();
                laser.destroyLaser();
                ufo.kill();
                timer = 15;
                pause = false;
            }
        } else if (win || lose) {       //win or lose screen
            if (checkMouse(e, 360, 205, 600, 260)) {        //retry or new game
                alien.init();
                ship.init();
                laser.destroyLaser();
                ufo.kill();
                timer = 15;
                win = false;
                lose = false;
            }
        }

    }

    protected boolean checkMouse(MouseEvent e, int a, int b, int x, int y) {
        if (a < e.getX() && e.getX() < x && e.getY() > b && e.getY() < y) {
            return true;
        } else {
            return false;
        }
    }

}
