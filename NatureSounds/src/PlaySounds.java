import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySounds {

	public static void main(String[] args) throws LineUnavailableException,
			UnsupportedAudioFileException, IOException {
		LinkedList<File> soundFiles = (LinkedList<File>) loadSounds();

		Scanner scanner = new Scanner(System.in);
		System.out
				.println("Press enter to scroll through sounds. To quit, press \"q,\" then enter");

		File sound;
		Clip clip = null;
		String s = scanner.nextLine();
		while (!s.equals("q")) {
			sound = soundFiles.remove();
			soundFiles.add(sound);

			System.out.print(sound.getName());
			clip = playSound(sound);

			s = scanner.nextLine();

			clip.close();
		}
		System.out.println("Have a nice day :)");
		System.exit(0);

	}

	public static Queue<File> loadSounds() {
		File folder = new File(".");
		File[] listOfFiles = folder.listFiles();
		Queue<File> soundFiles = new LinkedList<File>();
		for (File f : listOfFiles) {
			String s = f.getName();
			if (s.length() >= 4
					&& s.substring(s.length() - 4, s.length()).equals(".wav")) {
				soundFiles.add(f);
			}
		}
		return soundFiles;
	}

	public static Clip playSound(File sound) throws LineUnavailableException,
			UnsupportedAudioFileException, IOException {

		AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.loop(Clip.LOOP_CONTINUOUSLY);

		return clip;
	}

}
