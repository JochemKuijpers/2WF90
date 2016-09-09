package main;

public class Number {
	private final int base;
	private final int[] words;
	private final boolean negative;

	/**
	 * Create a new Number.
	 * 
	 * @param base
	 *            base of the number
	 * @param words
	 *            words of the number
	 * @param negative
	 *            whether or not the number is negative
	 */
	public Number(int base, int[] words, boolean negative) {

		if (base < 2 || base > 16) {
			throw new IllegalArgumentException("Illegal base. Should be > 1 and < 17, is: " + base);
		}

		if (words.length == 0) {
			words = new int[1];
			words[0] = 0;
		}

		int length = 1;
		for (int n = 0; n < words.length; n += 1) {
			if (words[n] < 0 || words[n] >= base) {
				throw new IllegalArgumentException(
						"Illegal word at position n=" + n + ", word=" + words[n] + ", base=" + base);
			}
			if (words[n] != 0) {
				length = n + 1;
			}
		}

		this.base = base;
		this.words = new int[length];
		for (int n = 0; n < length; n += 1) {
			this.words[n] = words[n];
		}
		this.negative = negative;
	}

	/**
	 * @return The number base
	 */
	public int getBase() {
		return base;
	}

	/**
	 * @return Integer array of word-values
	 */
	public int[] getWords() {
		return words;
	}

	/**
	 * If this Number is shorter than this length, the word array will be padded
	 * with zeroes, to make calculations easier.
	 * 
	 * @return Returns words of this number, optionally padded with zeroes.
	 * @param length
	 *            minimal length of the returned array
	 */
	public int[] getWords(int length) {
		if (words.length < length) {
			int[] newWords = new int[length];
			for (int n = 0; n < words.length; n += 1) {
				newWords[n] = words[n];
			}
			return newWords;
		}
		return words;
	}

	/**
	 * @return the length of the number
	 */
	public int getLength() {
		return words.length;
	}

	/**
	 * @return whether or not the number is negative
	 */
	public boolean isNegative() {
		return negative;
	}

	/**
	 * @return the number opposite of zero from this number, 7 becomes -7, -4
	 *         becomes 4, etc.
	 */
	public Number flipSign() {
		return new Number(base, words, !negative);
	}

	/**
	 * Get the numeric value of this number as a long (64 bits). May overflow or
	 * become inaccurate for large numbers. For debugging purposes.
	 * 
	 * @return numeric value
	 */
	public long getValue() {
		long value = 0;
		for (int n = 0; n < words.length; n += 1) {
			value += (long) (words[n] * Math.pow(base, n));
		}
		if (negative) {
			value *= -1;
		}
		return value;
	}
	
	/**
	 * @param other number to compare to
	 * @return whether or not this number is larger than the other one.
	 */
	public boolean isLargerThan(Number other) {
		int length = Math.max(words.length, other.getLength());
		int[] myWords = getWords(length);
		int[] otherWords = other.getWords(length);
		
		if (!negative && other.isNegative()) {
			return true;
		}
		
		if (negative && !other.isNegative()) {
			return false;
		}
		
		boolean absLarger = false;
		
		for (int n = length - 1; n >= 0; n -= 1) {
			if (myWords[n] > otherWords[n]) {
				absLarger = true;
				break;
			}
			if (myWords[n] < otherWords[n]) {
				absLarger = false;
				break;
			}
		}
		return absLarger ^ negative;
	}

	/**
	 * String representation of the Number
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int word : words) {
			sb.append(WordUtils.IntToToken(word));
		}
		if (negative) {
			sb.append("-");
		}
		sb.reverse();
		return sb.toString();
	}
	
	/**
	 * Create a new Number from the string representation
	 * 
	 * @param base
	 *            base of the number
	 * @param string
	 *            string representation
	 * @throws IllegalArgumentException
	 *             if an invalid token was present in the string or the word was
	 *             out of range
	 */
	public static Number fromString(int base, String string) throws IllegalArgumentException {
		boolean negative = string.startsWith("-");
		int length = string.length();
		if (negative) {
			length -= 1;
			string = string.substring(1);
		}
		int[] words = new int[length];

		for (int n = 0; n < length; n += 1) {
			words[length - n - 1] = WordUtils.TokenToInt(string.substring(n, n + 1));
			if (words[length - n - 1] < 0 || words[length - n - 1] > base) {
				throw new IllegalArgumentException(
						"Illegal word at position n=" + n + ", word=" + words[length - n - 1] + ", base=" + base);
			}
		}
		
		return new Number(base, words, negative);
	}
}
