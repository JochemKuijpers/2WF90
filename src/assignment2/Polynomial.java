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
}
