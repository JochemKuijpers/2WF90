package assignment2;

import java.util.ArrayList;

public class Runner {

	public static void main(String[] args) {
		int m = 3;
		ArrayList<IntegerMod> q = new ArrayList<IntegerMod>();
		q.add(new IntegerMod(1, m));
		q.add(new IntegerMod(1, m));
		q.add(new IntegerMod(1, m));
		Polynomial p = new Polynomial(q, m);
		// P should now b e x^2 + x + 1
		
		FiniteField f = new FiniteField(m, p);
		ArrayList<Polynomial> r = f.getAllElements();
		System.out.println("ready?");
		
		for(int i = 0; i < r.size(); i++){
			System.out.println(r.get(i).toString());
		}
	}
}
