/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.demo;

import game.v2.Console;
import game.v2.Game;
import java.awt.Image;
import static java.lang.System.console;

/**
 *
 * @author wtc94
 */
public class Shield {

    private Image red = Console.loadImage("/student/demo/img/red.png");
    private Image green = Console.loadImage("/student/demo/img/green.png");
    private Image[] images = {red, red, red, red, green, green, green, green, red, red, red, red, green, green, green, green, red, red, red, red};
    private int x[] = new int[20];
    private int y[] = new int[20];
    private int size = 20;
    private int height = 35;
    private int width = 40;
    private boolean[] alive = new boolean[20];

    public void initshield() {
        x[0] = 120;
        y[0] = 470;
        alive[0] = true;
        for (int i = 1; i < size; i++) {
            alive[i] = true;
            if (x[i - 1] < 720) {
                x[i] = x[i - 1] + 200;
            } else if (x[i - 1] >= 720) {
                x[i] = 120;
            }
            if (i <= 3) {
                y[i] = 470;
            } else if (i < 8) {
                y[i] = 480;
            } else if (i < 12) {
                y[i] = 490;
            } else if (i < 16) {
                y[i] = 500;
            } else if (i < 20) {
                y[i] = 510;
            }
        }
    }

public boolean collision(int a, int b) {

        for (int i = 0; i < 20; i++) {
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



    public Image getimg(int a) {

        return images[a];

    }
}
