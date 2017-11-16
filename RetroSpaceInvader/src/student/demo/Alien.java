package student.demo;

import game.v2.Console;
import java.awt.Image;

public class Alien {

    private Image img1 = Console.loadImage("/student/demo/img/invader64_1.png");
    private Image img2 = Console.loadImage("/student/demo/img/invader64_2.png");
    private Image[] images = {img1, img2};
    private int x;
    private int y;
    private int velx = 0;
    private int vely = 10;
    private int initx;          //The x cood of its initial spawn point.
    private int inity;
    private boolean right = true;
    private int size = 55;
    private int height = 33;
    private int width = 44;
    private int speed = 1;
    private boolean alive;
    private int hbx;        //hitbox X
    private int hby;        //hitbox Y

    public void init(int x, int y) {
        alive = true;
        this.x = x;
        initx = this.x;
        this.y = y;
        inity = y;
        initx = x;
    }

    public void move() {

        if (right) {
            x += speed;
        }
        if (right == false) {
            x -= speed;
        }
        if (x < initx) {
            right = true;
            y += 25;
        }

        if (x >= initx + 334) {
            right = false;
            y += 25;
        }
    }

    public void sansMove() {
        if (y > inity) {
            vely--;
        } else if (y < inity) {
            vely++;
        }

        velx = 1;

        x += velx;
        y += vely;
    }

    public boolean collision(int a, int b, int w, int h) {

        hbx = x + 10; //Top Left x cood of alien hitbox
        hby = y + 15; //Top Left y cood of alien hitbox

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

    public void setSpeed(int x) {
        speed = x;
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

    public boolean reachBottom() {
        if (alive && y >= 500) {
            return true;
        }

        return false;

    }

    public boolean Dead() {

        if (alive) {
            return false;
        } else {
            return true;
        }
    }

    public void kill() {

        alive = false;

    }

    public Image getimg(int a) {
        return images[a];
    }
}
