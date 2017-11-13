package student.demo;

import game.v2.Console;
import java.awt.Image;

public class Blaster {

    private Image img1 = Console.loadImage("/student/demo/img/blaster_1.png");
    private Image img2 = Console.loadImage("/student/demo/img/blaster_2.png");
    private Image img3 = Console.loadImage("/student/demo/img/blaster_3.png");
    private Image img4 = Console.loadImage("/student/demo/img/blaster_4.png");
    private Image img5 = Console.loadImage("/student/demo/img/blaster_5.png");
    private Image img6 = Console.loadImage("/student/demo/img/blaster_6.png");
    private Image[] images = {img1, img2, img3, img4, img5, img6};
    private int x = 0;
    private int y = 0;
    private int hbx;
    private int hby;
    private int width = 43;
    private int height = 57;
    private double timePassed;
    private double timeInit;

    public void init(int a, int b, double time) {
        x = a - 8;
        y = b;
        timeInit = time;
    }

    public void move(int a) {
        y = y + a;
        hbx = x;
        hby = y;
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

    public Image getimg(double timer) {
        
        if (timer - timeInit < 0.5) {
            return img1;
        } else if (timer - timeInit < 0.5) {
            return img2;
        } else if (timer - timeInit < 0.6) {
            return img3;
        } else if (timer - timeInit < 0.7) {
            return img4;
        } else if (timer - timeInit < 1.2) {
            return img5;
        } else if (timer - timeInit < 1.5) {
            return img6;
        }
        return img1;
    }

    public void destroyBlaster() {
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
