package main;

import java.util.Arrays;

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
		int base = a.getBase();
		int maxLength = Math.max(a.getLength(), b.getLength());
		if(a.getLength() < b.getLength()){
			Number c = a;
			a = b;
			b = c;
		}
		int[] transfer = new int[2 * maxLength];
		int[][] subresult = new int[a.getLength()][2 * maxLength];
		int prelres = 0;

		if (a.getBase() != b.getBase()) {
			throw new IllegalArgumentException("Both numbers have to be represented in the same base.");
		}

		int signA = a.isNegative() ? -1 : 1, signB = b.isNegative() ? -1 : 1;
		int resultSign = signA * signB;

		if (a.isNegative())
			a.flipSign();
		if (b.isNegative())
			b.flipSign();

		int carrier = 0;
		for (int i = 0; i < b.getLength(); i++) {
			carrier = 0;
			int t = 0;
			for (int j = 0; j < a.getLength(); j++) {
				prelres = a.getWords()[j] * b.getWords()[i] + carrier;
				mul++;
				if (carrier != 0)
					add++;
				subresult[i][2*maxLength - 1 - i - j] = prelres % base;
				carrier = prelres / base;
				t = j;
			}
			if(carrier != 0 && (2 * maxLength) > 2 ) {
				subresult[i][2 * maxLength - 2 - i - t] = carrier;
			}
			else if(carrier != 0 && (2 * maxLength) == 2){
				subresult[i][0] = carrier;
			}
		}

		for(int i = 2 * maxLength - 1; i >= 0; i--){
			for(int j = 0; j < b.getLength(); j++){
				if(transfer[2 * maxLength - 1 - i] != 0 && subresult[j][i] != 0){
					add++;
				}
				transfer[2 * maxLength - 1 - i] += subresult[j][i];
			}
		}
		carrier = 0;

		for(int i = 0; i < 2 * maxLength; i++){
			if(carrier != 0 && transfer[i] != 0){
				add++;
			}
			transfer[i] = transfer[i] + carrier;
			if(transfer[i] >= base) {
				carrier = transfer[i] / base;
				transfer[i] = transfer[i] % base;
			}
			else{
				carrier = 0;
			}
		}

		if(resultSign < 0)
			return new Number(base, transfer, true);
		return new Number(base, transfer, false);
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

		int signA = a.isNegative() ? -1 : 1, signB = b.isNegative() ? -1 : 1;
		int resultSign = signA * signB;

		if (a.isNegative())
			a.flipSign();
		if (b.isNegative())
			b.flipSign();

		// Recursive base
		if (a.getLength() == 1 && b.getLength() == 1) {
			int product = a.getWords()[0] * b.getWords()[0];
			mul++;
			if (product >= base) {
				int[] prodwords = new int[2];
				prodwords[0] = product % base;
				prodwords[1] = product / base;
				return new Number(base, prodwords, resultSign == -1);
			} else {
				int[] prodwords = { product };
				return new Number(base, prodwords, resultSign == -1);
			}
		}

		// Recursive step

		// First make the numbers even and equal length..
		int length = Math.max(a.getLength(), b.getLength());
		if (isOdd(length))
			length++;
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
		
		if (resultSign < 0) {
			return AB.flipSign();
		}
		return AB;
	}

	private boolean isOdd(int number) {
		return Math.floorMod(number, 2) == 1;
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

	public int getAdd() {
		return add;
	}

	public int getSub() {
		return sub;
	}

	public int getMul() {
		return mul;
	}
	
	
}