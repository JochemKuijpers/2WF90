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
		
		return null; 
	}
	
	public Polynomial product(Polynomial x, Polynomial y){
		
		return null; 
	}

	public Polynomial quotient(Polynomial x, Polynomial y){
		
		return null; 
	}
	
	public Polynomial inverse(Polynomial x){
		
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
