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
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author PuFF
 */
public class GameState {

    private Image logo = Console.loadImage("/student/demo/img/logo.png");
    private Music music = new Music();
    private UFO ufo = new UFO();
    private Alien[] aliens = new Alien[55];
    private Laser[] lasers = new Laser[5];
    private Bullets[] bullets = new Bullets[10];
    private Ship ship = new Ship();
    private int stage = 1;
    private Shield shield = new Shield();
    private int alienLeft;
    private int timer = 0;
    private int frame = 0;
    private int i;
    private int speed;
    private boolean Hitbox = false;
    Random r = new Random();

    public void Frame() {
        frame = ++frame % 50;       //50 frame as a cycle
    }

    public void Time() {        //This only run when running() is running
        i = ++i % 20;           //Pause animation when not running
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
        if (stage == 1) {
            speed = 1;
        } else if (stage == 2) {
            speed = 4;
        }

        alienLeft = 55;
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 5; j++) {
                aliens[count] = new Alien();
                aliens[count].init(6 + 60 * i, 30 + 50 * j);
                aliens[count].setSpeed(speed);
                count++;
            }
        }

        ship.init();
        timer = 5;              //make the first UFO spawn after 5 sec, other UFO spawns every 10s and it takes ~5s for them to travel cross screen.
        music.stopBGM();
        music.playBGM();
        for (int i = 0; i < 5; i++) {
            lasers[i] = new Laser();
            lasers[i].destroyLaser();
        }
        for (int i = 0; i < 10; i++) {
            bullets[i] = new Bullets();
            bullets[i].destroyBullets();
        }
        shield.initshield();
        ufo.kill();
    }

    public void running() {
        Time();

        if (frame % 2 == 0) {
            for (int i = 0; i < 55; i++) {          //half the alien move speed
                aliens[i].move();
            }
        }

        ufo.move();
        for (int i = 0; i < 5; i++) {
            lasers[i].move(-10);           //-13 pixel every frame;
        }
        for (int i = 0; i < 10; i++) {
            bullets[i].bulletMove(3);
        }
        if (timer % 10 == 0) {              //every 10s
            ufo.init();
        }

        ufoCollision();
        alienCollision();
        shieldCollision();
        spaceshipCollision();
        checkloseCombo();
        alienSpeed();
        alienShot();
        System.out.println(timer);

        drawRunning();

    }

    public boolean stage() {
        Time();
        if (timer <= 7) {           //run for 2 sec 7-5=3
            drawStage();
            System.out.println(timer);
        } else {
            return true;        //true when stage has shown for 2 sec
        }
        return false;

    }

    public void retry() {
        stage = 1;
        init();
    }

    public void nextStage() {
        stage = 2;
        int shotNumber = ship.getShots();
        init();
        while (shotNumber > 1) {
            ship.addShots(1);
            shotNumber--;
        }
    }

    public void pause() {
        drawRunning();
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
                .drawText(270, 590, "Shots:", new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                .drawText(325, 590, String.valueOf(ship.getShots() + "X"), new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                .drawText(730, 590, "HighestScore:", new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE)
                .drawText(860, 590, String.format("%09d", ship.getHighestScore()), new Font("Comic Sans MS", Font.BOLD, 18), Color.WHITE);

        drawLife();
        drawLaser();
        drawShip();
        drawAlien();
        drawUFO();
        drawShield();
        drawBullets();

        if (Hitbox) {
            drawHitbox();
        }

    }

    public void drawStage() {
        Console.getInstance()
                .drawText(390, 280, String.valueOf("Stage" + stage), new Font("Comic Sans MS", Font.BOLD, 50), Color.WHITE);
    }

    public void drawAlien() {
        for (int j = 0; j < 55; j++) {
            if (aliens[j].getAlive() == true) {
                Console.getInstance().drawImage(aliens[j].getx(), aliens[j].gety(), aliens[j].getimg(i / 10));
            }
        }
    }

    public void drawShield() {
        for (int k = 0; k < 20; k++) {
            if (shield.getAlive(k) == true) {
                Console.getInstance().drawImage(shield.getx(k), shield.gety(k), shield.getimg(k));
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
        for (int i = 0; i < 5; i++) {
            Console.getInstance().drawImage(lasers[i].getx(), lasers[i].gety(), lasers[i].getimg());
        }
    }

    public void drawBullets() {
        for (int i = 0; i < 10; i++) {
            Console.getInstance().drawImage(bullets[i].getx(), bullets[i].gety(), bullets[i].getimg());
        }
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

    public void drawBox(int x, int y, int w, int h) {

        Console.getInstance()
                .drawRectangle(x, y, w, 1, Color.CYAN)
                .drawRectangle(x, y, 1, h, Color.CYAN)
                .drawRectangle(x, y + h, w, 1, Color.CYAN)
                .drawRectangle(x + w, y, 1, h, Color.CYAN);
    }

    public void drawHitbox() {
        for (int i = 0; i < 55; i++) {
            if (aliens[i].getAlive()) {
                drawBox(aliens[i].getHbx(), aliens[i].getHby(), aliens[i].getWidth(), aliens[i].getHeight());
            }
        }
        for (int i = 0; i < 5; i++) {
            if (lasers[i].onScreen()) {
                drawBox(lasers[i].getHbx(), lasers[i].getHby(), lasers[i].getWidth(), lasers[i].getHeight());
            }
        }
        for (int i = 0; i < 10; i++) {
            if (bullets[i].onScreen()) {
                drawBox(bullets[i].getHbx(), bullets[i].getHby(), bullets[i].getWidth(), bullets[i].getHeight());
            }
        }
        drawBox(ufo.getHbx(), ufo.getHby(), ufo.getWidth(), ufo.getHeight());
        drawBox(ship.getHbx(), ship.getHby(), ship.getWidth(), ship.getHeight());
        for (int i = 0; i < 20; i++) {
            if (shield.getAlive(i)) {
                drawBox(shield.getx(i), shield.gety(i), shield.getWidth(), shield.getHeight());
            }
        }
    }

    public void toggleHitbox() {
        Hitbox = !Hitbox;
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

        for (int i = 0; i < ship.getShots(); i++) {

            if (!lasers[i].onScreen()) {
                music.playShoot();
                lasers[i].init(ship.getx(), ship.gety());
                break;                  //make sure only one shot is fire every time, prevent 5 shots at once.
            }
        }
    }

    public void alienShot() {

        if (r.nextInt(100) == 1) {
            for (int i = 0; i < 10; i++) {
                if (!bullets[i].onScreen()) {
                    bullets[i] = new Bullets();
                    bullets[i].destroyBullets();
                    int randomAlien;
                    do {
                        randomAlien = r.nextInt(54);
                    } while (aliens[randomAlien].getAlive() == false);
                    int alienX = aliens[randomAlien].getx();
                    int alienY = aliens[randomAlien].gety();
                    music.playShoot();
                    bullets[i].bulletInit(alienX, alienY);
                    break;
                }
            }
        }
    }

    public void ufoCollision() {

        for (int i = 0; i < 5; i++) {
            if (ufo.collision(lasers[i].getHbx(), lasers[i].getHby(), lasers[i].getWidth(), lasers[i].getHeight())) {
                music.playInvaderKilled();
                lasers[i].destroyLaser();
                ship.addScore(10000);
                ship.addShots(1);
                ufo.kill();
                if (ship.score > ship.highestScore) {
                    ship.setHighestScore(ship.score);
                }
                ship.addCombo(10);
            }
        }
    }

    public void alienCollision() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 55; j++) {
                if (aliens[j].collision(lasers[i].getHbx(), lasers[i].getHby(), lasers[i].getWidth(), lasers[i].getHeight())) {
                    music.playInvaderKilled();
                    lasers[i].destroyLaser();
                    ship.addScore(1000);
                    alienLeft--;
                    if (ship.score > ship.highestScore) {
                        ship.setHighestScore(ship.score);
                    }
                    ship.addCombo(1);
                }
            }
        }
    }

    public void spaceshipCollision() {

        for (int i = 0; i < 10; i++) {
            if (ship.collision(bullets[i].getHbx(), bullets[i].getHby(), bullets[i].getWidth(), bullets[i].getHeight())) {
                music.playExplosion();
                loseLife();
                bullets[i].destroyBullets();
            }
        }
        for (int i = 0; i < 10; i++) {
            if (shield.collision(bullets[i].getHbx(), bullets[i].getHby(), bullets[i].getWidth(), bullets[i].getHeight())) {
                music.playInvaderKilled();
                bullets[i].destroyBullets();
            }
        }

    }

    public void shieldCollision() {

        for (int i = 0; i < 5; i++) {
            if (shield.collision(lasers[i].getHbx(), lasers[i].getHby(), lasers[i].getWidth(), lasers[i].getHeight())) {
                music.playInvaderKilled();
                lasers[i].destroyLaser();
            }
        }
        for (int i = 0; i < 10; i++) {
            if (shield.collision(bullets[i].getHbx(), bullets[i].getHby(), bullets[i].getWidth(), bullets[i].getHeight())) {
                music.playInvaderKilled();
                bullets[i].destroyBullets();
            }
        }
        for (int i = 0; i < 55; i++) {
            if (aliens[i].getAlive() == true) {
                if (shield.collision(aliens[i].getHbx(), aliens[i].getHby(), aliens[i].getWidth(), aliens[i].getHeight())) {
                }
            }
        }
    }

    public void alienSpeed() {
        if (stage == 1) {
            if (alienLeft >= 30) {
                speed = 1;
            } else if (alienLeft >= 15 && alienLeft < 30) {
                speed = 2;
            } else if (alienLeft >= 5 && alienLeft < 15) {
                speed = 3;
            } else if (alienLeft >= 0 && alienLeft < 5) {
                speed = 4;
            }
        } else if (stage == 2) {
            if (alienLeft >= 30) {
                speed = 4;
            } else if (alienLeft >= 15 && alienLeft < 30) {
                speed = 5;
            } else if (alienLeft >= 5 && alienLeft < 15) {
                speed = 6;
            } else if (alienLeft >= 0 && alienLeft < 5) {
                speed = 7;
            }
        }
        for (int i = 0; i < 55; i++) {
            aliens[i].setSpeed(speed);
        }
    }

    public void checkloseCombo() {

        for (int i = 0; i < 5; i++) {
            if (lasers[i].gety() < 0 && lasers[i].gety() > -100) {
                ship.loseCombo();
            }
        }
    }

    public void loseLife() {
        ship.loseLife();
    }

    public String checkWin() {
        if (stage == 2) {
            if (alienLeft == 0) {
                return "WIN";
            }
        } else if (stage == 1) {
            if (alienLeft == 0) {
                nextStage();
                return "NEXTSTAGE";
            }
        }
        return "0";
    }

    public boolean checkLose() {
        for (int i = 0; i < 55; i++) {
            if (aliens[i].reachBottom()) {
                return true;
            }
        }
        if (ship.getLife() == 0) {
            return true;
        }
        return false;
    }

}
