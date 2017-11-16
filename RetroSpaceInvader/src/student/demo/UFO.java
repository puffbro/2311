package student.demo;

import game.v2.Console;
import java.awt.Image;

public class UFO {

    private Image img1 = Console.loadImage("/student/demo/img/kappa_1.png");
    private Image img2 = Console.loadImage("/student/demo/img/kappa_2.png");
    private Image[] images = {img1, img2};
    private int x;
    private int y;
    private int initx;
    private int inity;
    private int velx;
    private int vely = 8;
    private int hbx;
    private int hby;
    private int height = 60;
    private int width = 50;
    private boolean alive;

    public void init() {
        x = -200;
        y = 10;
        alive = true;
    }

    public void init(int a, int b) {
        x = a;
        y = b;
        initx = a;
        inity = b;
        alive = true;
    }

    public void move() {
        x += 3;
    }

    public void sansMove() {
        if (y > inity) {
            vely--;
        } else if (y < inity) {
            vely++;
        }

        velx = 3;

        x += velx;
        y += vely;
    }

    public boolean collision(int a, int b, int w, int h) {

        hbx = x + 15; //Top Left x cood of UFO hitbox
        hby = y; //Top Left y cood of UFO hitbox

        if (a - hbx <= width && a - hbx >= -w && b - hby <= height && b - hby >= -h && alive == true) {
            alive = false;
            return true;
        }

        return false;

    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getHbx() {
        return hbx;
    }

    public int getHby() {
        return hby;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getAlive() {
        return alive;
    }

    public boolean onScreen() {
        if (x > 900) {
            return false;
        }
        return true;
    }

    public Image getimg(int a) {
        return images[a];
    }

    public void kill() {
        x = 2000;
        velx = 0;
    }
}
