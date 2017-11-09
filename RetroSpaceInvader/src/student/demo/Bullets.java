package student.demo;

import game.v2.Console;
import java.awt.Image;

public class Bullets {

    private Image img = Console.loadImage("/student/demo/img/bullet.png");
    private int x=0;
    private int y=-100;
    private int hbx;
    private int hby;
    private int width = 2;
    private int height = 5;

    public void bulletInit(int a, int b) {
        x = a+32;
        y = b+50;
    }

    public void bulletMove(int a) {
        y = y + a;
        hbx = x;
        hby = y;
    }

    public boolean onScreen() {
        if (y < 600) {
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

    public void destroyBullets() {
        y = 1000;
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
