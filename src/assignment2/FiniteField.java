package assignment2;

import java.util.ArrayList;

public class FiniteField {

	int p;
	Polynomial q;
	
	public FiniteField(int p, Polynomial q) {
		if(q.getMod() != p){
			throw new IllegalArgumentException("The modulus of the polynomial is not equal to the modulus of the field");
		}
		
		this.q = q;
		this.p = p;
	}
	
	public ArrayList<Polynomial> getAllElements(){
		
		return null;
	}
	
	public Polynomial sum(Polynomial x, Polynomial y){		
		return reduce(x.sum(y)); 
	}
	
	public Polynomial product(Polynomial x, Polynomial y){
		
		return reduce(x.product(y)); 
	}

	public Polynomial quotient(Polynomial x, Polynomial y){
		
		return reduce(x.product(inverse(y))); 
	}
	
	/**
	 * This function returns the inverse of a polynomial, if it exists in this field. 
	 * Uses algorithm 2.3.3
	 * @param a 	Polynomial to find the inverse of
	 * @return		The inverse of a if it exists, else NULL
	 */
	public Polynomial inverse(Polynomial a){
		// Step 1:
		Polynomial.ExtEuclideanResult r = a.gcd(q);
		Polynomial x = r.x(), y = r.y();
		
		// Step 2:		
		if(r.gcd().equals(Polynomial.ONE(p))){
			return x;
		}else{
			return null;
		}
	}
	
	private Polynomial reduce(Polynomial x){
		return null;
	}
	
	public boolean primitive(Polynomial x){
		
		return false;
	}
	
	public boolean irreducible(Polynomial x){
		
		return false;
	}
	
	public Polynomial createIrreducible(int n){
		
		return null;
	}
}
