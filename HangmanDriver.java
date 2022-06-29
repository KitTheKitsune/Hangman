import java.util.Scanner;
/**
 * A simple text interface to play the game Hangman
 * @author cmb79981
 *
 */
public class HangmanDriver {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		boolean play = true;
		while (play) {
			//Hangman game = new HonestHangman("dictionary.txt");
			Hangman game = new DishonestHangman("dictionary.txt");
			System.out.println("Welcome to the game of Hangman! The computer has picked its word,");
			System.out.println("please begin guessing.");
			System.out.println();
			System.out.println("The word has "+game.getDisplayWord().length()+" letters");
			while (!game.gameOver()) {
				System.out.println();
				System.out.println("What is your next guess?");
				char next = in.next().charAt(0);
				System.out.println();
				String response = game.makeGuess(next);
				System.out.println(response);
				System.out.println("Here is the current state of the word: " + game.getDisplayWord());
			}
			System.out.println();
			System.out.println("Game Over! The word was: "+game.getDisplayWord());
			System.out.println("Would you like to play again? y/yes or n/no?");
			String again = in.next();
			if(again.toLowerCase().charAt(0)=='n') {
				play = false;
			}
		}
	}
}
