import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DishonestHangman implements Hangman{
	private int guesses;
	private int length;
	private String current;
	private HashMap<Character,Boolean> guessed;
	private HashMap<String,ArrayList<String>> dict;

	public DishonestHangman(String file) {
		//array of all letters not guessed
		this.guessed = new HashMap<Character,Boolean>(26);
		for (int i = 10 ; i < 36 ; i++) {
			char x = Character.forDigit(i, 36);
			//System.out.println(x);
			this.guessed.put(x, false);
		}
		
		//pick a random length 4-12
		this.length = (int) ((Math.random() * 8) + 4);
		
		//set current to -s
		this.current = "";
		for (int i = 0; i < this.length; i++) {
			this.current = this.current + "-";
		}
		
		//pull the dictionary
		String y;
		try {
			y = Files.readString(Path.of("dictionary.txt"), StandardCharsets.ISO_8859_1);
			var arr = y.split("\r\n");
			
			
			//reduce to length
			this.dict = new HashMap<String,ArrayList<String>>();
			for (int i = 0; i < arr.length; i ++) {
				if (arr[i].length() == this.length) {
					String converted = convert(arr[i]);
					if (dict.containsKey(converted)) {
						this.dict.get(converted).add(arr[i]);
					}else {
						this.dict.put(converted, new ArrayList<String>());
						this.dict.get(converted).add(arr[i]);
					}
				}
			}	
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//number of guesses remaining
		this.guesses = 6;
		
	}

	
	public String getDisplayWord() {
		if(gameOver()) {
			return this.dict.get(this.current).get(0);
		}else {
			return this.current;
		}
	}
	
	public String convert(String word) {
		String[] arr = word.split("");
		String out = "";
		for (int i = 0; i < arr.length; i++) {
			try {
				char x = arr[i].charAt(0);
				if (this.guessed.get(x)) {
					out = out + arr[i];
				}else {
					out = out + "-";
				}
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
		return out;
	}

	
	public String makeGuess(char let) {
		// set letter to guessed
		if(this.guessed.get(let)) {
			return "Letter " + let + " has already been guessed.";
		}
		this.guessed.put(let, true);
		
		//reform dict
		ArrayList<String> temp = this.dict.get(this.current);
		HashMap<String,ArrayList<String>> tempMap = new HashMap<String,ArrayList<String>>();
		for(int i = 0; i < temp.size(); i++) {
			String x = convert(temp.get(i));
			if (tempMap.containsKey(x)) {
				tempMap.get(x).add(temp.get(i));
			}else {
				tempMap.put(x, new ArrayList<String>());
				tempMap.get(x).add(temp.get(i));
			}
		}
		this.dict = tempMap;
		
		//select largest set of possibilities
		Iterator<Map.Entry<String, ArrayList<String>>> it = this.dict.entrySet().iterator();
		int longest = 0;
		while(it.hasNext()) {
			Map.Entry<String, ArrayList<String>> curr = it.next();
			if(curr.getValue().size() > longest) {
				longest = curr.getValue().size();
				this.current = curr.getKey();
			}
		}
		
		
		int x = Collections.frequency(Arrays.asList(this.current.split("")),let+"");
		if(x == 0) {
			this.guesses--;
			return "Oh No! There aren't any " + let + "s! Only " + this.guesses + " remaining!";
		}
		this.guesses--;
		return "There are " + x  + "s in the word!";
		
	}

	@Override
	public boolean gameOver() {
		//if out of guesses
		if (this.guesses < 1) {
			return true;
		}
		if (Arrays.asList(this.current.split("")).contains("-")) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public int guessesRemaining() {
		return this.guesses;
	}
}
