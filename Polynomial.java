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
		ArrayList<IntegerMod> a, b, summ, zero;
		a = this.coefficients;
		b = x.coefficients;
		summ = new ArrayList<IntegerMod>();
		zero = ZERO(x.mod).coefficients;

		if (a.size() <= b.size()){
			for(int i = 0; i < a.size(); i++){
				summ.add((a.get(i)).add(b.get(i)));
			}
			for(int i = a.size(); i < b.size(); i++){
				summ.add((zero.get(0)).add(b.get(i)));
			}
		}

		else {
			for(int i = 0; i < b.size(); i++){
				summ.add((a.get(i)).add(b.get(i)));
			}
			for(int i = b.size(); i < a.size(); i++){
				summ.add((zero.get(0)).add(a.get(i)));
			}
		}

		return new Polynomial(summ, x.mod);
	}

	/**
	 * Produce the scalar multiple
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial scalarMultiple(int x) {
		ArrayList<IntegerMod> a, mult, scal;
		a = this.coefficients;
		mult = new ArrayList<IntegerMod>();
		scal = new ArrayList<IntegerMod>();
		scal.add(new IntegerMod(x, this.mod));

		for(int i = 0; i < a.size(); i++){
			mult.add((scal.get(0)).multiply(a.get(i)));
		}

		return new Polynomial(mult, this.mod);
	}

	/**
	 * Returns the difference with x
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial difference(Polynomial x) {
		ArrayList<IntegerMod> a, b, diff, zero;
		a = this.coefficients;
		b = x.coefficients;
		diff = new ArrayList<IntegerMod>();
		zero = ZERO(x.mod).coefficients;

		if (a.size() <= b.size()){
			for(int i = 0; i < a.size(); i++){
				diff.add((a.get(i)).subtract(b.get(i)));
			}
			for(int i = a.size(); i < b.size(); i++){
				diff.add((zero.get(0)).subtract(b.get(i)));
			}
		}

		else {
			for(int i = 0; i < b.size(); i++){
				diff.add((a.get(i)).subtract(b.get(i)));
			}
			for(int i = b.size(); i < a.size(); i++){
				diff.add((zero.get(0)).subtract(a.get(i)));
			}
		}

		return new Polynomial(diff, x.mod);
	}

	/**
	 * Returns the product with x
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial product(Polynomial x) {
		ArrayList<IntegerMod> a, b, prod, zero;
		a = this.coefficients;
		b = x.coefficients;
		prod = new ArrayList<IntegerMod>();
		zero = ZERO(x.mod).coefficients;
		IntegerMod coefj = zero.get(0);

		if(a.size() <= b.size()){
			for(int i = a.size(); i < 2 * b.size(); i++){
				a.add(zero.get(0));
			}
			for(int i = b.size(); i < 2 * b.size(); i++){
				b.add(zero.get(0));
			}
		}
		else{
			for(int i = a.size(); i < 2 * a.size(); i++){
				a.add(zero.get(0));
			}
			for(int i = b.size(); i < 2 * a.size(); i++){
				b.add(zero.get(0));
			}
		}

		if(a.size() <= b.size()){
			for(int i = 0; i < 2 * b.size(); i++){
				coefj = zero.get(0);
				for(int j = 0; j <= i; j++) {
					coefj = coefj.add((a.get(j)).multiply(b.get(i - j)));
				}
				prod.add(coefj);
			}
		}
		else{
			for(int i = 0; i < 2 * a.size(); i++){
				coefj = zero.get(0);
				for(int j = 0; j <= i; j++) {
					coefj = coefj.add((a.get(j)).multiply(b.get(i - j)));
				}
				prod.add(coefj);
			}
		}

		return new Polynomial(prod, x.mod);
	}
	
	/**
	 * 
	 * @param b		the polynomial to divide by
	 * @return		(quot,rem) such that (this == quot * b + rem)
	 */
	public Polynomial.DivisionResult divide(Polynomial b) {
		Polynomial q, r, lcDiv;
		q = ZERO(b.mod);
		r = this;

		while (r.coefficients.size() >= b.coefficients.size()){
			int lcr, lcb;
			lcr = r.coefficients.get(r.coefficients.size() - 1).getValue();
			lcb = b.coefficients.get(b.coefficients.size() - 1).getValue();
			ArrayList<IntegerMod> lc = new ArrayList<IntegerMod>();
			lc.add(new IntegerMod(lcr / lcb, this.mod));
			lcDiv = new Polynomial(lc, this.mod);

			q = q.sum(lcDiv);
			r = r.difference(lcDiv.product(b));
		}

		return new Polynomial.DivisionResult(q, r);
	}
	
	/**
	 * Calculates the greatest common divider of polynomials this and other
	 * via the Extended Euclidean Algorithm (1.2.11)
	 * 
	 * @param other	the other polynomial
	 * @return		gcd, x and y such that this * x + other * y = gcd
	 */
	public Polynomial.ExtEuclideanResult gcd(Polynomial other) {
		Polynomial a, b, x, y, xp, yp, u, v, q, zero;
		a = this;
		b = other;
		zero = ZERO(this.mod);
		x = ONE(this.mod);
		v = ONE(this.mod);
		y = zero;
		u = zero;

		while (b.coefficients.get(b.coefficients.size() - 1) != zero.coefficients.get(0)){
			Polynomial.DivisionResult divi = a.divide(b);
			q = divi.quot;
			a = b;
			b = divi.rem;
			xp = x;
			yp = y;
			x = u;
			y = v;
			u = xp.difference(q.product(u));
			v = yp.difference(q.product(v));
		}

		return new Polynomial.ExtEuclideanResult(a, x, y);
	}
	
	/**
	 * Checks if polyonomial x is equal to this polynomial
	 * @param x
	 * @return equality of the input and this polynomial
	 */
	public boolean equals(Polynomial x){
		ArrayList<IntegerMod> a, b;
		a = this.coefficients;
		b = x.coefficients;

		if (a.size() != b.size())
			return false;
		else{
			for(int i = 0; i < a.size(); i++){
				if (a.get(i).getValue() != b.get(i).getValue()){
					return false;
				}
			}
		}
		return true;
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

	/**
	 * Used to get a 0 polynomial in an easy way.a
	 * @param m
	 * @return The 0 polynomial, with modulus m
	 */

	public static Polynomial ZERO(int m){
		ArrayList<IntegerMod> o = new ArrayList<IntegerMod>();
		o.add(new IntegerMod(0, m));
		Polynomial zero = new Polynomial(o, m);
		return zero;
	}

}
