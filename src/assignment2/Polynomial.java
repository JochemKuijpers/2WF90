package assignment2;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Polynomial {

	// Coefficients are ordered by significance, ie coefficients[0] = least
	// significant coefficient
	public ArrayList<IntegerMod> coefficients;
	public int mod;

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
	 * Clones a polynomial.
	 * 
	 * @param x
	 *            polynomial to clone
	 */
	public Polynomial(Polynomial x) {
		this.mod = x.mod;
		this.coefficients = new ArrayList<IntegerMod>();
		for (IntegerMod i : x.getCoefficients()) {
			this.coefficients.add(new IntegerMod(i));
		}
	}

	/**
	 * A subclass containing division results quot and rem, the quotient and
	 * remainder of the division Used from last year by Jochem Kuijpers
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
	 * A subclass containing results of the Extended Euclidean Algorithm. Used
	 * from last year by Jochem Kuijpers
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
		a = this.getCoefficients();
		b = x.getCoefficients();
		summ = new ArrayList<IntegerMod>();
		// the zero coefficient to be used when one array has a higher degree
		// than the other
		zero = ZERO(x.getMod()).getCoefficients();

		// start the addition of the coefficients
		// when the first polynomial has a lower/equal degree than/to the second
		if (a.size() <= b.size()) {
			for (int i = 0; i < a.size(); i++) {
				summ.add((a.get(i)).add(b.get(i)));
			}
			for (int i = a.size(); i < b.size(); i++) {
				summ.add((zero.get(0)).add(b.get(i)));
			}
		}

		// when the second polynomial has a lower than the first
		else {
			for (int i = 0; i < b.size(); i++) {
				summ.add((a.get(i)).add(b.get(i)));
			}
			for (int i = b.size(); i < a.size(); i++) {
				summ.add((zero.get(0)).add(a.get(i)));
			}
		}

		// return the sum of the polynomials
		return new Polynomial(summ, x.getMod());
	}

	/**
	 * Produce the scalar multiple
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial scalarMultiple(int x) {

		ArrayList<IntegerMod> a, mult, scal;
		a = this.getCoefficients();
		mult = new ArrayList<IntegerMod>();
		scal = new ArrayList<IntegerMod>();
		scal.add(new IntegerMod(x, this.getMod()));

		// the multiplication of the coefficients
		for (int i = 0; i < a.size(); i++) {
			mult.add((scal.get(0)).multiply(a.get(i)));
		}

		// return the scalar multiple
		return new Polynomial(mult, this.getMod());
	}

	/**
	 * Returns the difference with x
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial difference(Polynomial x) {

		ArrayList<IntegerMod> a, b, diff, zero;
		a = this.getCoefficients();
		b = x.getCoefficients();
		diff = new ArrayList<IntegerMod>();
		zero = ZERO(x.getMod()).getCoefficients();

		// start the subtraction of the coefficients
		// when the first polynomial has a lower/equal degree than/to the second
		if (a.size() <= b.size()) {
			for (int i = 0; i < a.size(); i++) {
				diff.add((a.get(i)).subtract(b.get(i)));
			}
			for (int i = a.size(); i < b.size(); i++) {
				diff.add((zero.get(0)).subtract(b.get(i)));
			}
		}

		// when the second polynomial has a lower degree than the first
		else {
			for (int i = 0; i < b.size(); i++) {
				diff.add((a.get(i)).subtract(b.get(i)));
			}
			for (int i = b.size(); i < a.size(); i++) {
				diff.add((zero.get(0)).subtract(a.get(i)));
			}
		}

		// return the difference of the polynomials
		return new Polynomial(diff, x.getMod());
	}

	/**
	 * Returns the product with x
	 * 
	 * @param x
	 * @return
	 */
	public Polynomial product(Polynomial x) {

		ArrayList<IntegerMod> a, b, prod, zero;
		a = this.getCoefficients();
		b = x.getCoefficients();
		prod = new ArrayList<IntegerMod>();
		// in order to extend the coefficient lists
		zero = ZERO(x.getMod()).getCoefficients();

		IntegerMod coefj = zero.get(0);

		// extending the coefficient lists when the first polynomial has a
		// lower/equal degree than/to the second
		if (a.size() <= b.size()) {
			int size = b.size();
			for (int i = a.size(); i < 2 * size - 1; i++) {
				a.add(zero.get(0));
			}
			for (int i = size; i < 2 * size - 1; i++) {
				b.add(zero.get(0));
			}
		}
		// extending the coefficient lists when the second polynomial has a
		// lower degree than the first
		else {
			int size = a.size();
			for (int i = size; i < 2 * size - 1; i++) {
				a.add(zero.get(0));
			}
			for (int i = b.size(); i < 2 * size - 1; i++) {
				b.add(zero.get(0));
			}
		}

		// starting the multiplication of the coefficients
		// when the first polynomial has a lower/equal degree than/to the second
		if (a.size() <= b.size()) {
			int size = b.size();
			for (int i = 0; i < size; i++) {
				coefj = zero.get(0);
				for (int j = 0; j <= i; j++) {
					coefj = coefj.add((a.get(j)).multiply(b.get(i - j)));
				}
				prod.add(coefj);
			}
		}
		// when the second polynomial has a lower degree than the first
		else {
			int size = a.size();
			for (int i = 0; i < size; i++) {
				coefj = zero.get(0);
				for (int j = 0; j <= i; j++) {
					coefj = coefj.add((a.get(j)).multiply(b.get(i - j)));
				}
				prod.add(coefj);
			}
		}

		// return the product of the two polynomials
		return new Polynomial(prod, x.getMod());
	}

	/**
	 * 
	 * @param b
	 *            the polynomial to divide by
	 * @return (quot,rem) such that (this == quot * b + rem)
	 */
	public Polynomial.DivisionResult divide(Polynomial b) {

		Polynomial q, r;
		q = ZERO(b.getMod());
		r = this;

		while (r.getDegree() >= b.getDegree()) {

			// the leading coefficients of the (current) r and the second
			// polynomial
			// the division of the two leading coefficients
			IntegerMod lcb = b.getCoefficients().get(b.getDegree());
			IntegerMod lcr = r.getCoefficients().get(r.getDegree());
			IntegerMod lc = lcr.divide(lcb);

			// the difference of the degrees of the (current) r and the second
			// polynomial
			int degdiff = (r.getDegree() - b.getDegree());

			// a list to be used in creating a polynomial of degree degdiff with
			// the all other coefficients 0
			ArrayList<IntegerMod> xdeg = new ArrayList<IntegerMod>();
			for (int i = 0; i <= degdiff; i++) {
				if (i < degdiff) {
					xdeg.add(ZERO(b.getMod()).getCoefficient(0));
				} else
					xdeg.add(ONE(b.getMod()).getCoefficient(0));
			}

			// when r and the second polynomial have the same degrees create
			// polynomial 1
			if (degdiff == 0) {
				xdeg = ONE(b.getMod()).getCoefficients();
			}

			// the polynomial of degree degdiff with all other coefficients 0
			Polynomial xpower = new Polynomial(xdeg, b.getMod());

			q = q.sum(xpower.scalarMultiple(lc.getValue()));
			r = r.difference((xpower.scalarMultiple(lc.getValue())).product(b));
		}

		// return the quotient and the remainder of the division
		return new Polynomial.DivisionResult(q, r);
	}

	/**
	 * Calculates the greatest common divider of polynomials this and other via
	 * the Extended Euclidean Algorithm (1.2.11)
	 * 
	 * @param other
	 *            the other polynomial
	 * @return gcd, x and y such that this * x + other * y = gcd
	 */
	public Polynomial.ExtEuclideanResult gcd(Polynomial other) {

		Polynomial a, b, x, y, xp, yp, u, v, q, zero;
		a = this;
		b = other;
		zero = ZERO(this.getMod());
		x = ONE(this.getMod());
		v = ONE(this.getMod());
		y = zero;
		u = zero;

		// the Extended Euclidean Algorithm
		while (b.getDegree() != 0) {
			Polynomial.DivisionResult divi = a.divide(b);
			q = divi.quot();
			a = b;
			b = divi.rem();
			xp = x;
			yp = y;
			x = u;
			y = v;
			u = xp.difference(q.product(u));
			v = yp.difference(q.product(v));
		}

		// return the result
		return new Polynomial.ExtEuclideanResult(a, x, y);
	}

	/**
	 * Checks if polyonomial x is equal to this polynomial
	 * 
	 * @param x
	 * @return equality of the input and this polynomial
	 */
	public boolean equals(Polynomial x) {

		ArrayList<IntegerMod> a, b;
		a = this.getCoefficients();
		b = x.getCoefficients();

		// check if the two polynomials are equal
		if (this.getDegree() != x.getDegree())
			// they can't be equal if they don't have the same degree
			return false;
		else {
			for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
				if (a.get(i).getValue() != b.get(i).getValue()) {
					// when the two coefficients for the same power of X differ
					return false;
				}
			}
		}

		// if they have the same degree and all coefficients are the same, then
		// they are equal
		return true;
	}

	/**
	 * Checks if polyonomial x is equal to this polynomial modulo polynomial y
	 * 
	 * @param x
	 *            , y
	 * @return equality of the input and polynomial x modulo polynomial y
	 */
	public boolean equalsModulo(Polynomial x, Polynomial y) {

		Polynomial a, b, k;
		a = new Polynomial(this);
		b = new Polynomial(x);
		k = new Polynomial(y);

		Polynomial.DivisionResult equalitycheck;

		// from 2.1.3 symmetry
		equalitycheck = (a.difference(b)).divide(k);

		// if the remainder is 0, then the two polynomials are equal modulo the
		// third
		if (equalitycheck.rem().getCoefficient(equalitycheck.rem().getDegree())
				.getValue() == 0)
			return true;
		return false;
	}

	/**
	 * Used to get a 1 polynomial in an easy way
	 * 
	 * @param m
	 * @return The 1 polynomial, with modulus m
	 */
	public static Polynomial ONE(int m) {
		ArrayList<IntegerMod> o = new ArrayList<IntegerMod>();
		o.add(new IntegerMod(1, m));
		Polynomial one = new Polynomial(o, m);
		return one;
	}

	/**
	 * Used to get a X polynomial in an easy way
	 * 
	 * @param m
	 * @return The X polynomial, with modulus m
	 */
	public static Polynomial X(int m) {
		ArrayList<IntegerMod> o = new ArrayList<IntegerMod>();
		o.add(new IntegerMod(0, m));
		o.add(new IntegerMod(1, m));
		Polynomial one = new Polynomial(o, m);
		return one;
	}

	/**
	 * Used to get a 0 polynomial in an easy way
	 * 
	 * @param m
	 * @return The 0 polynomial, with modulus m
	 */

	public static Polynomial ZERO(int m) {
		ArrayList<IntegerMod> o = new ArrayList<IntegerMod>();
		o.add(new IntegerMod(0, m));
		Polynomial zero = new Polynomial(o, m);
		return zero;
	}

	public int getDegree() {
		for (int i = coefficients.size() - 1; i >= 0; i--) {
			if (coefficients.get(i).getValue() != 0) {
				return i;
			}
		}

		return 0;
	}

	/**
	 * Give away a copy of the coefficients because we don't want other classes
	 * to change our coefficients. Used from last year by Jochem Kuijpers
	 * 
	 * @return ArrayList with coefficients
	 */
	public ArrayList<IntegerMod> getCoefficients() {
		ArrayList<IntegerMod> copy = new ArrayList<IntegerMod>();

		for (IntegerMod a : coefficients) {
			copy.add(a);
		}

		return copy;
	}

	public IntegerMod getCoefficient(int x) {
		if (x >= coefficients.size()) {
			throw new IllegalArgumentException("The coefficient with degree "
					+ x + " can not be found");
		} else {

			return coefficients.get(x);
		}

	}

	public String toString() {
		
		if (this.equals(Polynomial.ZERO(mod))) {
			return "(0)";
		}
		
		StringJoiner components = new StringJoiner(" + ");
		
		for (int i = coefficients.size() - 1; i >= 0; i--) {
			int c = coefficients.get(i).getValue();
			if (c > 1) {
				if (i > 1) {
					components.add(c + "×X^" + i);					
				}
				if (i == 1) {
					components.add(c + "×X");
				}
				if (i == 0) {
					components.add(""+c);
				}
			}
			if (c == 1) {
				if (i > 1) {
					components.add("X^" + i);					
				}
				if (i == 1) {
					components.add("X");
				}
				if (i == 0) {
					components.add("1");
				}
			}
		}

		return "(" + components.toString() + ")";
	}
}
