package cheatsheet;

import java.util.ArrayList;

/**
 * A class for polynomials with coefficients mod p prime.
 * 
 * Jochem Kuijpers
 * 0838617 - 26 October 2015
 */
public class Polynomial {
	/**
	 * A list of coefficients.
	 * 
	 * Element #0 is the coefficient of x^0, element #1 of x^1, etc.
	 */
	private ArrayList<IntegerPrime> coefficients;
	
	/**
	 * Modulo p = prime for the coefficients.
	 */
	private int prime;
	
	/**
	 * A subclass containing division results quot and rem, the quotient and
	 * remainder of the division
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
	 * Constructor with an ArrayList of coefficients.
	 * 
	 * The constructor makes sure the polynomial has at least one coefficient.
	 * 
	 * @param coefficients	the coefficients of the polynomial
	 * @param prime			the modulo of the coefficients
	 */
	public Polynomial(ArrayList<IntegerPrime> coefficients, int prime) {
		this.coefficients = new ArrayList<IntegerPrime>();
		this.prime = prime;

		// copy the input list and count the number of zero coefficients
		int zeroes = 0;
		if (coefficients != null) {
			for (IntegerPrime a : coefficients) {
				this.coefficients.add(a);
				
				if (a.getValue() == 0) {
					zeroes += 1;
				} else {
					zeroes = 0;
				}
			}
		}
		
		// remove the trailing zeroes in the coefficient list to reduce wasted
		// calculations and memory on zero-coefficients
		for (int n = 0; n < zeroes; n += 1) {
			this.coefficients.remove(this.coefficients.size() - 1);
		}
		
		// add a zero coefficient if the list is still empty
		if (this.coefficients.size() == 0) {
			this.coefficients.add(new IntegerPrime(0, prime));
		}
	}
	
	/**
	 * Constructor with a single coefficient and order such that
	 * 
	 * this = coefficient X^order
	 * 
	 * @param coefficient	the coefficient of this polynomial
	 * @param order			the order of the indeterminate corresponding to
	 * 						this coefficient
	 */
	public Polynomial(IntegerPrime coefficient, int order) {
		this.coefficients = new ArrayList<IntegerPrime>();
		this.prime = coefficient.getPrime();
		
		// add #order zero coefficients
		for (int n = 0; n < order; n += 1) {
			this.coefficients.add(new IntegerPrime(0, coefficient.getPrime()));
		}
		
		// copy coefficient
		this.coefficients.add(new IntegerPrime(
				coefficient.getValue(), coefficient.getPrime()));
	}
	
	
	/**
	 * Give away a copy of the coefficients because we don't want other classes
	 * to change our coefficients.
	 * 
	 * @return ArrayList with coefficients
	 */
	public ArrayList<IntegerPrime> getCoefficients() {
		ArrayList<IntegerPrime> copy = new ArrayList<IntegerPrime>();
		
		for (IntegerPrime a : coefficients) {
			copy.add(a);
		}
		
		return copy;
	}

	/**
	 * Polynomial addition
	 * 
	 * a = a0 + a1 X + a2 X^2 + ... + an X^n
	 * b = b0 + b1 X + b2 X^2 + ... + bn X^n
	 * c = (a0 + b0) + (a1 + b1) X + ... + (an + bn) X^n
	 * 
	 * @param other	the polynomial to add to this one
	 * @return		c
	 */
	public Polynomial add(Polynomial other) {
		ArrayList<IntegerPrime> a, b, c;
		a = this.getCoefficients();
		b = other.getCoefficients();
		c = new ArrayList<IntegerPrime>();
		
		// make a and b equally big, fill with zero coefficients.
		while (a.size() < b.size()) {
			a.add(new IntegerPrime(0, this.prime));
		}
		while (b.size() < a.size()) {
			b.add(new IntegerPrime(0, this.prime));
		}
		
		// now calculate cn = an + bn for all coefficients
		for (int n = 0; n < a.size(); n += 1) {
			c.add(a.get(n).add(b.get(n)));
		}
		
		// create and return new polynomial
		return new Polynomial(c, this.prime);
	}
	
	/**
	 * Polynomial subtraction
	 * 
	 * a = a0 + a1 X + a2 X^2 + ... + an X^n
	 * b = b0 + b1 X + b2 X^2 + ... + bn X^n
	 * c = (a0 - b0) + (a1 - b1) X + ... + (an - bn) X^n
	 * 
	 * @param other	the polynomial to subtract from this one
	 * @return		c
	 */
	public Polynomial subtract(Polynomial other) {
		ArrayList<IntegerPrime> a, b, c;
		a = this.getCoefficients();
		b = other.getCoefficients();
		c = new ArrayList<IntegerPrime>();
		
		// make a and b equally big, fill with zero coefficients.
		while (a.size() < b.size()) {
			a.add(new IntegerPrime(0, this.prime));
		}
		while (b.size() < a.size()) {
			b.add(new IntegerPrime(0, this.prime));
		}
		
		// now calculate cn = an - bn for all coefficients
		for (int n = 0; n < a.size(); n += 1) {
			c.add(a.get(n).subtract(b.get(n)));
		}
		
		// create and return new polynomial
		return new Polynomial(c, this.prime);
	}
	
	/**
	 * Polynomial multiplication
	 * 
	 * a = a0 + a1 X + a2 X^2 + ... + an X^n
	 * b = b0 + b1 X + b2 X^2 + ... + bn X^n
	 * c = c0 + c1 X + c2 X^2 + ... + c2n X^2n
	 *     where ck = a0bk + a1b(k-1) + ... + akb0
	 * 
	 * @param other	the polynomial to multiply with this one
	 * @return		c
	 */
	public Polynomial multiply(Polynomial other) {
		ArrayList<IntegerPrime> a, b, c;
		a = this.getCoefficients();
		b = other.getCoefficients();
		c = new ArrayList<IntegerPrime>();
		
		int n = Math.max(a.size(), b.size());
		IntegerPrime ck;
		
		// make a and b equally big, fill with zero coefficients.
		while (a.size() < 2*n) {
			a.add(new IntegerPrime(0, this.prime));
		}
		while (b.size() < 2*n) {
			b.add(new IntegerPrime(0, this.prime));
		}
		
		// now calculate ck = a0bk + a1b(k-1) + ... + akb0 for k from 0 to 2n
		for (int i = 0; i < 2*n; i += 1) {
			ck = new IntegerPrime(0, this.prime);
			for (int k = 0; k <= i; k += 1) {
				ck = ck.add(a.get(k).multiply(b.get(i - k)));
			}
			c.add(ck);
		}
		
		// create and return new polynomial
		return new Polynomial(c, this.prime);
	}
	
	/**
	 * Divide this polynomial by the other polynomial by long division.
	 * 
	 * rem is the 'working copy' of this polynomial. Every step the leading
	 * term is subtracted and in the end rem is the remainder of the division.
	 * 
	 * Every step, the leading terms of b and rem are matched by multiplying b 
	 * with a certain factor. This factor is added to quot.
	 * 
	 * At all times at the end of each step, a = quot * b + rem. Every step,
	 * lc(rem) decreases and when lc(rem) < lc(b), the division is over.
	 * 
	 * A more elaborate proof of this algorithm is given in the PDF.
	 * 
	 * @param b		the polynomial to divide by
	 * @return		(quot,rem) such that (this == quot * b + rem)
	 */
	public Polynomial.DivisionResult divide(Polynomial b) {
		
		// quot = 0 X^0
		Polynomial quot = new Polynomial(new IntegerPrime(0, this.prime), 0);
		
		// copy this to rem
		Polynomial rem = new Polynomial(this.coefficients, this.prime);
		
		Polynomial factor; 
		
		// while deg(rem) >= deg(b), run long division
		while (rem.deg() >= b.deg()) {
			
			// factor = (rem.lt / b.lt) X^(rem.lc - b.lc)
			factor = new Polynomial(rem.lt().divide(b.lt()), rem.lc() - b.lc());
			
			// subtract factor * b from rem
			rem = rem.subtract(factor.multiply(b));
			
			// add factor to quot
			quot = quot.add(factor);
		}
		
		return new Polynomial.DivisionResult(quot, rem);
	}
	
	/**
	 * Calculates the greatest common divider of polynomials this and other
	 * via the Extended Euclidean Algorithm (1.2.11)
	 * 
	 * @param other	the other polynomial
	 * @return		gcd, x and y such that this * x + other * y = gcd
	 */
	public Polynomial.ExtEuclideanResult gcd(Polynomial other) {
		// step 1: declare and initialize variables
		Polynomial a = new Polynomial(this.coefficients, this.prime);
		Polynomial b = new Polynomial(other.getCoefficients(), this.prime);
		Polynomial x = new Polynomial(new IntegerPrime(1, this.prime), 0);
		Polynomial y = new Polynomial(new IntegerPrime(0, this.prime), 0);
		Polynomial v = new Polynomial(new IntegerPrime(1, this.prime), 0);
		Polynomial u = new Polynomial(new IntegerPrime(0, this.prime), 0);
		Polynomial q, y_, x_;
		
		boolean swapped = false;
		
		// a.lc() >= b.lc(), if not, borrow a bit of memory from q to swap
		if (a.lc() < b.lc()) {
			q = a;
			a = b;
			b = q;
			swapped = true;
		}
		
		Polynomial.DivisionResult result;
		
		// step 2:
		while (b.lt().getValue() != 0) {
			result = a.divide(b);
			q = result.quot();
			a = b;
			b = result.rem();
			x_ = x;
			y_ = y;
			x = u;
			y = v;
			u = x_.subtract(q.multiply(u));
			v = y_.subtract(q.multiply(v));
		}
		
		// if we swapped a and b in the beginning, we need to swap x and y now
		if (swapped) {
			// again, borrow q:
			q = x;
			x = y;
			y = q;
		}
		
		return new Polynomial.ExtEuclideanResult(a, x, y);
	}
	
	/**
	 * Return the leading term of this polynomial
	 * @return	the leading term	
	 */
	public IntegerPrime lt() {
		return this.coefficients.get(this.coefficients.size() - 1);
	}
	
	/**
	 * Return the leading coefficient of this polynomial
	 * @return	the leading coefficient
	 */
	public int lc() {
		return this.coefficients.size() - 1;
	}
	
	/**
	 * Return the degree of the polynomial. It's essentially the same as the
	 * leading coefficient, except when the leading coefficient is 0 and the
	 * leading term is 0, then the degree is -1.
	 * @return	the leading coefficient
	 */
	public int deg() {
		if (this.lc() == 0 && this.lt().getValue() == 0) {
			return -1;
		}
		return this.lc();
	}
	
	/**
	 * Generate a string representation of the polynomial
	 */
	public String toString() {
		String retval = "";
		IntegerPrime a;
		
		for (int n = 0; n < this.coefficients.size(); n += 1) {
			a = this.coefficients.get(n);
			
			if (a.getValue() > 0) {
				
				if (a.getValue() > 1 || n == 0) {
					// only display coefficient if larger than 1 or when X^0
					retval += a;
					
					// multiplication dot between coefficient and X^n
					if (n > 0) {
						retval += "·";
					}
				}
				
				// display X if n > 0
				if (n > 0) {
					retval += "X";
					
					// display ^n if n > 1
					if (n > 1) {
						retval += "^" + n;
					}
				}
				
				if (n < this.coefficients.size() - 1) {
					retval += " + ";
				}
			}
		}
		
		if (this.coefficients.size() == 1
					&& this.coefficients.get(0).getValue() == 0) {
			retval = "0";
		}
		
		return retval;
	}
}
