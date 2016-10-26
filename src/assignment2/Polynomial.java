package assignment2;

import java.util.ArrayList;

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
		a = this.getCoefficients();
		b = x.getCoefficients();
		summ = new ArrayList<IntegerMod>();
		zero = ZERO(x.getMod()).getCoefficients();

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

		for(int i = 0; i < a.size(); i++){
			mult.add((scal.get(0)).multiply(a.get(i)));
		}

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
		zero = ZERO(x.getMod()).getCoefficients();
		IntegerMod coefj = zero.get(0);

		if(a.size() <= b.size()){
			int size = b.size();
			for(int i = a.size(); i < 2 * size - 1; i++){
				a.add(zero.get(0));
			}
			for(int i = size; i < 2 * size - 1; i++){
				b.add(zero.get(0));
			}
		}
		else{
			int size = a.size();
			for(int i = size; i < 2 * size - 1; i++){
				a.add(zero.get(0));
			}
			for(int i = b.size(); i < 2 * size - 1; i++){
				b.add(zero.get(0));
			}
		}
		if(a.size() <= b.size()){
			int size = b.size();
			for(int i = 0; i < size; i++){
				coefj = zero.get(0);
				for(int j = 0; j <= i; j++) {
					coefj = coefj.add((a.get(j)).multiply(b.get(i - j)));
				}
				prod.add(coefj);
			}
		}
		else{
			int size = a.size();
			for(int i = 0; i < size; i++){
				coefj = zero.get(0);
				for(int j = 0; j <= i; j++) {
					coefj = coefj.add((a.get(j)).multiply(b.get(i - j)));
				}
				prod.add(coefj);
			}
		}
		return new Polynomial(prod, x.getMod());

	}
	
	/**
	 * 
	 * @param b		the polynomial to divide by
	 * @return		(quot,rem) such that (this == quot * b + rem)
	 */
	public Polynomial.DivisionResult divide(Polynomial b) {
		Polynomial q, r;
		q = ZERO(b.getMod());
		r = this;


		while (r.getDegree() >= b.getDegree()){
			int lcb = b.getCoefficients().get(b.getDegree()).getValue();
			int lcr = r.getCoefficients().get(r.getDegree()).getValue();
			int lc = lcr/lcb;
			int degdiff = (r.getDegree() - b.getDegree());
			Polynomial xdeg = ZERO(b.getMod());
			for (int i = 0; i < degdiff - 1; i++)
				xdeg.getCoefficients().add(new IntegerMod(0, b.getMod()));
			xdeg.getCoefficients().add(new IntegerMod(1, b.getMod()));
			if(degdiff == 0)
				xdeg = ONE(b.getMod());
			q = q.sum(xdeg.scalarMultiple(lc));
			r = r.difference((xdeg.scalarMultiple(lc)).product(b));
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
		zero = ZERO(this.getMod());
		x = ONE(this.getMod());
		v = ONE(this.getMod());
		y = zero;
		u = zero;

		while (b.getDegree() != 0){
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
		a = this.getCoefficients();
		b = x.getCoefficients();

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
	 * Checks if polyonomial x is equal to this polynomial modulo polynomial y
	 * @param x, y
	 * @return equality of the input and polynomial x modulo polynomial y
	 */
	public boolean equalsModulo(Polynomial x, Polynomial y){
		Polynomial a, b, k;
		a = this;
		b = x;
		k = y;
		Polynomial.DivisionResult equalitycheck;

		equalitycheck = (a.difference(b)).divide(k);

		if(equalitycheck.rem().getCoefficient(equalitycheck.rem().getDegree()).getValue() == 0)
			return true;
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

	public int getDegree(){
		for(int i = coefficients.size() - 1; i >= 0; i--){
			if(coefficients.get(i).getValue() != 0){
				return i;
			}
		}
		
		return 0;
	}

	/**
	 * Give away a copy of the coefficients because we don't want other classes
	 * to change our coefficients.
	 * Used from last year by Jochem Kuijpers
	 * @return ArrayList with coefficients
	 */
	public ArrayList<IntegerMod> getCoefficients() {
		ArrayList<IntegerMod> copy = new ArrayList<IntegerMod>();

		for (IntegerMod a : coefficients) {
			copy.add(a);
		}

		return copy;
	}
	
	public IntegerMod getCoefficient(int x){
		if(x >= coefficients.size()){
			throw new IllegalArgumentException("The coefficient with degree " + x + " can not be found"); 
		}else{
			
			return coefficients.get(x);
		}
		
	}
	
	public String toString(){
		String r = "[";
		boolean f = true;
		for(int i = coefficients.size() - 1; i >= 0 ; i--){
			if(!f){
				r += " ";
			}else{
				f = false;
			}
			r += coefficients.get(i).getValue();
		}
		
		r += "]";
		return r;
	}
}