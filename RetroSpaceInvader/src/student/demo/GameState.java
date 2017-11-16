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
    private Image pwr1 = Console.loadImage("/student/demo/img/powerup_2.png");
    private Image pwr2 = Console.loadImage("/student/demo/img/powerup_3.png");
    private Image pwr3 = Console.loadImage("/student/demo/img/powerup_1.png");
    private Image alien = Console.loadImage("/student/demo/img/invader64_1.png");
    private Music music = new Music();
    private UFO ufo = new UFO();
    private Alien[] aliens = new Alien[55];
    private Laser[] lasers = new Laser[5];
    private Bullets[] bullets = new Bullets[10];
    private Powerup[] powerups = new Powerup[5];
    private Blaster[] blasters = new Blaster[10];
    private UFO[] kappa = new UFO[10];
    private Beam beam = new Beam();
    Ship ship = new Ship();
    private int stage = 1;
    private Shield shield = new Shield();
    private int alienLeft;
    private double timer = 0;
    private int frame = 0;
    private int i;
    private int speed;
    private boolean Hitbox = false;
    private boolean dev = false;
    private int rand;
    Random r = new Random();

    public void Frame() {
        frame = ++frame % 50;       //50 frame as a cycle

    }

    public void Time() {        //This only run when running() is running
        i = ++i % 20;           //Pause animation when not running

        if (frame % 5 == 0) //50 frame = 1s (50fps)
        {
            timer = timer * 10 + 1;                // + 0.1sec       
            timer = timer / 10;             //to prevent error in double.
            System.out.println(timer);

        }
    }

    public void startMenu() {
        music.stopBGM();
        Console.getInstance()
                .drawText(380, 460, "PowerUps", new Font("Agency FB", Font.BOLD, 25), Color.WHITE)
                .drawImage(280, 30, logo)
                .drawImage(350, 480, pwr1)
                .drawText(400, 500, "Max Laser +1 (5 Max)", new Font("Agency FB", Font.BOLD, 20), Color.WHITE)
                .drawImage(350, 520, pwr2)
                .drawText(400, 540, "Life +1", new Font("Agency FB", Font.BOLD, 20), Color.WHITE)
                .drawImage(350, 560, pwr3)
                .drawText(400, 580, "Ghaster Blaster +1", new Font("Agency FB", Font.BOLD, 20), Color.WHITE)
                .drawRectangle(360, 305, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 350, "New Game", new Font("Agency FB", Font.BOLD, 40), Color.CYAN)
                .drawText(50, 460, "Controls", new Font("Agency FB", Font.BOLD, 25), Color.WHITE)
                .drawText(20, 500, "L/R arrows - move", new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(20, 520, "Space - Shoot laser", new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(20, 540, "B       - Summon Ghaster Blaster", new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(20, 560, "Esc    - Pause/unpause", new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(750, 460, "Scores", new Font("Agency FB", Font.BOLD, 25), Color.WHITE)
                .drawText(750, 510, "+10000", new Font("Agency FB", Font.BOLD, 25), Color.WHITE)
                .drawText(750, 560, "+1000", new Font("Agency FB", Font.BOLD, 25), Color.WHITE)
                .drawText(850, 460, "Combo", new Font("Agency FB", Font.BOLD, 25), Color.WHITE)
                .drawText(850, 510, "+10", new Font("Agency FB", Font.BOLD, 25), Color.WHITE)
                .drawText(850, 560, "+1", new Font("Agency FB", Font.BOLD, 25), Color.WHITE)
                .drawText(670, 590, "*You lose your combo if you miss your shot", new Font("Agency FB", Font.BOLD, 16), Color.WHITE)
                .drawImage(650, 470, ufo.getimg(1))
                .drawImage(660, 520, alien);

        if (dev) {
            Console.getInstance()
                    .drawText(20, 590, "Dev mode enabled", new Font("Agency FB", Font.BOLD, 16), Color.MAGENTA);
        } else {
            Console.getInstance()
                    .drawText(20, 590, "F3 to toggle Dev mode", new Font("Agency FB", Font.BOLD, 16), Color.WHITE);
        }
    }

    public void init() {

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
            powerups[i] = new Powerup();
            powerups[i].destroyPowerup();
        }
        for (int i = 0; i < 10; i++) {
            bullets[i] = new Bullets();
            bullets[i].destroyBullets();
            blasters[i] = new Blaster();
            blasters[i].destroyBlaster();
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
        beam.move(blasters[0].getx(), blasters[0].gety(), timer);
        for (int i = 0; i < 5; i++) {
            lasers[i].move(-10);           //-13 pixel every frame;
            powerups[i].move(2);
        }
        for (int i = 0; i < 10; i++) {
            bullets[i].bulletMove(3);
            blasters[i].move(timer);
        }
        if ((int) timer % 10 == 0 && !ufo.onScreen()) {              //every 10s
            ufo.init();
        }

        ufoCollision();
        alienCollision();
        shieldCollision();
        spaceshipCollision();
        checkloseCombo();
        alienSpeed();
        alienShot();

        drawRunning();

    }

    public String stage() {
        Time();
        if (timer <= 7) {           //run for 2 sec 7-5=3
            drawStage();
            System.out.println(timer);
        } else if (stage < 3) {
            return "true";        //true when stage has shown for 2 sec
        } else if (stage == 3) {
            return "sans";
        }
        return "false";

    }

    public void retry() {
        stage = 3;

        initSans();
    }

    public void nextStage() {
        if (stage < 3) {
            stage++;
        }
        int shotNumber = ship.getShots();
        int life = ship.getLife();
        int score = ship.getScore();
        int combo = ship.getCombo();
        int beam = ship.getBlaster();

        ship.setScore(score);
        while (shotNumber > 1) {
            ship.addShots(1);
            shotNumber--;
        }
        while (combo > 1) {
            ship.addCombo(1);
            combo--;
        }
        while (beam > 1) {
            ship.addBlaster();
            beam--;
        }
        if (life > 3) {
            while (life - 3 >= 1) {      //-3 because init life is 3
                ship.addLife();
                life--;
            }
        }
        if (stage != 3) {
            init();
        } else if (stage == 3) {
            initSans();
        }
    }

    public void pause() {
        drawRunning();
        Console.getInstance()
                .drawRectangle(360, 105, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 150, "Resume", new Font("Agency FB", Font.BOLD, 40), Color.CYAN)
                .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 250, "Retry", new Font("Agency FB", Font.BOLD, 40), Color.CYAN)
                .drawRectangle(360, 305, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 350, "Main Menu", new Font("Agency FB", Font.BOLD, 40), Color.CYAN);
        if (dev) {
            Console.getInstance()
                    .drawRectangle(23, 32, 130, 170, Color.DARK_GRAY, 0)
                    .drawText(30, 50, "H - Toggle Hit box", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA)
                    .drawText(30, 70, "W - Win", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA)
                    .drawText(30, 90, "L - Lose", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA)
                    .drawText(30, 110, "N - Next Stage", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA)
                    .drawText(30, 130, "E - Kill ship", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA)
                    .drawText(30, 150, "A - Life + 1", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA)
                    .drawText(30, 170, "S - Max shot +1", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA)
                    .drawText(30, 190, "D - Ghaster Blaster +1", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA);
        }
    }

    public void win() {

        ufo.init();     //remove ufo from screen
        music.stopBGM();
        Console.getInstance()
                .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 250, "New Game", new Font("Agency FB", Font.BOLD, 40), Color.CYAN)
                .drawRectangle(360, 305, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 350, "Main Menu", new Font("Agency FB", Font.BOLD, 40), Color.CYAN)
                .drawText(400, 100, "You Win!", new Font("Agency FB", Font.BOLD, 60), Color.YELLOW)
                .drawText(350, 170, String.format("Your Score: " + ship.getScore()), new Font("Agency FB", Font.BOLD, 40), Color.WHITE);

    }

    public void lose() {
        music.stopBGM();
        drawRunning();
        Console.getInstance()
                .drawRectangle(360, 205, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 250, "Retry", new Font("Agency FB", Font.BOLD, 40), Color.CYAN)
                .drawRectangle(360, 305, 250, 60, Color.DARK_GRAY, 20)
                .drawText(380, 350, "Main Menu", new Font("Agency FB", Font.BOLD, 40), Color.CYAN)
                .drawText(370, 100, "You're Dead", new Font("Agency FB", Font.BOLD, 60), Color.RED)
                .drawText(350, 170, String.format("Your Score: " + ship.getScore()), new Font("Agency FB", Font.BOLD, 40), Color.RED);
    }

    public void drawRunning() {

        drawLife();
        drawLaser();
        drawShip();
        drawAlien();
        drawUFO();
        drawShield();
        drawBlaster();
        drawBullets();
        drawPowerup();
        drawBeam();
        drawUIBlaster();
        drawUIShots();

        Console.getInstance()
                .drawText(570, 590, "Score:", new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(630, 590, String.format("%09d", ship.getScore()), new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(430, 590, "Combo:", new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(495, 590, String.valueOf(ship.getCombo() + "X"), new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(750, 590, "HighScore:", new Font("Agency FB", Font.BOLD, 18), Color.WHITE)
                .drawText(860, 590, String.format("%09d", ship.getHighestScore()), new Font("Agency FB", Font.BOLD, 18), Color.WHITE);

        if (dev) {
            Console.getInstance().drawText(800, 20, "dev mode enabled, pause to see hotkeys", new Font("Agency FB", Font.PLAIN, 15), Color.MAGENTA);
            if (Hitbox) {
                drawHitbox();
            }
        }

    }

    public void drawStage() {
        if (stage != 3) {
            Console.getInstance()
                    .drawText(430, 280, String.valueOf("Stage" + stage), new Font("Agency FB", Font.BOLD, 50), Color.WHITE);
        } else {
            Console.getInstance()
                    .drawText(430, 280, String.valueOf("Stage ???"), new Font("Comic Sans MS", Font.BOLD, 50), Color.WHITE);
        }
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

    public void drawBlaster() {
        for (int j = 0; j < 10; j++) {
            Console.getInstance().drawImage(blasters[j].getx(), blasters[j].gety(), blasters[j].getimg(timer));
        }
    }

    public void drawBeam() {
        Console.getInstance().drawImage(beam.getx(), beam.gety(), beam.getimg(timer));
    }

    public void drawBullets() {
        for (int i = 0; i < 10; i++) {
            Console.getInstance().drawImage(bullets[i].getx(), bullets[i].gety(), bullets[i].getimg());
        }
    }

    public void drawPowerup() {
        for (int i = 0; i < 5; i++) {
            Console.getInstance().drawImage(powerups[i].getx(), powerups[i].gety(), powerups[i].getimg((int) (timer * 4) % 3));
        }
    }

    public void drawLife() {
        if (ship.getLife() <= 5) {
            for (int i = 0; i < ship.getLife() - 1; i++) {
                Console.getInstance().drawImage(10 + i * 30, 570, ship.getimg2());
            }
        } else if (ship.getLife() > 5) {
            Console.getInstance()
                    .drawImage(10, 570, ship.getimg2())
                    .drawText(40, 590, " x " + String.valueOf(ship.getLife() - 1), new Font("Agency FB", Font.BOLD, 20), Color.WHITE);
        }
    }

    public void drawUIBlaster() {

        Console.getInstance()
                .drawImage(150, 568, pwr3)
                .drawText(180, 590, " x " + ship.getBlaster(), new Font("Agency FB", Font.BOLD, 20), Color.WHITE);

    }

    public void drawUIShots() {
        Console.getInstance()
                .drawImage(250, 568, pwr1)
                .drawText(280, 590, " x " + ship.getShots(), new Font("Agency FB", Font.BOLD, 20), Color.WHITE);
    }

    public void drawBox(int x, int y, int w, int h) {

        Console.getInstance()
                .drawRectangle(x, y, w, 1, Color.MAGENTA)
                .drawRectangle(x, y, 1, h, Color.MAGENTA)
                .drawRectangle(x, y + h, w, 1, Color.MAGENTA)
                .drawRectangle(x + w, y, 1, h, Color.MAGENTA);
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
        for (int i = 0; i < 5; i++) {
            if (powerups[i].onScreen()) {
                drawBox(powerups[i].getHbx(), powerups[i].getHby(), powerups[i].getWidth(), powerups[i].getHeight());
            }
        }
        drawBox(ufo.getHbx(), ufo.getHby(), ufo.getWidth(), ufo.getHeight());
        drawBox(ship.getHbx(), ship.getHby(), ship.getWidth(), ship.getHeight());
        drawBox(beam.getHbx(), beam.getHby(), beam.getWidth(), beam.getHeight());
        for (int i = 0; i < 20; i++) {
            if (shield.getAlive(i)) {
                drawBox(shield.getx(i), shield.gety(i), shield.getWidth(), shield.getHeight());
            }
        }
    }

    public void toggleHitbox() {
        Hitbox = !Hitbox;
    }

    public void toggleDev() {
        dev = !dev;
    }

    public boolean getDev() {
        return dev;
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

    public void blaster() {
        if (ship.getBlaster() > 0 && !blasters[0].onScreen()) {
            blasters[0].init(ship.getx(), ship.gety(), timer);
            beam.init(ship.getx(), ship.gety(), timer);
            ship.loseBlaster();
            music.playBeam();
        }
    }

    public void alienShot() {
        int frequency = 100;
        if (stage == 3) {
            frequency = 5;
        }

        if (r.nextInt(frequency) == 1) {
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
                for (int j = 0; j < 5; j++) {
                    if (!powerups[j].onScreen()) {
                        powerups[j].init(ufo.getx(), ufo.gety());
                        break;                  //make sure only one powerup spawn every time.
                    }
                }
                music.playInvaderKilled();
                lasers[i].destroyLaser();
                ship.addScore(10000);
                ufo.kill();
                if (ship.score > ship.highestScore) {
                    ship.setHighestScore(ship.score);
                }
                ship.addCombo(10);
            }
            if (ufo.collision(beam.getHbx(), beam.getHby(), beam.getWidth(), beam.getHeight())) {
                for (int j = 0; j < 5; j++) {
                    if (!powerups[j].onScreen()) {
                        powerups[j].init(ufo.getx(), ufo.gety());
                        break;                  //make sure only one powerup spawn every time.
                    }
                }
                music.playInvaderKilled();
                ship.addScore(10000);
                ufo.kill();
                if (ship.score > ship.highestScore) {
                    ship.setHighestScore(ship.score);
                }
                ship.addCombo(10);
            }
        }
    }

    public void kappaCollision() {
        for (int k = 0; k < 10; k++) {
            for (int i = 0; i < 5; i++) {
                if (kappa[k].collision(lasers[i].getHbx(), lasers[i].getHby(), lasers[i].getWidth(), lasers[i].getHeight())) {
                    for (int j = 0; j < 5; j++) {
                        if (!powerups[j].onScreen()) {
                            powerups[j].init(kappa[k].getx(), kappa[k].gety());
                            break;                  //make sure only one powerup spawn every time.
                        }
                    }
                    music.playInvaderKilled();
                    lasers[i].destroyLaser();
                    ship.addScore(10000);
                    ufo.kill();
                    if (ship.score > ship.highestScore) {
                        ship.setHighestScore(ship.score);
                    }
                    ship.addCombo(10);
                }
                if (kappa[k].collision(beam.getHbx(), beam.getHby(), beam.getWidth(), beam.getHeight())) {
                    for (int j = 0; j < 5; j++) {
                        if (!powerups[j].onScreen()) {
                            powerups[j].init(kappa[k].getx(), kappa[k].gety());
                            break;                  //make sure only one powerup spawn every time.
                        }
                    }
                    music.playInvaderKilled();
                    ship.addScore(10000);
                    kappa[k].kill();
                    if (ship.score > ship.highestScore) {
                        ship.setHighestScore(ship.score);
                    }
                    ship.addCombo(10);
                }
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
                if (aliens[j].collision(beam.getHbx(), beam.getHby(), beam.getWidth(), beam.getHeight())) {
                    music.playInvaderKilled();
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
        for (int i = 0; i < 5; i++) {
            if (ship.collision(powerups[i].getHbx(), powerups[i].getHby(), powerups[i].getWidth(), powerups[i].getHeight())) {
                music.playPowerup();
                if (((int) (timer * 4) % 3) == 0) {
                    ship.addBlaster();
                } else if (((int) (timer * 4) % 3) == 1) {
                    ship.addShots(1);
                } else if (((int) (timer * 4) % 3) == 2) {
                    ship.addLife();
                }
                powerups[i].destroyPowerup();
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
                speed = 3;
            } else if (alienLeft >= 15 && alienLeft < 30) {
                speed = 4;
            } else if (alienLeft >= 5 && alienLeft < 15) {
                speed = 5;
            } else if (alienLeft >= 0 && alienLeft < 5) {
                speed = 6;
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

    public void addLife() {
        ship.addLife();
    }

    public String checkWin() {
        if (stage == 3) {
            return "NEXTSTAGE";
        } else if (stage == 2) {
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

    public void initSans() {
        alienLeft = 56;
        int count = 0;
        timer = 4;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 5; j++) {
                aliens[count] = new Alien();
                aliens[count].init(6 + 60 * i, 100 + 50 * j);
                aliens[count].setSpeed(speed);
                count++;
            }
        }

        ship.init();
        music.stopBGM();
        music.playBGM();
        for (int i = 0; i < 5; i++) {
            lasers[i] = new Laser();
            lasers[i].destroyLaser();
            powerups[i] = new Powerup();
            powerups[i].destroyPowerup();
        }
        for (int i = 0; i < 10; i++) {
            bullets[i] = new Bullets();
            bullets[i].destroyBullets();
            blasters[i] = new Blaster();
            blasters[i].destroyBlaster();
            kappa[i] = new UFO();
            kappa[i].init(-100 - 40 * i, 100);
        }
        shield.initshield();
        ufo.kill();

    }

    public void sans() {
        Time();
        if (frame % 2 == 0) {
            for (int i = 0; i < 55; i++) {
                aliens[i].sansMove();
            }
        }

        beam.move(blasters[0].getx(), blasters[0].gety(), timer);
        for (int i = 0; i < 5; i++) {
            lasers[i].move(-10);           //-13 pixel every frame;
            powerups[i].move(2);
        }
        for (int i = 0; i < 10; i++) {
            bullets[i].bulletMove(3);
            blasters[i].move(timer);
            kappa[i].sansMove();
        }

        kappaCollision();
        alienCollision();
        shieldCollision();
        spaceshipCollision();
        checkloseCombo();
        alienSpeed();
        drawShip();

        drawRunning();
        for (int j = 0; j < 10; j++) {
            Console.getInstance().drawImage(kappa[j].getx(), kappa[j].gety(), kappa[j].getimg(i / 10));
        }

        if (timer == 10.0) {
            music.playSansBeam();
        }
        if (timer < 11.0) {
            alienShot();
        }

        if (timer == 11.1) {
            for (int i = 0; i < 10; i++) {
                kappa[i].kill();
                bullets[i].destroyBullets();
            }
            for (int i = 0; i < 55; i++) {
                aliens[i].kill();
            }
            for (int i = 0; i < 20; i++) {
                shield.kill(i);
            }

        }
        if (timer > 11.1 && timer < 12) {
            Console.getInstance().drawRectangle(-100, 30, 1300, 500, Color.WHITE);
            music.stopBGM();
        }
        if (timer >= 12 && timer < 12.2) {
            Console.getInstance().drawRectangle(-100, 100, 1300, 400, Color.WHITE);
        }
        if (timer >= 12.2 && timer < 12.3) {
            Console.getInstance().drawRectangle(-100, 150, 1300, 300, Color.WHITE);
        }
        if (timer >= 12.3 && timer < 12.4) {
            Console.getInstance().drawRectangle(-100, 180, 1300, 200, Color.WHITE);
        }
        if (timer >= 12.4 && timer < 12.5) {
            Console.getInstance().drawRectangle(-100, 210, 1300, 100, Color.WHITE);
        }

    }
}
