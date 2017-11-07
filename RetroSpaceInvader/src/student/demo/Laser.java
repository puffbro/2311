package student.demo;

import game.v2.Console;
import java.awt.Image;

public class Laser {

    private Image img = Console.loadImage("/student/demo/img/Laser.png");
    private int x = 0;
    private int y = -100;
    private int hbx;
    private int hby;
    private int width = 5;
    private int height = 10;

    public void init(int a, int b) {
        x = a - 8;
        y = b;
    }

    public void move(int a) {
        y = y + a;
        hbx = x + 30;
        hby = y + 17;
    }

    public boolean onScreen() {
        if (y > -50) {
            return true;
        } else {
            return false;
        }
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public Image getimg() {
        return img;
    }

    public void destroyLaser() {
        y = -1000;
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
}
