package cheatsheet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class containing the main method
 * 
 * Jochem Kuijpers
 * 0838617 - 26 October 2015
 */
public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		Main.doShowMenu(scanner);
		
		Utils.printHr();
		Utils.printWrapped("");
		Utils.printWrapped("DONE.");
		
		scanner.close();
	}
	
	/**
	 * Shows a menu with items which you can select.
	 * 
	 * @param scanner	the input scanner
	 */
	private static void doShowMenu(Scanner scanner) {
		int choice;
		
		do {
			Utils.printHr(); // ---
			Utils.printWrapped(" Menu");
			Utils.printHr(); // ---
			Utils.printWrapped("");
			Utils.printWrapped("What do you want to do?");
			Utils.printWrapped("");
			Utils.printWrapped(" 1: Calculations on two polynomials with"
					+ " coefficients modulo a prime");
			Utils.printWrapped(" 2: Calculate quotient and remainder on two"
					+ " polynomials");
			Utils.printWrapped(" 3: (Extended) Euclidean algorithm on two"
					+ " polynomials");
			Utils.printWrapped(" 4: Check whether or not two polynomials are"
					+ " equal modulo a third polynomial");
			Utils.printWrapped("");
			Utils.printWrapped(" 5: Produce addition and multiplication table"
					+ " of Z/pZ[X]/q(X)");
			Utils.printWrapped(" 6: Calculations on two field elements");
			Utils.printWrapped(" 7: Check primitivity of a field element");
			Utils.printWrapped(" 8: Test irreducibility of a polynomial mod p"
					+ " and produce irreducible");
			Utils.printWrapped("    polynomials of a prescribed degree");
			Utils.printWrapped("");
			Utils.printWrapped(" 0: Exit");
			Utils.printWrapped("");
			Utils.printWrapped("Enter your choice: ", System.out, false);
			
			choice = scanner.nextInt();
			
			// skip the rest of the line
			scanner.nextLine();
			
			Utils.printWrapped("");
			
			switch (choice) {
			case 0:
				break;
			case 1:
				Main.doPolynomialCalculations(scanner);
				break;
			case 2:
				Main.doPolynomialDivision(scanner);
				break;
			case 3:
				Main.doPolynomialExtendedEuclidean(scanner);
				break;
			case 4:
				Main.doPolynomialModuloPolynomial(scanner);
				break;
			default:
				Utils.printWrapped("Not yet implemented / invalid choice.",
						System.err);
				Utils.printWrapped("");
				break;
			}
			
			if (choice != 0) {
				// sleep a second after every result so it is clear the user
				// can advance
				Utils.sleep(1);
				Utils.printWrapped("Press ENTER to return to Menu...");
				scanner.nextLine();
			}
			
		} while (choice != 0);
	}

	/**
	 * Asks for a prime number, an order n, a scalar s and two polynomials and
	 * shows the sum, scalar multiple, difference and product 
	 * 
	 * @param scanner	the input scanner
	 */
	private static void doPolynomialCalculations(Scanner scanner) {
		int prime, order, scalar;
		Polynomial a, b, s, c;
		
		
		
		// INPUT
		
		Utils.printHr(); // ---
		Utils.printWrapped(" Polynomial calculations");
		Utils.printHr(); // ---
		Utils.printWrapped("");
		
		Utils.printWrapped("Enter a prime number p to form the set Z/pZ for"
				+ " the coefficients of the polynomials:");
		Utils.printWrapped("");
		prime = Utils.getNumber(scanner, 2, 1000, true);
		
		Utils.printWrapped("Enter the order of the polynomials:");
		Utils.printWrapped("");
		order = Utils.getNumber(scanner, 0, 100, false);
		
		
		Utils.printWrapped("Enter polynomial a:");
		Utils.printWrapped("");
		a = Utils.getPolynomial(scanner, order, prime);
		
		Utils.printWrapped("Enter polynomial b:");
		Utils.printWrapped("");
		b = Utils.getPolynomial(scanner, order, prime);
		
		Utils.printWrapped("Enter a scalar value s:");
		Utils.printWrapped("");
		scalar = Utils.getNumber(scanner, 0, prime-1, false);
		
		// construct a constant polynomial from the scalar value 
		ArrayList<IntegerPrime> coefficients = new ArrayList<IntegerPrime>();
		coefficients.add(new IntegerPrime(scalar, prime));
		s = new Polynomial(coefficients, prime);

		
		
		// SUM
		c = a.add(b);
		
		Utils.printWrapped("Sum:");
		Utils.printWrapped("a: " + a);
		Utils.printWrapped("b: " + b);
		Utils.printOperandLine("+");
		Utils.printWrapped("c: " + c);
		Utils.printWrapped("");
		
		
		
		// SCALAR VALUE
		
		c = a.multiply(s);
		
		Utils.printWrapped("Scalar multiple:");
		Utils.printWrapped("a: " + a);
		Utils.printWrapped("s: " + s);
		Utils.printOperandLine("×");
		Utils.printWrapped("c: " + c);
		Utils.printWrapped("");
		
		
		
		// DIFFERENCE
		c = a.subtract(b);
		
		Utils.printWrapped("Difference:");
		Utils.printWrapped("a: " + a);
		Utils.printWrapped("b: " + b);
		Utils.printOperandLine("-");
		Utils.printWrapped("c: " + c);
		Utils.printWrapped("");
		
		
		
		
		// PRODUCT
		c = a.multiply(b);
		
		Utils.printWrapped("Product:");
		Utils.printWrapped("a: " + a);
		Utils.printWrapped("b: " + b);
		Utils.printOperandLine("×");
		Utils.printWrapped("c: " + c);
		Utils.printWrapped("");
		
	}
	
	/**
	 * Asks for a prime number, an order n, a scalar s and two polynomials and
	 * shows the sum, scalar multiple, difference and product 
	 * 
	 * @param scanner	the input scanner
	 */
	private static void doPolynomialDivision(Scanner scanner) {
		int prime, order;
		Polynomial a, b;
		Polynomial.DivisionResult result;
		
		
		
		// INPUT
		
		Utils.printHr(); // ---
		Utils.printWrapped(" Polynomial division");
		Utils.printHr(); // ---
		Utils.printWrapped("");
		
		Utils.printWrapped("Enter a prime number p to form the set Z/pZ for"
				+ " the coefficients of the polynomials:");
		Utils.printWrapped("");
		prime = Utils.getNumber(scanner, 2, 1000, true);
		
		Utils.printWrapped("Enter the order of the polynomials:");
		Utils.printWrapped("");
		order = Utils.getNumber(scanner, 0, 100, false);
		
		
		Utils.printWrapped("Enter polynomial a:");
		Utils.printWrapped("");
		a = Utils.getPolynomial(scanner, order, prime);
		
		Utils.printWrapped("Enter polynomial b:");
		Utils.printWrapped("");
		b = Utils.getPolynomial(scanner, order, prime);
		
		
		
		// CALCULATION
		
		result = a.divide(b);
		
		
		
		// OUTPUT
		
		Utils.printWrapped("a = quot * b + rem");
		Utils.printWrapped("");
		Utils.printWrapped("   a: " + a);
		Utils.printWrapped("quot: " + result.quot());
		Utils.printWrapped("   b: " + b);
		Utils.printWrapped(" rem: " + result.rem());
		Utils.printWrapped("");
		
	}
	

	private static void doPolynomialExtendedEuclidean(Scanner scanner) {
		int prime, order;
		Polynomial a, b;
		Polynomial.ExtEuclideanResult result;
		
		
		
		// INPUT
		
		Utils.printHr(); // ---
		Utils.printWrapped(" Polynomial Extended Euclidean Algorithm");
		Utils.printHr(); // ---
		Utils.printWrapped("");
		
		Utils.printWrapped("Enter a prime number p to form the set Z/pZ for"
				+ " the coefficients of the polynomials:");
		Utils.printWrapped("");
		prime = Utils.getNumber(scanner, 2, 1000, true);
		
		Utils.printWrapped("Enter the order of the polynomials:");
		Utils.printWrapped("");
		order = Utils.getNumber(scanner, 0, 100, false);
		
		
		Utils.printWrapped("Enter polynomial a:");
		Utils.printWrapped("");
		a = Utils.getPolynomial(scanner, order, prime);
		
		Utils.printWrapped("Enter polynomial b:");
		Utils.printWrapped("");
		b = Utils.getPolynomial(scanner, order, prime);
		
		
		
		// CALCULATION
		
		result = a.gcd(b);
		
		
		
		// OUTPUT
		
		Utils.printWrapped("a * x + b * y = gcd");
		Utils.printWrapped("");
		Utils.printWrapped("  a: " + a);
		Utils.printWrapped("  x: " + result.x());
		Utils.printWrapped("  b: " + b);
		Utils.printWrapped("  y: " + result.y());
		Utils.printWrapped("gcd: " + result.gcd());
		Utils.printWrapped("");
	}
	

	private static void doPolynomialModuloPolynomial(Scanner scanner) {
		int prime, order;
		Polynomial a, b, d;
		Polynomial.DivisionResult result;
		
		
		
		// INPUT
		
		Utils.printHr(); // ---
		Utils.printWrapped(" Polynomials modulo a polynomial");
		Utils.printHr(); // ---
		Utils.printWrapped("");
		
		Utils.printWrapped("Enter a prime number p to form the set Z/pZ for"
				+ " the coefficients of the polynomials:");
		Utils.printWrapped("");
		prime = Utils.getNumber(scanner, 2, 1000, true);
		
		Utils.printWrapped("Enter the order of the polynomials:");
		Utils.printWrapped("");
		order = Utils.getNumber(scanner, 0, 100, false);
		
		
		Utils.printWrapped("Enter polynomial a:");
		Utils.printWrapped("");
		a = Utils.getPolynomial(scanner, order, prime);
		
		Utils.printWrapped("Enter polynomial b:");
		Utils.printWrapped("");
		b = Utils.getPolynomial(scanner, order, prime);
		
		Utils.printWrapped("Enter polynomial d:");
		Utils.printWrapped("");
		d = Utils.getPolynomial(scanner, order, prime);
		
		
		
		// CALCULATION
		
		// result = (a-b)/d
		result = a.subtract(b).divide(d);
		
		
		
		// OUTPUT
		
		Utils.printWrapped("(a - b) = quot * b + rem");
		Utils.printWrapped("");
		Utils.printWrapped("   a: " + a);
		Utils.printWrapped("   b: " + b);
		Utils.printWrapped("");
		Utils.printWrapped("   d: " + d);
		Utils.printWrapped("");
		if (result.rem().lt().getValue() == 0) {
			Utils.printWrapped("a and b are equal modulo d");
		} else {
			Utils.printWrapped("a and b are not equal modulo d");
		}
		Utils.printWrapped("");
		Utils.printWrapped("quot: " + result.quot());
		Utils.printWrapped(" rem: " + result.rem());
		Utils.printWrapped("");
	}

}
