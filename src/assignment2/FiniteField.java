package assignment2;

import java.util.ArrayList;
import java.util.Random;

public class FiniteField {

	int p;// The integer that is the modulus for the coefficients
	Polynomial q; // The polynomial which is modded out

	public FiniteField(int p, Polynomial q) {
		if (q.getMod() != p) {
			throw new IllegalArgumentException(
					"The modulus of the polynomial is not equal to the modulus of the field");
		}

		this.q = q;
		this.p = p;
	}

	/**
	 * Function that creates all elements in this field. Uses a recursive
	 * function
	 * 
	 * @return
	 */
	public ArrayList<Polynomial> getAllElements() {
		ArrayList<Polynomial> o = new ArrayList<Polynomial>();
		ArrayList<IntegerMod> c = new ArrayList<IntegerMod>();
		for (int z = 0; z < q.getDegree(); z++) {
			c.add(z, new IntegerMod(0, p));
		}

		return getAllElementsRecursive(o, c, q.getDegree() - 1);
	}

	/**
	 * This is a recursive function that returns the input 'list', added with
	 * every polynomial with coefficients under 'prevc' with degree < i
	 * 
	 * @param list
	 * @param prevc
	 * @param i
	 * @return
	 */
	public ArrayList<Polynomial> getAllElementsRecursive(
			ArrayList<Polynomial> list, ArrayList<IntegerMod> prevc, Integer i) {

		ArrayList<IntegerMod> copyPrevc = new ArrayList<IntegerMod>(prevc);

		// Base case
		if (i == 0) {
			ArrayList<IntegerMod> backUpCopyPrevc = new ArrayList<IntegerMod>(
					copyPrevc);
			for (int j = 0; j < p; j++) {
				copyPrevc = new ArrayList<IntegerMod>(backUpCopyPrevc);
				copyPrevc.set(0, new IntegerMod(j, p));
				Polynomial newP = new Polynomial(copyPrevc, p);
				if (!listContainsPolynomial(newP, list)) {
					list.add(newP);
				}
			}
		} else { // Step case
			for (int j = 0; j < p; j++) {
				copyPrevc.set(i, new IntegerMod(j, p));
				getAllElementsRecursive(list, copyPrevc, i - 1);
			}
		}

		return list;

	}

	public Polynomial sum(Polynomial x, Polynomial y) {
		return reduce(x.sum(y));
	}

	public Polynomial product(Polynomial x, Polynomial y) {

		return reduce(x.product(y));
	}

	public Polynomial quotient(Polynomial x, Polynomial y) {

		return reduce(x.product(inverse(y)));
	}

	/**
	 * This function returns the inverse of a polynomial, if it exists in this
	 * field. Uses algorithm 2.3.3
	 * 
	 * @param a
	 *            Polynomial to find the inverse of
	 * @return The inverse of a if it exists, else NULL
	 */
	public Polynomial inverse(Polynomial a) {
		// Step 1:
		Polynomial.ExtEuclideanResult r = a.gcd(q);
		Polynomial x = r.x();

		// Step 2:
		if (r.gcd().equals(Polynomial.ONE(p))) {
			return x;
		} else {
			return null;
		}
	}

	public Polynomial reduce(Polynomial x) {
		Polynomial.DivisionResult div = x.divide(q);
		return div.rem();
	}

	public boolean primitive(Polynomial x) {

		return false;
	}

	public boolean irreducible(Polynomial x) {
		Polynomial XqtMinusX;

		for (int t = 1; t <= q.getDegree(); t += 1) {
			if (t == q.getDegree()) {
				return true;
			}

			ArrayList<IntegerMod> c = new ArrayList<IntegerMod>();
			for (int i = 0; i < Math.pow(p, t); i += 1) {
				c.add(new IntegerMod(0, p));
			}
			c.add(new IntegerMod(1, p));
			XqtMinusX = reduce(new Polynomial(c, p).difference(Polynomial.X(p)));

			if (x.gcd(XqtMinusX).gcd().equals(Polynomial.ONE(p))) {
				break;
			}
		}

		return false;
	}

	/**
	 * Creates an irreducible polynomial of degree n by randomly creating
	 * 
	 * @param n
	 * @return
	 */
	public Polynomial createIrreducible(int n) {
		ArrayList<IntegerMod> c = new ArrayList<IntegerMod>();
		Polynomial x;
		Random random = new Random();
		int nextCoefficient;

		do {
			c.clear();

			// create random coefficients
			for (int i = 0; i <= n; i += 1) {
				nextCoefficient = random.nextInt() % p;
				// highest coefficient cannot be zero, of course.
				while (i == n && nextCoefficient == 0) {
					nextCoefficient += 1;
				}
				c.add(new IntegerMod(nextCoefficient, p));
			}

			// create a polynomial
			x = new Polynomial(c, p);

			System.out.println("Created: " + x.toString());

		} while (!irreducible(x));

		System.out.println("Irreducible!");

		// stop if it's irreducible.
		return x;
	}

	/**
	 * Outputs the 'addition table' of this finite field. It will output a list
	 * of all possible sums
	 * 
	 * @return
	 */
	public String getAdditionTable() {
		ArrayList<Polynomial> x = getAllElements();
		Polynomial y, z;
		String out = "";

		for (int i = 0; i < x.size(); i++) {
			y = x.get(i);
			for (int j = 0; j < x.size(); j++) {
				z = x.get(j);
				out += (y.toString() + " + " + z.toString() + " = " + sum(y, z) + "\n");
			}
		}
		return out;
	}

	/**
	 * Outputs the 'multiplication table' of this finite field. It will output a
	 * list of all possible products
	 * 
	 * @return
	 */
	public String getMultiplicationTable() {
		ArrayList<Polynomial> x = getAllElements();
		Polynomial y, z;
		String out = "";

		for (int i = 0; i < x.size(); i++) {
			y = x.get(i);
			for (int j = 0; j < x.size(); j++) {
				z = x.get(j);
				out += (y.toString() + " * " + z.toString() + " = "
						+ product(y, z) + "\n");
			}
		}
		return out;
	}

	/**
	 * Checks if list l conatins polynomial z
	 * 
	 * @param z
	 * @param l
	 * @return
	 */
	private boolean listContainsPolynomial(Polynomial z, ArrayList<Polynomial> l) {
		boolean a = false;

		for (int i = 0; i < l.size(); i++) {
			a = true;
			for (int j = 0; j <= z.getDegree(); j++) {
				int c1 = z.getCoefficient(j).getValue();
				int c2 = l.get(i).getCoefficient(j).getValue();
				if (c1 != c2) {
					a = false;
				}
			}

			if (a) {
				return true;
			}
		}

		return false;
	}
	
	@Override
	public String toString() {
		return "Z/" + p + "Z/" + q.toString();
	}
}
