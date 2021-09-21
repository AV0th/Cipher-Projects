package week4;

import java.util.HashSet;

import edu.duke.*;

public class VigenereBreaker {

	public String sliceString(String message, int whichSlice, int totalSlices) {
		String result = "";
		for (int k = whichSlice; k < message.length(); k += totalSlices) {
			result = result + message.charAt(k);
		}
		
		return result;
	}
	
	public int[] tryKeyLength(String encrypted, int kLength, char mostCommon) {
		int[] holderArray = new int[kLength];
		String slicedString = "";
		
		CaesarCracker cracker = new CaesarCracker();
		
			for (int k = 0; k < kLength; k++) {
				slicedString = sliceString(encrypted,k,kLength);
				holderArray[k] = cracker.getKey(slicedString);				
			}
			
		return holderArray;
	}
	
	public void breakVigenere() {
		
		FileResource resource = new FileResource();
		String fileString = resource.asString();
		
		
		FileResource dictionaryResoure = new FileResource();
		
		HashSet<String> dictionary = readDictionary(dictionaryResoure);
		
		//prints first line of string
		
		String[] decrypted = breakForLanguage(fileString,dictionary).split("\n");
		System.out.println(decrypted[0]);
		
		
		//breakForLanguage(fileString,dictionary);
		
	}
	
	public HashSet<String> readDictionary(FileResource fr) {
		HashSet<String> dictionary = new HashSet<String>();
		
		for (String line : fr.lines()) {
			dictionary.add(line.toLowerCase());
		}
		
		return dictionary;
	}
	
	public int countWords(String message, HashSet<String> dictionary) {
		int numWordsFound = 0;
		
		for (String word : message.split("\\W")) {
			if (dictionary.contains(word.toLowerCase())) {
				numWordsFound++;
			}
		}
		
		return numWordsFound;
	}
	
	public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
		int maxNumOfWords = 0;
		int indexHolder = 0;
		int currNumOfWords = 0;
		int[] keyHolder;
		VigenereCipher cypher;
		
		for (int k = 1; k <= 100; k++) {
			keyHolder = tryKeyLength(encrypted, k,'e');
			cypher = new VigenereCipher(keyHolder);
			currNumOfWords = countWords(cypher.decrypt(encrypted),dictionary);
			if (currNumOfWords > maxNumOfWords) {
				
				if (k == 38) {
					System.out.println("key length of " + k + " current number of valid words: " + currNumOfWords);
				}
				
				maxNumOfWords = currNumOfWords;
				indexHolder = k;
			}
		}
		
		keyHolder = tryKeyLength(encrypted, indexHolder,'e');
		cypher = new VigenereCipher(keyHolder);
		String wordKey = cypher.toString();
		System.out.println("word key: " + wordKey);
		System.out.println("word key length: " + wordKey.length());
		System.out.println("key holder length: " +keyHolder.length);
		System.out.println("max number of words: " + maxNumOfWords);
		return cypher.decrypt(encrypted);
		
		
	}
		
		
}

