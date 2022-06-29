import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class HonestHangman implements Hangman{
	private int guesses;
	private ArrayList<Boolean> guessed;
	private ArrayList<String> letters;
	private ArrayList<String> dict;
	private String word;
	private ArrayList<String> correct;
	
	
	public HonestHangman(String file){
		//array of all letters
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		this.letters = new ArrayList<String>();
		var x = alphabet.split("");
		for(int i = 0; i<26; i++) {
			this.letters.add(x[i]);
		}
		
		//number of guesses remaining
		this.guesses = 6;
		
		//which letters have been guessed
		this.guessed = new ArrayList<Boolean>();
		for(int i = 0; i<26; i++) {
			this.guessed.add(false);
		}
		
		//pick a word
		Path fileName = Path.of(file);
		String y;
		this.dict = new ArrayList<String>();
		try {
			y = Files.readString(fileName, StandardCharsets.ISO_8859_1);
			var arr = y.split("\r\n");
			int num = (int) Math.ceil(Math.random()*arr.length);
			this.word = arr[num];
			for(int i = 0; i<arr.length;i++) {
				this.dict.add(arr[i]);
			}
			//System.out.println(this.word);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//pull correct letters
		this.correct = new ArrayList<String>();
		var z = this.word.split("");
		for(int i = 0; i<z.length; i++) {
			this.correct.add(z[i]);
		}
	}

	@Override
	public String getDisplayWord() {
		String output = "";
		if (gameOver()) {
			return this.word;
		}
		
		var x = this.word.split("");
		for(int i = 0 ; i < x.length; i++) {
			if (this.guessed.get(this.letters.indexOf(x[i]))) {
				output = output + x[i];
			}else {
				output = output + "-";
			}
		}
		return output;
	}

	@Override
	public String makeGuess(char let) {
		String letter = String.valueOf(let);
		if(this.guessed.get(this.letters.indexOf(letter))) {
			//error already guessed letter
			return "You have already guessed " + letter;
		}else {
			this.guessed.set(this.letters.indexOf(letter),true);
			if(this.correct.contains(letter)) {
				return "Correct! There are " + Collections.frequency(this.correct,letter) + " " + letter.toUpperCase() + "s!";
			}else {
				this.guesses--;
				return "Incorrect! You have " + this.guesses + " guesses remaining!";
			}
		}
	}

	@Override
	public boolean gameOver() {
		//if out of guesses
		if (this.guesses < 1) {
			return true;
		}
		
		//for each letter in the word
		for(int i = 0; i < this.correct.size(); i++) {
			if(!this.guessed.get(this.letters.indexOf(this.correct.get(i)))) {
				//if not guessed
				return false;
			}
		}
		
		//each letter must be guessed
		return true;
	}

	@Override
	public int guessesRemaining() {
		return this.guesses;
	}

}
