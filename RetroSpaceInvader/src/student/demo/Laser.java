package student.demo;

import game.v2.Console;
import java.awt.Image;


public class Laser {

    private Image img = Console.loadImage("/student/demo/img/Laser.png");
    private int x = 0;
    private int y = -100;

    public static void main(String[] args) {
    }

    public void init(int a, int b) {
        x = a - 8;
        y = b;
    }

    public void move() {
        y -= 13;
    }

    public boolean onScreen() {
        if (y > 0) {
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
    
    public void destroyLaser(){
        y = -1000;        
    }

}
