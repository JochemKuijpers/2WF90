package main;

import java.util.Arrays;

public class Calculator {

	private long add;
	private long sub;
	private long mul;

	public Calculator() {
		add = 0;
		sub = 0;
		mul = 0;
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

		// ignore zeroes
		if (a.getLength() == 1 && a.getWords()[0] == 0) {
			return b;
		}
		if (b.getLength() == 1 && b.getWords()[0] == 0) {
			return a;
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
		System.out.println(a.toString() + "-" + b.toString());
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

		// ignore zeroes
		if (a.getLength() == 1 && a.getWords()[0] == 0) {
			return b.flipSign();
		}
		if (b.getLength() == 1 && b.getWords()[0] == 0) {
			return a;
		}

		// normal subtraction (a > b, a and b non-negative)
		int base = a.getBase();
		int length = Math.max(a.getLength(), b.getLength());
		int[] wordsa = a.getWords(length);
		int[] wordsb = b.getWords(length);
		int[] wordsc = new int[length];
		int carry = 0;
		for (int n = 0; n < length; n += 1) {
			wordsc[n] = wordsa[n] - wordsb[n];
			sub += 1;
			if (carry > 0) {
				wordsc[n] -= carry;
				sub += 1;
			}
			carry = 0;
			if (wordsc[n] < 0) {
				carry = 1;
				wordsc[n] += base;
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
		if (a.getBase() != b.getBase()) {
			throw new IllegalArgumentException("Both numbers have to be represented in the same base.");
		}

		int base = a.getBase();
		int length = Math.max(a.getLength(), b.getLength());
		if (a.getLength() < b.getLength()) {
			Number t = a;
			a = b;
			b = t;
		}
		int[] transfer = new int[2 * length];
		int[][] subresult = new int[a.getLength()][2 * length];
		int prelres = 0;

		// Find the sign of the result
		boolean resultSign = a.isNegative() ^ b.isNegative();

		if (a.isNegative())
			a = a.flipSign();
		if (b.isNegative())
			b = b.flipSign();

		// The primary school multiplication (multiplying digit by digit and
		// putting each subresult in a matrix)
		int carrier = 0;
		for (int i = 0; i < b.getLength(); i++) {
			carrier = 0;
			int t = 0;
			for (int j = 0; j < a.getLength(); j++) {
				prelres = a.getWords()[j] * b.getWords()[i];
				mul++;
				if (carrier != 0) {
					prelres += carrier;
					add++;
				}
				subresult[i][2 * length - 1 - i - j] = prelres % base;
				carrier = prelres / base;
				t = j;
			}
			if (carrier != 0 && (2 * length) > 2) {
				subresult[i][2 * length - 2 - i - t] = carrier;
			} else if (carrier != 0 && (2 * length) == 2) {
				subresult[i][0] = carrier;
			}
		}

		// The addition column by column for the final result
		for (int i = 2 * length - 1; i >= 0; i--) {
			for (int j = 0; j < b.getLength(); j++) {
				if (subresult[j][i] != 0) {
					transfer[2 * length - 1 - i] += subresult[j][i];
					add++;
				}
			}
		}
		carrier = 0;

		// Making sure that each "digit" of the result isn't equal to or greater
		// than the base (the carries for the result)
		for (int i = 0; i < 2 * length; i++) {
			if (carrier != 0) {
				transfer[i] = transfer[i] + carrier;
				add++;
			}
			if (transfer[i] >= base) {
				carrier = transfer[i] / base;
				transfer[i] = transfer[i] % base;
			} else {
				carrier = 0;
			}
		}

		// The final result (represented in the proper base)
		return new Number(base, transfer, resultSign);
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

		if (a.getBase() != b.getBase()) {
			throw new IllegalArgumentException("Both numbers have to be represented in the same base.");
		}

		int base = a.getBase();

		boolean resultNegative = a.isNegative() ^ b.isNegative();

		if (a.isNegative())
			a = a.flipSign();
		if (b.isNegative())
			b = b.flipSign();

		// Recursive base
		if (a.getLength() == 1 && b.getLength() == 1) {
			int product = a.getWords()[0] * b.getWords()[0];
			mul++;
			if (product >= base) {
				int[] prodwords = new int[2];
				prodwords[0] = product % base;
				prodwords[1] = product / base;
				return new Number(base, prodwords, resultNegative);
			} else {
				int[] prodwords = { product };
				return new Number(base, prodwords, resultNegative);
			}
		}

		// Recursive step

		// First make the numbers even and equal length..
		int length = Math.max(a.getLength(), b.getLength());
		if (length % 2 == 1) {
			length++;
		}
		int[] wordsa = a.getWords(length);
		int[] wordsb = b.getWords(length);

		// get Ahi, Alo, Bhi, Blo
		Number Alo = new Number(base, Arrays.copyOfRange(wordsa, 0, length / 2), false);
		Number Ahi = new Number(base, Arrays.copyOfRange(wordsa, length / 2, length), false);
		Number Blo = new Number(base, Arrays.copyOfRange(wordsb, 0, length / 2), false);
		Number Bhi = new Number(base, Arrays.copyOfRange(wordsb, length / 2, length), false);

		// Calculate the three parts you need
		Number AhiBhi = mulKaratsuba(Ahi, Bhi);
		Number AloBlo = mulKaratsuba(Alo, Blo);
		// Calculates: (Ahi + Alo) * (Bhi + Blo)
		Number AhiAloBhiBlo = mulKaratsuba(add(Ahi, Alo), add(Bhi, Blo));

		// ahiblo + alobhi = (ahi + alo) * (bhi + blo) - ahibhi - aloblo
		Number AhiBloPlusAloBhi = subtract(subtract(AhiAloBhiBlo, AhiBhi), AloBlo);
		Number AB = add(add(AhiBhi.shiftToLeft(length), AhiBloPlusAloBhi.shiftToLeft(length / 2)), AloBlo);

		if (resultNegative) {
			return AB.flipSign();
		}
		return AB;
	}

	/**
	 * Print the statistics of this calculator. Should be called after
	 * performing a calculation
	 */
	public void printStats() {
		System.out.println("Number of elementary operations");
		System.out.println("  Elementary additions:       " + String.format("%1$9s", add));
		System.out.println("  Elementary subtractions:    " + String.format("%1$9s", sub));
		System.out.println("  Elementary multiplications: " + String.format("%1$9s", mul));
		System.out.println("  Total elem. operations:     " + String.format("%1$9s", (add + sub + mul)));
	}

	/**
	 * Resets the calculator statistics.
	 */
	public void resetStats() {
		add = 0;
		sub = 0;
		mul = 0;
	}

	public long getAdd() {
		return add;
	}

	public long getSub() {
		return sub;
	}

	public long getMul() {
		return mul;
	}

}
