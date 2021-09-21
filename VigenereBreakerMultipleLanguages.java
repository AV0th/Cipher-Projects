package week4;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import edu.duke.*;

public class VigenereBreakerMultipleLanguages {

	public void breakVigenere() {
		
		FileResource resource = new FileResource();
		String fileString = resource.asString();
		
		DirectoryResource directoryResource = new DirectoryResource();
		
		HashMap<String,HashSet<String>> dictionaries = new HashMap<String,HashSet<String>>();
		FileResource dictionaryResoure;
		HashSet<String> dictionary;
		
		
		for (File file : directoryResource.selectedFiles()) {
			dictionaryResoure = new FileResource(file);
			dictionary = readDictionary(dictionaryResoure);
			dictionaries.put(file.getName(), dictionary);
			System.out.println("Imported to dictionary: " + file.getName());
		}
		
		//prints first line of string
		
		String[] decrypted = breakForAllLanguage(fileString,dictionaries).split("\n");
		System.out.println(decrypted[0]);
		
		
		
	}
	
	private HashSet<String> readDictionary(FileResource fr) {
		HashSet<String> dictionary = new HashSet<String>();
		
		for (String line : fr.lines()) {
			dictionary.add(line.toLowerCase());
		}
		
		return dictionary;
	}
	
	private String breakForAllLanguage(String encrypted, HashMap<String,HashSet<String>> dictionary) {
		int maxNumOfWords = 0;
		int indexHolder = 0;
		int currNumOfWords = 0;
		int[] keyHolder;
		VigenereCipher cypher;
		String languageEncrypted = "";
		
		
		
		for (String language : dictionary.keySet()) {
			System.out.println(" ");
			System.out.println("Testing language: " + language);
			System.out.println(" ");
			
			char mostCommonChar = mostCommonCharIn(dictionary.get(language));
			for (int k = 1; k <= 100; k++) {
				keyHolder = tryKeyLength(encrypted, k,mostCommonChar);
				cypher = new VigenereCipher(keyHolder);
				currNumOfWords = countWords(cypher.decrypt(encrypted),dictionary.get(language));
				if (currNumOfWords > maxNumOfWords) {
					maxNumOfWords = currNumOfWords;
					indexHolder = k;
					languageEncrypted = language;
				}
			}					
		}
		
		
		char mostCommonChar = mostCommonCharIn(dictionary.get(languageEncrypted));
		keyHolder = tryKeyLength(encrypted, indexHolder,mostCommonChar);
		cypher = new VigenereCipher(keyHolder);
		String wordKey = cypher.toString();
		System.out.println("Language is: " + languageEncrypted);
		System.out.println("word key: " + wordKey);
		System.out.println("word key length: " + wordKey.length());
		System.out.println("key holder length: " +keyHolder.length);
		System.out.println("max number of words: " + maxNumOfWords);
		
		return cypher.decrypt(encrypted);
		
	}
	
	private char mostCommonCharIn(HashSet<String> dictionary) {
		int[] counter = new int[26];
		String originalAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		
		for (String holderString : dictionary) {
			for (int k = 0; k < holderString.length(); k++) {
				char holderChar = Character.toUpperCase(holderString.charAt(k));
				int dex = originalAlphabet.indexOf(holderChar);
				if (dex != -1) {
					counter[dex] +=1;
				}
			}
		}
		
		int maxValue = 0;
		int index = 0;
		char mostCommon;
		for (int k = 0; k < counter.length; k++) {
			if (maxValue < counter[k]) {
				maxValue = counter[k];
				index = k;
			}
		}
		
		mostCommon = originalAlphabet.charAt(index);
		
		return mostCommon;
	}
	
	private int[] tryKeyLength(String encrypted, int kLength, char mostCommon) {
		int[] holderArray = new int[kLength];
		String slicedString = "";
		
		CaesarCracker cracker = new CaesarCracker();
		
			for (int k = 0; k < kLength; k++) {
				slicedString = sliceString(encrypted,k,kLength);
				holderArray[k] = cracker.getKey(slicedString);				
			}
			
		return holderArray;
	}
	
	private String sliceString(String message, int whichSlice, int totalSlices) {
		String result = "";
		for (int k = whichSlice; k < message.length(); k += totalSlices) {
			result = result + message.charAt(k);
		}
		
		return result;
	}
	
	
	

	
	private int countWords(String message, HashSet<String> dictionary) {
		int numWordsFound = 0;
		
		for (String word : message.split("\\W")) {
			if (dictionary.contains(word.toLowerCase())) {
				numWordsFound++;
			}
		}
		
		return numWordsFound;
	}
	
	
	
	
		
		
}

