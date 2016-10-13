package assignment2;

import java.util.ArrayList;

public class Polynomial {

	// Coefficients are ordered by significance, ie coefficients[0] = least
	// significant coefficient
	private ArrayList<IntegerMod> coefficients;
	private int mod;

	/**
	 * Constructs an empty polynomial
	 * 
	 * @param m
	 *            The modulus
	 */
	public Polynomial(int m) {

		mod = m;
		coefficients = new ArrayList<IntegerMod>();
		coefficients.add(new IntegerMod(0, m));
	}

	/**
	 * Constructs Polynomial from arraylist of coefficients
	 * 
	 * @param c
	 *            Coefficients
	 * @param m
	 *            Modulus
	 */
	public Polynomial(ArrayList<IntegerMod> c, int m) {
	
		mod = m;
		coefficients = c;
	}

	/**
	 * A subclass containing division results quot and rem, the quotient and
	 * remainder of the division
	 * Used from last year by Jochem Kuijpers
	 */
	public class DivisionResult {
		private Polynomial quot;
		private Polynomial rem;

		public DivisionResult(Polynomial quot, Polynomial rem) {
			this.quot = quot;
			this.rem = rem;
		}

		public Polynomial quot() {
			return this.quot;
		}

		public Polynomial rem() {
			return this.rem;
		}
	}

	/**
	 * A subclass containing results of the Extended Euclidean Algorithm.
	 * Used from last year by Jochem Kuijpers
	 */
	public class ExtEuclideanResult {
		private Polynomial gcd;
		private Polynomial x;
		private Polynomial y;

		public ExtEuclideanResult(Polynomial gcd, Polynomial x, Polynomial y) {
			this.gcd = gcd;
			this.x = x;
			this.y = y;
		}

		public Polynomial gcd() {
			return this.gcd;
		}

		public Polynomial x() {
			return this.x;
		}

		public Polynomial y() {
			return this.y;
		}
	}

	public int getMod() {
		return mod;
	}

	/**
	 * Produce the sum of the polynomials
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial sum(Polynomial x) {
		return null;
	}

	/**
	 * Produce the scalar multiple
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial scalarMultiple(int x) {
		return null;
	}

	/**
	 * Returns the difference with x
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial difference(Polynomial x) {
		return null;
	}

	/**
	 * Returns the product with x
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial product(Polynomial x) {
		return null;
	}
	
	/**
	 * 
	 * @param b		the polynomial to divide by
	 * @return		(quot,rem) such that (this == quot * b + rem)
	 */
	public Polynomial.DivisionResult divide(Polynomial b) {

		return null;
	}
	
	/**
	 * Calculates the greatest common divider of polynomials this and other
	 * via the Extended Euclidean Algorithm (1.2.11)
	 * 
	 * @param other	the other polynomial
	 * @return		gcd, x and y such that this * x + other * y = gcd
	 */
	public Polynomial.ExtEuclideanResult gcd(Polynomial other) {
		
		
		return null;
	}
	
	/**
	 * Checks if polyonomial x is equal to this polynomial
	 * @param x
	 * @return equality of the input and this polynomial
	 */
	public boolean equals(Polynomial x){
		
		return false;
	}
	
	/**
	 * Used to get a 1 polynomial in an easy way.a
	 * @param m
	 * @return The 1 polynomial, with modulus m
	 */
	public static Polynomial ONE(int m){
		ArrayList<IntegerMod> o = new ArrayList<IntegerMod>();
		o.add(new IntegerMod(1, m));
		Polynomial one = new Polynomial(o, m);
		return one;
	}
}
