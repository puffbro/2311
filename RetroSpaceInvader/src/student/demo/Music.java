package student.demo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Music {

    private AudioClip BGMusic;              // audio clip for background music
    private AudioClip move;                 // audio clip for spaceship movement
    private AudioClip explosion;            // audio clip for spaceship explosion
    private AudioClip invaderkilled;        // audio clip for kill invader
    private AudioClip shoot;                // audio clip for spaceship shoot
    private AudioClip powerup;
    private AudioClip ufo;
    private boolean bgmIsPlaying;           // boolean represents the BGM start or not 
    private boolean bgmOn = true;           // true for bgm is ON
    private boolean effectSoundOn = true;   // true for effect sound is ON

    Music() {
        prepareSound();
    }

    private void prepareSound() {
        BGMusic = createAudioClip("/student/demo/music/BGM.wav");
        move = createAudioClip("/student/demo/music/move.wav");
        explosion = createAudioClip("/student/demo/music/explosion.wav");
        invaderkilled = createAudioClip("/student/demo/music/invaderkilled.wav");
        shoot = createAudioClip("/student/demo/music/shoot.wav");
        powerup = createAudioClip("/student/demo/music/powerup.wav");
        ufo = createAudioClip("/student/demo/music/ufo.wav");

    }

    private AudioClip createAudioClip(String path) {
        AudioClip audio = null;

        try {
            URL clipURL = Music.class.getResource(path);  // get the full path of audio file
            if (clipURL != null) {
                audio = Applet.newAudioClip(clipURL);       // initialize a AudioClip
            }

        } catch (NullPointerException e) {      // print error message if the path provide in invalid
            System.err.println("Couldn't find file: " + Music.class.getResource("").toString() + path);

        } finally {
            return audio;
        }
    }

    public void playBGM() {
        try {
            if (!bgmIsPlaying && bgmOn) {   // start playing BGM if it isn't playing
                BGMusic.loop();             // loop the BGM
                bgmIsPlaying = true;        // set true represent the BGM is started to play
            }

        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (BGMusic)");
        }
    }

    public void stopBGM() {
        try {
            if (bgmIsPlaying && bgmOn) {   // stop playing BGM if it is playing
                BGMusic.stop();             // loop the BGM
                bgmIsPlaying = false;        // set false represent the BGM is started to stop
            }

        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (BGMusic)");
        }
    }

    public void playMove() {
        try {
            if (effectSoundOn) {
                move.play();
            }
        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (move)");
        }
    }

    public void playShoot() {
        try {
            if (effectSoundOn) {
                shoot.play();
            }
        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (move)");
        }
    }

    public void playExplosion() {
        try {
            if (effectSoundOn) {
                explosion.play();
            }
        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (move)");
        }
    }

    public void playInvaderKilled() {
        try {
            if (effectSoundOn) {
                invaderkilled.play();
            }
        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (move)");
        }
    }

    public void playPowerup() {
        try {
            if (effectSoundOn) {
                powerup.play();
            }
        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (powerup)");
        }
    }

    public void playUFO() {
        try {
            if (effectSoundOn) {
                ufo.play();
            }
        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (ufo)");
        }
    }
        public void stopUFO() {
        try {
            ufo.stop();
        } catch (NullPointerException e) {      // print error message if the audio file not found
            System.err.println("Audio Clip Not Found - (ufo)");
        }
    }
}
