package assignment2;

import java.util.ArrayList;

public class Polynomial {

	// Coefficients are ordered by significance, ie coefficients[0] = least significant coefficient
	private ArrayList<IntegerMod> coefficients;
	private int mod;
	
	/**
	 * Constructs an empty polynomial
	 * @param m 	The modulus
	 */
	public Polynomial(int m){
		
		mod = m;
		coefficients = new ArrayList<IntegerMod>();
		coefficients.add(new IntegerMod(0, m));
	}
	
	/**
	 * Constructs Polynomial from arraylist of coefficients
	 * @param c 	Coefficients
	 * @param m		Modulus
	 */
	public Polynomial(ArrayList<IntegerMod> c, int m){
		
		mod = m;
		coefficients = c;
	}
	
	/**
	 * Produce the sum of the polynomials
	 * @param x
	 * @return
	 */
	public Polynomial sum(Polynomial x){
		return null;
	}
	
	/**
	 * Produce the scalar multiple
	 * @param x
	 * @return
	 */
	public Polynomial scalarMultiple(int x){
		return null;
	}
	
	/**
	 * Returns the difference with x
	 * @param x
	 * @return
	 */
	public Polynomial difference(Polynomial x){
		return null;
	}
	
	/**
	 * Returns the product with x
	 * @param x
	 * @return
	 */
	public Polynomial product(Polynomial x){
		return null;
	}
}
