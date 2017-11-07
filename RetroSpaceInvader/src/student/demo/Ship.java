package student.demo;

import game.v2.Console;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ship {

    private Image img = Console.loadImage("/student/demo/img/spaceship_1.png");
    private Image img2 = Console.loadImage("/student/demo/img/shiplife.png");
    private int x;
    private int y;
    private int life;
    public int score;
    public int highestScore;
    private int combo;
    private int shots;

    public void init() {
        x = 450;
        y = 525;
        score = 0;
        combo = 1;
        life = 3;
        shots = 1;
    }

    public int getShots() {
        return shots;
    }

    public void addShots(int a) {
        if (shots < 5) {
            shots += a;
        }

    }

    public void loseShots() {
        shots = 1;
    }

    public void move(char a) {
        if (a == 'R' && x < 950) {
            x += 10;
        }
        if (a == 'L' && x > 0) {
            x -= 10;
        }
    }

    public void addScore(int a) {
        score += a * combo;
    }

    public void addCombo(int a) {
        combo += a;
    }

    public void loseCombo() {
        combo = 1;
    }

    public void loseLife() {
        life -= 1;
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

    public Image getimg2() {
        return img2;
    }

    public int getScore() {
        return score;
    }

    public int getCombo() {
        return combo;
    }

    public int getLife() {
        return life;
    }

    public void setHighestScore(int score) {
        highestScore = score;
        FileOutputStream fop = null;
        File file;
        String content = score + "";
        FileWriter fw;
        try {
            file = new File("highestScore.txt");
            if (file.exists()) {
                file.delete();
            }
            fw = new FileWriter("highestScore.txt");
            fw.write(content);
            System.out.println("saved highestscore to file");
            fw.close();
        } catch (IOException ex) {
        }
    }

    public int getHighestScore() {
        File file = new File("highestScore.txt");
        if (!file.exists()) {
            System.out.println("highestscore file doesnt exist");
            setHighestScore(0);
        } else {
            try {
                FileReader fr = new FileReader("highestScore.txt");
                BufferedReader br = new BufferedReader(fr);
                while (br.ready()) {
                    highestScore = (Integer.parseInt(br.readLine()));
                }
                fr.close();
            } catch (FileNotFoundException ex) {
                System.out.println("read failed");
            } catch (IOException ex) {
            }
        }
        return highestScore;
    }

}
