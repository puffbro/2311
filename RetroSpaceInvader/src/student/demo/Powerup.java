package student.demo;

import game.v2.Console;
import java.awt.Image;

public class Powerup {

    private Image img = Console.loadImage("/student/demo/img/powerup.png");
    private Image img1 = Console.loadImage("/student/demo/img/powerup_1.png");
    private Image img2 = Console.loadImage("/student/demo/img/powerup_2.png");
    private Image img3 = Console.loadImage("/student/demo/img/powerup_3.png");
    private Image[] images = {img1, img2, img3};
    private int x = 0;
    private int y = -100;
    private int hbx;
    private int hby;
    private int width = 30;
    private int height = 30;

    public void init(int a, int b) {
        x = a + 10;
        y = b + 50;
    }

    public void move(int a) {
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

    public Image getimg(int a) {
        return images[a];
    }

    public void destroyPowerup() {
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
