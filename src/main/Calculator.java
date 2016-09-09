package main;

public class Calculator {

	private int add, sub, mul;

	public Calculator() {
		add = sub = mul = 0;
	}

	/**
	 * Adds numbers a and b and returns the answer
	 * 
	 * @param a
	 *            First number
	 * @param b
	 *            Second number
	 * @return Answer of the sum a + b
	 */
	public Number add(Number a, Number b) {
		if (a.getBase() != b.getBase()) {
			throw new IllegalArgumentException("Both numbers have to be represented in the same base.");
		}

		// handle non-positive cases
		if (a.isNegative() && b.isNegative()) {
			// a + b = -(-a + -b)
			return this.add(a.flipSign(), b.flipSign()).flipSign();
		}
		if (a.isNegative() && !b.isNegative()) {
			// a + b = b - -a
			return this.subtract(b, a.flipSign());
		}
		if (!a.isNegative() && b.isNegative()) {
			// a + b = a - -b
			return this.subtract(a, b.flipSign());
		}

		// addition of positive integers
		int base = a.getBase();
		int length = Math.max(a.getLength(), b.getLength());
		int[] wordsa = a.getWords(length);
		int[] wordsb = b.getWords(length);
		int[] wordsc = new int[length + 1];
		int carry = 0;
		for (int n = 0; n < length; n += 1) {
			wordsc[n] = wordsa[n] + wordsb[n];
			add += 1;
			if (carry > 0) {
				wordsc[n] += carry;
				add += 1;
			}
			carry = 0;
			if (wordsc[n] >= base) {
				carry = 1;
				wordsc[n] -= base;
				sub += 1;
			}
		}
		if (carry > 0) {
			wordsc[length] = carry;
		}

		return new Number(base, wordsc, false);
	}

	/**
	 * Subtracts b from a and returns the answer
	 * 
	 * @param a
	 *            First number
	 * @param b
	 *            Second number
	 * @return The subtraction a - b
	 */
	public Number subtract(Number a, Number b) {
		if (a.getBase() != b.getBase()) {
			throw new IllegalArgumentException("Both numbers have to be represented in the same base.");
		}

		// handle non-positive cases
		if (!a.isNegative() && b.isNegative()) {
			// a - b = a + -b
			return this.add(a, b.flipSign());
		}
		if (a.isNegative() && !b.isNegative()) {
			// a - b = -(-a + b)
			return this.add(a.flipSign(), b).flipSign();
		}
		if (a.isNegative() && b.isNegative()) {
			// a - b = -b - -a
			return this.subtract(b.flipSign(), a.flipSign());
		}

		// handle b > a case
		if (b.isLargerThan(a)) {
			// a - b = -(b - a)
			return this.subtract(b, a).flipSign();
		}

		// normal subtraction (a > b, a and b non-negative)
		int base = a.getBase();
		int length = Math.max(a.getLength(), b.getLength());
		int[] wordsa = a.getWords(length);
		int[] wordsb = b.getWords(length);
		int[] wordsc = new int[length + 1];
		int carry = 0;
		for (int n = 0; n < length; n += 1) {
			wordsc[n] = wordsa[n] - wordsb[n];
			sub += 1;
			if (carry > 0) {
				wordsc[n] -= carry;
				sub += 1;
			}
			if (wordsc[n] < 0) {
				carry = 1;
				wordsc[n] += base;
				add += 1;
			}
		}
		return new Number(base, wordsc, false);
	}

	/**
	 * Calculates the product of the input numbers using the primary school
	 * method
	 * 
	 * @param a
	 *            First number
	 * @param b
	 *            Second number
	 * @return The answer of the product a * b
	 */
	public Number mulPrimarySchool(Number a, Number b) {
		return Number.fromString(a.getBase(), "0");
	}

	/**
	 * Calculates the product of the input numbers using Karatsuba's method
	 * 
	 * @param a
	 *            First number
	 * @param b
	 *            Second number
	 * @return The answer of the product a * b
	 */
	public Number mulKaratsuba(Number a, Number b) {
		return Number.fromString(a.getBase(), "0");
	}

	/**
	 * Print the statistics of this calculator. Should be called after
	 * performing a calculation
	 */
	public void printStats() {
		System.out.println("Elementary additions:       " + add);
		System.out.println("Elementary subtractions:    " + sub);
		System.out.println("Elementary multiplications: " + mul);
	}

	/**
	 * Resets the calculator statistics.
	 */
	public void resetStats() {
		add = 0;
		sub = 0;
		mul = 0;
	}
}
