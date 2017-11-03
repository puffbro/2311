package student.demo;

import game.v2.Console;
import game.v2.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.Locale;
import javax.swing.JOptionPane;

public class UFO {

    private Image img1 = Console.loadImage("/student/demo/img/kappa_1.png");
    private Image img2 = Console.loadImage("/student/demo/img/kappa_2.png");
    private Image[] images = {img1, img2};
    private int x;
    private int y;
    private int height = 60;
    private int width = 50;
    private boolean alive;

    public static void main(String[] args) {

    }

    public void init() {
        x = -200;
        y = 10;
        alive = true;
    }


    public void move() {        
        x += 4;
    }

    public boolean collision(int a, int b) {


            int AL = x + 10; //Left cood of alien hitbox
            int AT = y + 15; //Top cood of alien hitbox
            int LL = a + 30;
            int LT = b + 20;

            if (LL - AL <= width && LL - AL >= 0 && LT - AT <= height && LT - AT >= 0 && alive == true) {
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

    public boolean getAlive() {
        return alive;
    }  
    public Image getimg(int a){
        return images[a];
    }
    public void kill(){
        x = 2000;
    }
}
