package assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import assignment2.Polynomial.DivisionResult;
import assignment2.Polynomial.ExtEuclideanResult;

public class Runner {

	public static void main(String[] args) {
		new Runner().run();

	}

	public void run() {
		printHeader();
		Scanner scanner = new Scanner(System.in);
		String choice = printMenu(scanner);

		switch (choice) {
		case "q":
		case "Q":
			break;
		case "1":
			doPolynomialMath(scanner);
			break;
		case "2":
			doPolynomialDivision(scanner);
			break;
		case "3":
			doPolynomialModularEquality(scanner);
			break;
		case "4":
			doFiniteFieldTables(scanner);
			break;
		case "5":
			doFiniteFieldElementsMath(scanner);
			break;
		case "6":
			doFiniteFieldIrreducibility(scanner);
			break;
		}

		println();
		println("DONE.");
	}

	private void doPolynomialMath(Scanner scanner) {
		println("= 1. Polynomial Math =======================================");
		println();

		println("Enter the prime number for the coefficients.");
		int prime = inputPrime(scanner);

		println("Enter the first polynomial.");
		Polynomial a = inputPolynomial(scanner, prime);

		println("Enter the second polynomial.");
		Polynomial b = inputPolynomial(scanner, prime);

		println("Enter a scalar factor.");
		int s = inputInteger(scanner);

		// Calculations
		Polynomial sum = new Polynomial(a).sum(b);
		Polynomial scalar = new Polynomial(a).scalarMultiple(s);
		Polynomial diff = new Polynomial(a).difference(b);
		Polynomial product = new Polynomial(a).product(b);

		// Results
		println("= Results ==================================================");
		println();
		println("Integer coefficients modulo " + prime);
		println("a(X) = " + a.toString());
		println("b(X) = " + b.toString());
		println("s = " + s);
		println();
		println("a + b = " + sum.toString());
		println("a * s = " + scalar.toString());
		println("a - b = " + diff.toString());
		println("a * b = " + product.toString());
	}

	private void doPolynomialDivision(Scanner scanner) {
		println("= 2. Polynomial Division and Extended Euclidean Algorithm ==");
		println();

		println("Enter the prime number for the coefficients.");
		int prime = inputPrime(scanner);

		println("Enter the first polynomial.");
		Polynomial a = inputPolynomial(scanner, prime);

		println("Enter the second polynomial.");
		Polynomial b = inputPolynomial(scanner, prime);

		// Calculations
		DivisionResult divResult = new Polynomial(a).divide(new Polynomial(b));
		ExtEuclideanResult extResult = new Polynomial(a).gcd(new Polynomial(b));
		
		// Results
		println("= Results ==================================================");
		println();
		println("Integer coefficients modulo " + prime);
		println("a(X) = " + a.toString());
		println("b(X) = " + b.toString());
		println();
		println("a = q * b + r");
		println("q(X) = " + divResult.quot().toString());
		println("r(X) = " + divResult.rem().toString());
		println();
		println("g = gcd(a, b) = x * a + y * b");
		println("g(X) = " + extResult.gcd().toString());
		println("x(X) = " + extResult.x().toString());
		println("y(X) = " + extResult.y().toString());
	}

	private void doPolynomialModularEquality(Scanner scanner) {
		println("= 3. Polynomial Modular Equality ===========================");
		println();

		println("Enter the prime number for the coefficients.");
		int prime = inputPrime(scanner);

		println("Enter the first polynomial.");
		Polynomial a = inputPolynomial(scanner, prime);

		println("Enter the second polynomial.");
		Polynomial b = inputPolynomial(scanner, prime);
		
		println("Enter the third polynomial (modulo polynomial).");
		Polynomial c = inputPolynomial(scanner, prime);

		// Calculations
		boolean equality = a.equalsModulo(b, c);
		
		// Results
		println("= Results ==================================================");
		println();
		println("Integer coefficients modulo " + prime);
		println("a(X) = " + a.toString());
		println("b(X) = " + b.toString());
		println("c(X) = " + b.toString());
		println();
		if (equality) {
			println("a(X) EQUALS b(X) (mod c(X))");
		} else {
			println("a(X) DOES NOT EQUAL b(X) (mod c(X))");
		}
	}

	private void doFiniteFieldTables(Scanner scanner) {
		println("= 4. Finite Fields Tables ==================================");
		println();

		println("Enter the prime number.");
		int prime = inputPrime(scanner);

		println("Enter an irreducible polynomial.");
		Polynomial q = inputPolynomial(scanner, prime);

		FiniteField field = new FiniteField(prime, q);

		// Calculations
		String additionTable = field.getAdditionTable();
		String multiplicationTable = field.getMultiplicationTable();
		
		// Results
		println("= Results ==================================================");
		println();
		println("Finite Field: " + field.toString());
		println();
		println("Addition table (in list form):"); 
		System.out.println(additionTable);
		println("Multiplication table (in list form):"); 
		System.out.println(multiplicationTable);
	}

	private void doFiniteFieldElementsMath(Scanner scanner) {
		println("= 5. Finite Fields Math ====================================");
		println();

		println("Enter the prime number.");
		int prime = inputPrime(scanner);

		println("Enter an irreducible polynomial.");
		Polynomial q = inputPolynomial(scanner, prime);

		FiniteField field = new FiniteField(prime, q);

		println("Enter the first element from the field " + field.toString() + ".");
		Polynomial a = inputPolynomial(scanner, prime);
		
		println("Enter the second element from the field " + field.toString() + ".");
		Polynomial b = inputPolynomial(scanner, prime);
		
		// Calculations
		Polynomial sum = field.reduce(new Polynomial(a).sum(new Polynomial(b)));
		Polynomial prod = field.reduce(new Polynomial(a).product(new Polynomial(b)));
		Polynomial inverseb = field.inverse(new Polynomial(b));
		Polynomial quot = null;
		if (inverseb != null) {
			quot = new Polynomial(a).product(new Polynomial(inverseb));
		}
		
		// Results
		println("= Results ==================================================");
		println();
		println("Finite Field: " + field.toString());
		println();
		println("element a(X): " + a.toString());
		println("element b(X): " + b.toString());
		if (inverseb != null) {
			println("inverse b^-1(X): " + inverseb.toString());
		} else {
			println("inverse b^-1(X): Does not exist.");
		}
		println();
		println("a + b = " + sum.toString());
		println("a * b = " + prod.toString());
		if (inverseb != null) {
			println("ab^-1 = " + quot.toString());
		} else {
			println("ab^-1 = N/A");
		}
	}

	private void doFiniteFieldIrreducibility(Scanner scanner) {
		println("= 6. Finite Fields Irreducibility ==========================");
		println();

		println("Enter the prime number.");
		int prime = inputPrime(scanner);

		println("Enter an irreducible polynomial.");
		Polynomial q = inputPolynomial(scanner, prime);

		FiniteField field = new FiniteField(prime, q);

		println("Enter an element from the field " + field.toString() + ".");
		Polynomial a = inputPolynomial(scanner, prime);
		
		println("Enter the degree of the irreducible polynomial to construct.");
		int degree = inputInteger(scanner);
		
		// Calculations
		boolean isIrreducible = field.irreducible(a);
		Polynomial irreducible = field.createIrreducible(degree);
		
		// Results
		println("= Results ==================================================");
		println();
		println("Finite Field: " + field.toString());
		println();
		println("element a(X) = " + a.toString());
		println();
		if (isIrreducible) {
			println("a is IRREDUCIBLE.");
		} else {
			println("a is NOT IRREDUCIBLE (reducible).");
		}
		println();
		println("Generated irreducible polynomial of degree " + degree + ":");
		println("i(X) = " + irreducible.toString());
	}

	private void printHeader() {
		println("============================================================");
		println("= Algebra for Security - 2WF90 (2016-2017)                 =");
		println("=   Programming Assignment 2                               =");
		println("============================================================");
		println("=  David Kortleven (0937121) ,  Jochem Kuijpers (0838617)  =");
		println("=             and  Mircea Florica (0978933)                =");
		println("============================================================");
		println();
	}

	private String printMenu(Scanner scanner) {

		println("= Menu =====================================================");
		println();
		println(" 1. Calculate sum, scalar multiple, difference and product");
		println("    of two polynomials with coefficients modulo a prime.");
		println(" 2. Produce quotient and remainder of two polynomials with");
		println("    coefficients modulo a prime and Ext. Eucl. Algorithm.");
		println(" 3. Decide whether or not two polynomials are equal modulo");
		println("    a third polynomial.");
		println(" 4. Produce the addition and multiplication table of a field");
		println("    given a prime number and irreducible polynomial.");
		println(" 5. Calculate sum, product and quotient ab^-1 for two");
		println("    elements in a given field.");
		println(" 6. Test irredicubility of an element in a given field and");
		println("    generate an irreducible polynomial of a given degree.");
		println();
		println(" q. Terminate the program.");
		println();

		String choice = "";
		boolean valid = false;
		while (!valid) {
			println("Your choice [1-6,q]: ");
			choice = scanner.nextLine().trim();
			switch (choice) {
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
			case "6":
			case "q":
			case "Q":
				valid = true;
				break;
			default:
				println("Invalid choice.");
				println();
			}
		}

		return choice;
	}

	private Polynomial inputPolynomial(Scanner scanner, int prime) {
		ArrayList<IntegerMod> c = new ArrayList<IntegerMod>();
		boolean valid = false;
		while (!valid) {
			println("Enter a string of coefficients modulo " + prime + ", separated by spaces.");
			
			String inputString = scanner.nextLine();
			String[] coefficients = inputString.split(" ");
			valid = true;
			for (String coefficientString : coefficients) {
				int coefficient = 0;
				try {
					if (coefficientString.trim().length() > 0) {
						coefficient = Integer.parseInt(coefficientString.trim());
						c.add(new IntegerMod(coefficient, prime));
					}
				} catch (NumberFormatException e) {
					println("'" + coefficientString + "' is not a number.");
					c.clear();
					valid = false;
					break;
				}
			}
		}
		
		Collections.reverse(c);
		
		return new Polynomial(c, prime);
	}

	private int inputInteger(Scanner scanner) {
		int x = 0;
		boolean valid = false;
		
		while (!valid) {
			println("Enter an integer:");
			String inputString = scanner.nextLine();
			valid = true;
			try {
				x = Integer.parseInt(inputString);
			} catch (NumberFormatException e) {
				println("Invalid input.");
				println();
				valid = false;
			}
		}
		return x;
	}

	private int inputPrime(Scanner scanner) {
		int x = 0;
		boolean valid = false;
		
		while (!valid) {
			println("Enter a prime integer:");
			String inputString = scanner.nextLine();
			valid = true;
			try {
				x = Integer.parseInt(inputString);
			} catch (NumberFormatException e) {
				println("Invalid input.");
				println();
				valid = false;
			}
			if (valid) {
				if (!isPrime(x)) {
					println("This number is not prime.");
					println();
					valid = false;
				}
			}
		}
		return x;
	}

	private boolean isPrime(int x) {
		if (x <= 1) {
			return false;
		}
		if (x <= 3) {
			return true;
		}
		if ((x & 1) == 0) {
			return false;
		}
		for (int n = 3; n < Math.sqrt(x) + 1; n += 1) {
			if (x % n == 0) {
				return false;
			}
		}
		return true;
	}

	private void println() {
		System.out.println();
	}

	private void println(String string) {
		System.out.println(string);
	}
}
