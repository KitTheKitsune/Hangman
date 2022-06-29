import java.util.Set;
/**
 * An interface representing the methods needed to be able to play the game Hangman.
 * @author cmb79981
 *
 */
public interface Hangman {
	
	/**
	 * If the game is not over, it returns the selected word with unguessed letters redacted with an -, if the game is over it returns
	 * the actual word
	 * @return the redacted word if the game is not over, the actual word if the game is over.
	 */
	public String getDisplayWord();
	
	/**
	 * Records the guess and returns a message describing the result. If the user correctly guesses, the redacted word
	 * is updated to reflect the added letters. If the user guess wrong, the user gets closer to losing. If the user
	 * guesses a letter they have already guessed, they should get an error message, but it should not count as a guess
	 * @param let the character being guessed
	 * @return a message with the result of the guess, including how many times the letter occurs if it is in the word
	 * and the number of wrong guesses the user could potential have. If the guess is correct, it will return a message such as
	 * "You guessed correctly! There are 2 e's". If the user guess is incorrect, it will return a message such as
	 * "You guessed wrong! There are no e's. You only have 2 more wrong guesses possible." If the user guesses a letter they
	 * have already guessed, it should return a message indicating that such as "You have already guessed e".
	 */
	public String makeGuess(char let);
	
	
	/**Determines if the game is over. The game is considered over when the 
	 * player has used all their wrong guesses or the player has correctly 
	 * guesses the word
	 * @return true if the game is over, false otherwise
	 */
	public boolean gameOver();
	
	/**
	 * Gets how many wrong guesses are remaining.
	 * @return the number of wrong guesses remaining
	 */
	public int guessesRemaining();
	

}
