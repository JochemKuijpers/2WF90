package assignment2;

import java.util.ArrayList;

public class Runner {

	public static void main(String[] args) {
		int m = 2;
		ArrayList<IntegerMod> q = new ArrayList<IntegerMod>();
		q.add(new IntegerMod(1, m));
		q.add(new IntegerMod(1, m));
		q.add(new IntegerMod(1, m));
		Polynomial p = new Polynomial(q, m);
		// P should now b e x^2 + x + 1
		
	
		FiniteField f = new FiniteField(m, p);
		//System.out.println("There are " + r.size() + " elements found!");
		ArrayList<Polynomial> r = f.getAllElements();
		
		System.out.println(f.getMultiplicationTable());
	}
}
