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
		q.remove(2);
		Polynomial p2 = new Polynomial(q, m);
		
		System.out.println(p.divide(Polynomial.ONE(m)));
		
		/*for(int i = 0; i < r.size(); i++){
			//System.out.println(r.get(i).toString());
		
		}
		
		System.out.println("There are " + r.size() + " elements found!");
		
		System.out.println(f.getMultiplicationTable());*/
	}
}
