/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.demo;

import game.v2.Console;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

/**
 *
 * @author PuFF
 */
public class GameState {

    private Image logo = Console.loadImage("/student/demo/img/logo.png");
    private Music music = new Music();
    private UFO ufo = new UFO();
    private Alien alien = new Alien();
    private Laser laser = new Laser();
    private Ship ship = new Ship();
    private int timer = 0;
    private int frame = 0;
    private int i;

    public void Frame() {
        i = ++i % 20;                //0-19 
        frame = ++frame % 50;       //50 frame as a cycle
        if (frame == 49) //50 frame = 1s (50fps)
        {
            timer++;                // + 1sec
        }

    }

    public void startMenu() {
        music.stopBGM();
        Console.getInstance()
                .drawImage(280, 10, logo)
                .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 250, "New Game", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN);
    }

    public void init() {
        alien.init();
        ship.init();
        timer = 5;              //make the first UFO spawn at 5 sec, other UFO spawns every 10s as it takes ~5s for them to travel cross screen.
        music.stopBGM();
        music.playBGM();
        laser.destroyLaser();
        ufo.kill();
    }

    ;
    public void running() {

        alien.move();
        ufo.move();
        laser.move();
        if (timer % 10 == 0) {
            ufo.init();
        }

        ufoCollision();
        alienCollision();
        checkloseCombo();
        drawRunning();

    }

    public void pause() {
        Console.getInstance()
                .drawRectangle(360, 105, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 150, "Resume", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN)
                .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 250, "Retry", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN);
    }

    public void win() {
        ufo.init();     //remove ufo from screen
        music.stopBGM();
        Console.getInstance()
                .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 250, "New Game", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN)
                .drawText(350, 100, "You Win!", new Font("Comic Sans MS", Font.BOLD, 60), Color.YELLOW)
                .drawText(300, 170, String.format("Your Score: " + ship.getScore()), new Font("Comic Sans MS", Font.BOLD, 40), Color.WHITE);
    }

    public void lose() {
        music.stopBGM();
        drawRunning();
        Console.getInstance()
                .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 250, "Retry", new Font("Comic Sans MS", Font.BOLD, 40), Color.CYAN)
                .drawText(300, 100, "You're Dead", new Font("Comic Sans MS", Font.BOLD, 60), Color.RED)
                .drawText(300, 170, String.format("Your Score: " + ship.getScore()), new Font("Comic Sans MS", Font.BOLD, 40), Color.WHITE);
    }

    public void drawRunning() {

        Console.getInstance()
                .drawText(540, 590, "Score:", new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                .drawText(600, 590, String.format("%09d", ship.getScore()), new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                .drawText(400, 590, "Combo:", new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                .drawText(465, 590, String.valueOf(ship.getCombo() + "X"), new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                .drawText(730, 590, "HighestScore:", new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                .drawText(860, 590, String.format("%09d", ship.getHighestScore()), new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE);
        
        drawLife();
        drawLaser();
        drawShip();
        drawAlien();
        drawUFO();

    }

    public void drawAlien() {
        for (int j = 0; j < 55; j++) {
            if (alien.getAlive(j) == true) {
                Console.getInstance().drawImage(alien.getx(j), alien.gety(j), alien.getimg(i / 10));
            }
        }
    }

    public void drawShip() {
        Console.getInstance().drawImage(ship.getx(), ship.gety(), ship.getimg());
    }

    public void drawUFO() {
        Console.getInstance().drawImage(ufo.getx(), ufo.gety(), ufo.getimg(i / 10));
    }

    public void drawLaser() {
        Console.getInstance().drawImage(laser.getx(), laser.gety(), laser.getimg());
    }

    public void drawLife() {
        if (ship.getLife() == 3) {
            Console.getInstance().drawImage(12, 570, ship.getimg2());
            Console.getInstance().drawImage(40, 570, ship.getimg2());
        }
        if (ship.getLife() == 2) {
            Console.getInstance().drawImage(12, 570, ship.getimg2());
        }
    }

    public void moveR() {
        ship.move('R');
        music.playMove();
    }

    public void moveL() {
        ship.move('L');
        music.playMove();
    }

    public void shoot() {

        if (!laser.onScreen()) {

            music.playShoot();
            laser.init(ship.getx(), ship.gety());

        }
    }

    public void ufoCollision() {
        if (ufo.collision(laser.getx(), laser.gety())) {
            music.playInvaderKilled();
            laser.destroyLaser();
            ship.addScore(10000);
            ufo.kill();
            if (ship.score > ship.highestScore) {
                ship.setHighestScore(ship.score);
            }
            ship.addCombo(10);
        }
    }

    public void alienCollision() {
        if (alien.collision(laser.getx(), laser.gety())) {
            music.playInvaderKilled();
            laser.destroyLaser();
            ship.addScore(1000);
            if (ship.score > ship.highestScore) {
                ship.setHighestScore(ship.score);
            }
            ship.addCombo(1);
        }
    }

    public void checkloseCombo() {
        if (laser.gety() < 0 && laser.gety() > -100) {
            ship.loseCombo();
        }
    }

    public void loseLife() {
        ship.loseLife();
    }

    public boolean checkWin() {
        return alien.allDead();
    }

    public boolean checkLose() {
        if (alien.reachBottom()) {
            return true;
        }
        if (ship.getLife() == 0) {
            return true;
        }
        return false;
    }
}
