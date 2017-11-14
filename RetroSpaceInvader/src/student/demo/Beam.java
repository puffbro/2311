package student.demo;

import game.v2.Console;
import java.awt.Image;

public class Beam {

    private Image img1 = Console.loadImage("/student/demo/img/Beam_1.png");
    private Image img2 = Console.loadImage("/student/demo/img/Beam_2.png");
    private Image img3 = Console.loadImage("/student/demo/img/Beam_3.png");
    private Image img4 = Console.loadImage("/student/demo/img/Beam_4.png");
    private Image img5 = Console.loadImage("/student/demo/img/Beam_5.png");
    private Image img6 = Console.loadImage("/student/demo/img/Beam_6.png");
    private Image[] images = {img1, img2, img3, img4, img5, img6};
    private int x = 0;
    private int y = 0;
    private int velx = 0;
    private int vely = 0;
    private int hbx;
    private int hby;
    private int width = 43;
    private int height = 800;
    private double timePassed;
    private double timeInit;

    public void init(int a, int b, double time) {
        x = a + 20;
        y = b - 800;
        timeInit = time;
    }

    public void move(double timer) {
        timePassed = timer - timeInit;        

        if (timePassed < 0.8) {
            x = x-100;
        } else if (timePassed < 0.9) {
            x = x+100;
        }

        if (timePassed > 1.5) {
            x = -500;            
        }
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

        timePassed = timer - timeInit;

        if (timePassed < 0.2) {
            return img1;
        } else if (timePassed < 0.4) {
            return img2;
        } else if (timePassed < 0.9) {
            return img3;
        } else if (timePassed < 1.1) {
            return img4;
        } else if (timePassed < 1.3) {
            return img5;
        }
        return img6;
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
