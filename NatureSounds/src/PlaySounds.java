import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlaySounds {

	public static Clip clip = null;

	public static void main(String[] args) throws LineUnavailableException,
			UnsupportedAudioFileException, IOException {
		final LinkedList<File> soundFiles = (LinkedList<File>) loadSounds();

		JPanel panel = new JPanel();
		JButton soundButton = new JButton("New Sound");
		panel.add(soundButton);
		final JLabel jlabel = new JLabel("");
		panel.add(jlabel);
		soundButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if (clip != null) {
					clip.close();
				}

				File sound = soundFiles.remove();
				soundFiles.add(sound);

				try {
					clip = playSound(sound);
					jlabel.setText(sound.getName());
				} catch (LineUnavailableException
						| UnsupportedAudioFileException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		JFrame frame = new JFrame("Click on the button to hear new sounds");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(500, 80);
		frame.setVisible(true);

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
