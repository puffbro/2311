package student.demo;

import game.v2.Console;
import game.v2.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.Locale;
import javax.swing.JOptionPane;


public class Alien {

    private Image img1 = Console.loadImage("/student/demo/img/invader64_1.png");
    private Image img2 = Console.loadImage("/student/demo/img/invader64_2.png");
    private Image[] images = {img1, img2};
    private int[] x = new int[55];
    private int[] y = new int[55];
    private boolean right = true;
    private int size = 55;
    private int height = 35;
    private int width = 40;
    private int speed = 1;
    private boolean[] alive = new boolean[55];

    public static void main(String[] args) {
        System.out.println("test");
        Alien test = new Alien();
        test.init();
        for (int i = 0; i < 55; i++) {
            System.out.println(i + "=" + test.x[i] + "," + test.y[i]);
        }

    }

    public void init() {
        x[0] = 6;
        y[0] = 30;
        alive[0] = true;
        for (int i = 1; i < size; i++) {
            alive[i] = true;
            if (x[i - 1] < 606) {
                x[i] = x[i - 1] + 60;
            } else {
                x[i] = 6;
            }
            if (i <= 10) {
                y[i] = 30;
            } else if (i < 22) {
                y[i] = 80;
            } else if (i < 33) {
                y[i] = 130;
            } else if (i < 44) {
                y[i] = 180;
            } else if (i < 55) {
                y[i] = 230;
            }
        }
    }

    public void move() {
        int alienLeft = 0;

        for (int i = 0; i < 55; i++) {
            if (alive[i]) {
                alienLeft += 1;
            }
        }

        if (alienLeft > 25) {
            speed = 1;
        } else if (alienLeft > 15) {
            speed = 2;
        } else if (alienLeft > 5) {
            speed = 3;
        } else if (alienLeft > 0) {
            speed = 4;
        }

        if (right) {
            for (int i = 0; i < size; i++) {
                x[i] += speed;
            }
        }
        if (right == false) {
            for (int i = 0; i < size; i++) {
                x[i] -= speed;
            }
        }
        if (x[0] <= 5) {
            right = true;
            for (int i = 0; i < size; i++) {
                y[i] += 25;
            }
        }

        if (x[0] >= 340) {
            right = false;

            for (int i = 0; i < size; i++) {
                y[i] += 25;
            }
        }
    }

    public boolean collision(int a, int b) {

        for (int i = 0; i < 55; i++) {
            int AL = x[i] + 10; //Left cood of alien hitbox
            int AT = y[i] + 15; //Top cood of alien hitbox
            int LL = a + 25;    //Left cood of laser hitbox
            int LT = b + 20;    //Top cood of Laser hitbox

            if (LL - AL <= width && LL - AL >= 0 && LT - AT <= height && LT - AT >= 0 && alive[i] == true) {
                alive[i] = false;
                return true;
            }
        }
        return false;
    }

    public int getx(int a) {
        return x[a];
    }

    public int gety(int a) {
        return y[a];
    }

    public boolean getAlive(int a) {
        return alive[a];
    }

    public boolean reachBottom() {
        for (int i = 0; i < 55; i++) {
            if (alive[i] && y[i] >= 500) {
                return true;
            }
        }
        return false;

    }

    public boolean allDead() {
        for (int i = 0; i < 55; i++) {
            if (alive[i]) {
                return false;
            }
        }
        return true;
    }
    
    public void killAll(){
        for(int i = 0;i<55;i++){
            alive[i]=false;
        }
    }

    public Image getimg(int a) {
        return images[a];
    }
}
