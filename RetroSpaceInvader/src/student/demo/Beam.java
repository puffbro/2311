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
    private Image blank = Console.loadImage("/student/demo/img/null.png");
    private Image[] images = {img1, img2, img3, img4, img5, img6};
    private int x = -500;
    private int y = 0;
    private double delay = 0.6;            //Beam appears after delay. (For blaster animation to play)
    private int hbx = -500;
    private int hby;
    private int width = 39;
    private int height = 800;
    private double timePassed;
    private double timeInit;

    public void init(int a, int b, double time) {
        x = a + 4;
        y = b - 860;
        timeInit = time;
    }

    public void move(int a, int b, double timer) {
        timePassed = timer - timeInit - delay;

        x = a + 2;
        y = b - 790;
        hby = y;
        if (timePassed < 0) {
            hbx = -500;           //not yet fired

        } else if (timePassed < 0.2) {
            hbx = x+12;
            width = 15;
            height = 800;
        } else if (timePassed < 0.6) {
            hbx = x;
            width = 39;
            height = 800;
        } else if (timePassed < 0.9) {
            hbx = -500;
        }
        if (timePassed > 1.1) {
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

        timePassed = timer - timeInit - delay;
        if (timePassed < 0) {
            return blank;
        } else if (timePassed < 0.1) {
            return img1;
        } else if (timePassed < 0.2) {
            return img2;
        } else if (timePassed < 0.5) {
            return img3;
        } else if (timePassed < 0.6) {
            return img4;
        } else if (timePassed < 0.7) {
            return img5;
        } else if (timePassed < 0.8) {
            return img6;
        }
        return blank;
    }

    public void destroyBeam() {
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
