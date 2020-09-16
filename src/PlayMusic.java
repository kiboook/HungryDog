import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlayMusic {
	File soundFile = null;
	AudioInputStream audioIn = null;
	Clip clip;

	public void startMusic() { // 배경음악 받아와서 반복재생하기
		try {
			soundFile = new File("Music/playMusic.wav");
			audioIn = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start(); // 음악 재생
			clip.loop(Clip.LOOP_CONTINUOUSLY); // 무한반복
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void stopMusic() { // 음악 멈추기
		clip.stop();
	}

	public void start() { // 음악 재생하기
		clip.start();
	}

	public void moveMusic() { // 움직임 효과음 받아와서 재생하기
		try {
			soundFile = new File("Music/Bark.wav");
			audioIn = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}