package assignment2;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class FiniteField {

	int p;// The integer that is the modulus for the coefficients
	Polynomial q; // The polynomial which is modded out
	
	public FiniteField(int p, Polynomial q) {
		if(q.getMod() != p){
			throw new IllegalArgumentException("The modulus of the polynomial is not equal to the modulus of the field");
		}
		
		this.q = q;
		this.p = p;
	}
	
	public ArrayList<Polynomial> getAllElements(){
		ArrayList<Polynomial> o = new ArrayList<Polynomial>();
		int deg = q.getDegree();
		ArrayList<IntegerMod> c = new ArrayList<IntegerMod>();
		for(int z = 0; z < q.getDegree(); z++){
			c.add(z, new IntegerMod(0, p));
		}
		
		
		
		
		
		
		return getAllElementsRecursive(o, c, q.getDegree()-1);
	}
	
	/**
	 * This is a recursive function that returns the input 'list', added with every polynomial 
	 * with coefficients under 'prevc' with degree < i
	 * @param list
	 * @param prevc
	 * @param i
	 * @return
	 */
	public ArrayList<Polynomial> getAllElementsRecursive(
							ArrayList<Polynomial> list, ArrayList<IntegerMod> prevc, Integer i){
		
		ArrayList<IntegerMod> copyPrevc = new ArrayList<IntegerMod>(prevc);
		
		//Base case
		if(i == 0){
			for(int j = 0; j < p; j++){
				copyPrevc.set(0, new IntegerMod(j, p));
				Polynomial newP = new Polynomial(copyPrevc, p);
				System.out.println("Testing " + newP);
				if( !listContainsPolynomial(newP, list)){
					list.add(newP);
					System.out.println("It got IN!!!");
				}
			}
		}else{ //Step case
			for(int j = 0; j < p; j++){
				copyPrevc.set(i, new IntegerMod(j, p));
				getAllElementsRecursive(list, copyPrevc, i-1);
			}
		}
		
		return list;
		
		
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
	
	/**
	 * Checks if list l conatins polynomial z 
	 * @param z
	 * @param l
	 * @return
	 */
	private boolean listContainsPolynomial(Polynomial z, ArrayList<Polynomial> l){
		boolean a = false;
		
		for(int i = 0; i < l.size(); i++){
			a = true;
			for(int j = 0; j < z.getDegree(); j++){
				int c1 = z.getCoefficient(j).getValue();
				int c2 = l.get(i).getCoefficient(j).getValue();
				if(c1 != c2){
					a = false;
				}
			}
			
			if(a){
				return true;
			}
		}
		
		return false;
	}
}
