package mypackage;

import java.util.*;
import java.util.Map.Entry;
//import java.util.Scanner;
import java.io.*;

public class LyricAnalyzer {

	//	If the word is not in the map, 
	//	then a mapping is added that maps that word to a list containing the position of the word in the lyric. 
	//	else then its word position is added to the list of word positions for this word. 
	public static void add(HashMap<String, ArrayList<Integer>> map, String lyricWord, int wordPosition) {
		if (map.containsKey(lyricWord)) {
			map.get(lyricWord).add(wordPosition);
		} else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			map.put(lyricWord, list);
			list.add(wordPosition);
		}
	}

	//	display the words of the song along with the word positions of the word, one word per line, in alphabetical order
	public static void displayWords(HashMap<String, ArrayList<Integer>> map) {
		String tmp = "";
		List<String> key = new ArrayList<>(map.keySet());
		//sort key set
		Collections.sort(key);
		for (String k : key) {
			tmp += k + " " + map.get(k).toString() + "\n";
		}
		System.out.println(tmp);
	}

	// display the lyrics for the song (in upper case) stored in the map
	public static void displayLyrics(HashMap<String, ArrayList<Integer>> map) {
		int size = 0;
		//get the size of the lyrics
		for (String k : map.keySet()) {
			size += map.get(k).size();
		}
		// create an array to store the lyrics and initialized to ""
		String[] lyric = new String[size + 1];
		Arrays.fill(lyric, "");
		// loop the whole map and fill the lyrics array
		for (Map.Entry<String, ArrayList<Integer>> item : map.entrySet()) {
			String key = item.getKey();
			ArrayList<Integer> value = item.getValue();
			for (int pos : value) {
				if (pos > 0) {
					lyric[pos] = key;
				}
				// the last word of each line should add "\n" to create a new line
				else {
					lyric[-pos] = key + "\n";
				}
			}
		}
		// print the array
		for (String s : lyric) {
			System.out.print(s + " ");
		}
	}

	//	total number of unique words 
	public static int count(HashMap<String, ArrayList<Integer>> map) {
		return map.size();
	}
	
	//	return the word that occurs most frequently in the lyric
	public static String mostFrequentWord(HashMap<String, ArrayList<Integer>> map) {
		String result = "";
		int count = 0;
		//sort the key set
		List<String> key = new ArrayList<>(map.keySet());
		Collections.sort(key);
		// loop key set and find the longest ArrayList among the words and store to count
		for (String k : key) {
			int tmp = map.get(k).size();
			if (tmp > count) {
				count = tmp;
				result = k;
			}
		}
		return result;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// Tester to call the methods written above
		HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
		String myFile = "anotherbrick.txt";
//		String myFile = "minimal.txt";
//		String myFile = "realgone.txt";
//		String myFile = "sample.txt";
//		String myFile = "minimalss.txt";
		try {
			Scanner fileScanner = new Scanner(new File(myFile));
		
			int position = 0;
			// scan by line
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				// scan by word
				Scanner lineScanner = new Scanner(line);
				String word = "";
				while (lineScanner.hasNext()) {
					word = lineScanner.next().toUpperCase();
					position++;
					// add to each word to map
					add(map, word, position);
				}
				lineScanner.close();
				// at the end of the line change the last element of the ArrayList to negative.
				if (map.get(word) != null) {
					int tmp = map.get(word).size();
					map.get(word).set(tmp - 1, -position);
				}
			}
			fileScanner.close();
			System.out.println("Input name of lyrics file: " + myFile + "\n");
			// display the map
			displayWords(map);
			System.out.println();
			// display lyrics
			displayLyrics(map);
			System.out.println();
			// The number of unique words
			System.out.println("The number of unique words in the lyric is: " + count(map) + "\n");
			// Most frequent word
			System.out.println("Most frequent word: " + mostFrequentWord(map) + "\n");
			
		}catch(FileNotFoundException ex) {
			System.out.println("Error "+ ex);
			System.exit(0);
			}
	}

}
