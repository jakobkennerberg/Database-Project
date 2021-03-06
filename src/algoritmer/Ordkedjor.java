package algoritmer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ordkedjor {
	private Graph graph;
	private ArrayList<Node> wordList;
	
	public void readFile() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/words250.txt")));
		ArrayList<String> words = new ArrayList<String>();
		wordList = new ArrayList<Node>();
		while (true) {
			String word = br.readLine();
			if (word == null) {
				break; 
			}
			assert word.length() == 5; // indatakoll, om man kör med assertions på
			words.add(word);
			wordList.add(new Node(word));
		}
		createGraph(wordList);	
	}
	
			
	public void test() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/testcase250.txt")));
		Node startWord = null;
		Node goalWord = null;
		while (true) {
			String line = br.readLine();
			if (line == null) { 
				break; 
			}
			assert line.length() == 11; // indatakoll, om man kör med assertions på
			String start = line.substring(0, 5);
			String goal = line.substring(6, 11);
			for(int i = 0; i < wordList.size(); i++) {
				if(start.equals(wordList.get(i).getWord())) {
					startWord = wordList.get(i);	
				}
				if(goal.equals(wordList.get(i).getWord())) {
					goalWord = wordList.get(i);
				}	
			}
			int dist = graph.search(startWord, goalWord);
			System.out.println(dist);
		}	
	}
	
	public void createGraph(ArrayList<Node> words) {
		graph = new Graph(words);
		
		for(int i = 0; i < words.size(); i++) {
			for(Node w : words) {
				if(words.get(i).equals(w)) {
					
				} else if (controlLetters(words.get(i), w)) {
					words.get(i).addEdge(w);
					//System.out.println(words.get(i).getWord() + " -> " + w.getWord());	
				}		
			}
		}
	}
	
	public static boolean controlLetters(Node currentWord, Node compare) {
		String word = currentWord.getWord();
		boolean[] charPicked = new boolean[compare.getWord().length()];
		boolean charFound = false;
		int count = 0;
		for(int i = 1; i < word.length(); i++) {
			char ch = word.charAt(i);
			charFound = false;
			for(int j = 0; j < compare.getWord().length(); j++) {
				if(ch == compare.getWord().charAt(j) && !charPicked[j]) {
					charFound = true;
					charPicked[j] = true;
					count++;
					break;
				}
			}
			if(!charFound) {
				break;
			}
		}
		if(count > 3) {
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		Ordkedjor ord = new Ordkedjor();
		try {
			ord.readFile();
			ord.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
