package main;

public class WordUtils {
	private static final String[] tokens = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
			"F" };

	/**
	 * Returns the string value of a word
	 * 
	 * @param word
	 *            word value
	 * @return string value of the word
	 */
	public static String IntToToken(int word) {
		if (word < 0 || word > tokens.length - 1) {
			throw new IllegalArgumentException("No token for word: " + word);
		}
		return tokens[word];
	}

	/**
	 * Returns the integer value of a single word string
	 * 
	 * @param token
	 *            single character word
	 * @return integer value of the word
	 */
	public static int TokenToInt(String token) {
		for (int n = 0; n < tokens.length; n += 1) {
			if (tokens[n].equals(token) || tokens[n].equals(token.toLowerCase().charAt(0))) {
				return n;
			}
		}

		throw new IllegalArgumentException("Unknown token cannot be converted to an integer: " + token);
	}
}
