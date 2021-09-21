package week4;

public class VigenereCipher {
	
	private CaesarCipher[] ciphers;
	private int[] cypherKey;
	
	public VigenereCipher(int[] key) {
		ciphers = new CaesarCipher[key.length];
		cypherKey = key;
		for (int k = 0; k < key.length; k++) {
			ciphers[k] = new CaesarCipher(cypherKey[k]);
		}
		
	}
	
	public String encrypt(String input) {
		
		StringBuilder holderString = new StringBuilder(input);
		char encryptedCharacter;
		
		for (int k = 0; k < ciphers.length; k++) {
			
			for (int i = k; i < holderString.length(); i += ciphers.length) {
				encryptedCharacter = ciphers[k].encryptLetter(holderString.charAt(i));
				holderString.setCharAt(i,encryptedCharacter);
			}
		}
		
		return holderString.toString();
	}
	
	public String decrypt(String input) {
		StringBuilder holderString = new StringBuilder(input);
		char decryptedCharacter;
		
		for (int k = 0; k < ciphers.length; k++) {
			for (int i = k; i < holderString.length(); i += ciphers.length) {
				decryptedCharacter = ciphers[k].decryptLetter(holderString.charAt(i));
				holderString.setCharAt(i,decryptedCharacter);
			}
		}
		
		return holderString.toString();
	}
	
	public String toString() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder holderString = new StringBuilder(cypherKey.length);
				
		for (int k = 0; k < cypherKey.length; k++) {
			holderString.append(alphabet.charAt(cypherKey[k]));
		}
		
		return "key is " + holderString.toString();
	}
	
	
	
}
