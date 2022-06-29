import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * DO NOT TOUCH
 * 
 * This class provides a simple interface for playing the game of Hangman.
 * @author cmb79981
 *
 */
public class HangmanGUI {

	private JFrame frame;
	private Hangman game;
	private JLabel message;
	private JLabel redacted;
	private JTextPane textPane;
	private JButton submit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HangmanGUI window = new HangmanGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HangmanGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		message = new JLabel("Current State of the Word");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setBounds(0, 0, 453, 92);
		frame.getContentPane().add(message);

		redacted = new JLabel("New label");
		redacted.setHorizontalAlignment(SwingConstants.CENTER);
		redacted.setBounds(132, 99, 183, 46);
		frame.getContentPane().add(redacted);

		textPane = new JTextPane();
		textPane.setBounds(101, 147, 247, 29);
		frame.getContentPane().add(textPane);

		submit = new JButton("Submit Guess");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.gameOver()) {
					startGame();
				} else {
					guess();
				}
			}
		});
		submit.setBounds(113, 200, 235, 46);
		frame.getContentPane().add(submit);
		startGame();
	}

	/**
	 * Starts a new game of hangman
	 */
	public void startGame() {
		//this.game = new HonestHangman("dictionary.txt");
		this.game = new DishonestHangman("dictionary.txt");
		message.setText("Welcome to the game of hangman.\nThe word to guess has " + game.getDisplayWord().length()
				+ " letters");
		redacted.setText(game.getDisplayWord());
		submit.setText("Submit Guess");
	}

	/**
	 * Gets the guess from the textbox and updates the interface in response
	 */
	public void guess() {
		String next = textPane.getText();
		textPane.setText("");
		if (next.length() > 0) {
			String mess = game.makeGuess(next.charAt(0));
			message.setText(mess);
			redacted.setText(game.getDisplayWord());
			if (game.gameOver()) {
				message.setText("Game Over! The word was "+game.getDisplayWord());
				redacted.setText(game.getDisplayWord());
				submit.setText("Play Again");

			} else {
				message.setText(mess);
				redacted.setText(game.getDisplayWord());
			}
		}

	}

}
