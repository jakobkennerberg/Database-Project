package algoritmer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Ordkedjor {
	private Graph graph;
	private BFS bfs;
	ArrayList<Node> wordList;
	
	public void readFile() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/words14.txt")));
		ArrayList<String> words = new ArrayList<String>();
		wordList = new ArrayList<Node>();
		int positionCount = 0;
		while (true) {
			String word = br.readLine();
			if (word == null) {
				break; 
			}
			assert word.length() == 5; // indatakoll, om man kör med assertions på
			words.add(word);
			wordList.add(new Node(word, positionCount));
			positionCount++;
			
		}
		createGraph(wordList);	
	}
	

	
			
	public void test() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/testcase14.txt")));
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
			graph.search(startWord, goalWord);	
		}
			
	}
	
	public void createGraph(ArrayList<Node> words) {
		graph = new Graph(words.size());
		
		for(int i = 0; i < words.size(); i++) {
			for(Node w : words) {
				if(words.get(i).equals(w)) {
					//SKA MAN HA EN EDGE MED SIG SJÄLV????
				} else if (controlLetters(words.get(i), w)) {
					graph.addEdge(words.get(i), w);
					//System.out.println(words.get(i).getWord() +" -> "+ w.getWord());
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
