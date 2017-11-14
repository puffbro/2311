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
    private int velx = 0;
    private int vely = 0;
    private int hbx;
    private int hby;
    private int width = 43;
    private int height = 57;
    private double timePassed;
    private double timeInit;

    public void init(int a, int b, double time) {
        x = a + 2;
        y = b + 50;
        vely = -15;
        timeInit = time;
    }

    public void move(double timer) {
        timePassed = timer - timeInit;

        y = y + vely;
        hbx = x;
        hby = y;
        if (vely != 0) {
            vely++;
        }

        if (timePassed > 1.3) {
            vely++;
        } else if (timePassed > 0.7) {
            vely = 1;
        }

        if (y > 700) {
            vely =0;            
            x = -1000;
        }
    }

    public boolean onScreen() {
        if (x > -100) {
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

        timePassed = timer - timeInit;

        if (timePassed < 0.4) {
            return img1;
        } else if (timePassed < 0.5) {
            return img2;
        } else if (timePassed < 0.6) {
            return img3;
        } else if (timePassed < 0.7) {
            return img4;
        } else if (timePassed < 1.1) {
            return img5;
        }
        return img6;
    }

    public void destroyBlaster() {
        x = -1000;
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
