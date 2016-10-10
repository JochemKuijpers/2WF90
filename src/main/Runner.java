package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		new Runner().run();
		System.out.println("DONE.");
	}

	public void run() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("2WF90 - Number Theory assignment");
		System.out.println(" - David Kortleven (0937121)");
		System.out.println(" - Mircea Florica  (0978933)");
		System.out.println(" - Jochem Kuijpers (0838617)");
		System.out.println();
		System.out.println("Which operation would you like to execute?");
		System.out.println(" 1. Addition");
		System.out.println(" 2. Subtraction");
		System.out.println(" 3. Multiplication (primary school)");
		System.out.println(" 4. Multiplication (Karatsuba, recursive)");
		System.out.println(" 5. Compare multiplication methods");
		System.out.println(" q. Quit");
		System.out.println();

		String choice = getChoice(scanner);

		if (choice.equals("q")) {
			scanner.close();
			return;
		}

		int base = getBase(scanner);

		System.out.println();
		System.out.println("Enter the first number in base-" + base + ".");
		Number a = getNumber(scanner, base);

		System.out.println();
		System.out.println("Enter the second number in base-" + base + ".");
		Number b = getNumber(scanner, base);

		Calculator calc = new Calculator();
		Number c;

		System.out.println();
		switch (choice) {
		case "1":
			System.out.println(a + " + " + b + " (base " + base + ")");
			c = calc.add(a, b);
			break;
		case "2":
			System.out.println(a + " - " + b + " (base " + base + ")");
			c = calc.subtract(a, b);
			break;
		case "3":
			System.out.println(a + " × " + b + " (base " + base + ") by the primary school method");
			c = calc.mulPrimarySchool(a, b);
			break;
		case "4":
			System.out.println(a + " × " + b + " (base " + base + ") by Karatsuba's method");
			c = calc.mulKaratsuba(a, b);
			break;
		case "5":
			System.out.println(a + " × " + b + " (base " + base + ") compare multiplication methods");
			c = calc.mulPrimarySchool(a, b);
			
			int length = Math.max(a.getLength(), b.getLength());
			
			long addPr = calc.getAdd();
			long subPr = calc.getSub();
			long mulPr = calc.getMul();
			long totPr = addPr + subPr + mulPr;
			
			double lgMulPr = Math.log(mulPr) / Math.log(length);
			
			calc.resetStats();
			Number d = calc.mulKaratsuba(a, b);
			
			long addKa = calc.getAdd();
			long subKa = calc.getSub();
			long mulKa = calc.getMul();
			long totKa = addKa + subKa + mulKa;
			
			double lgMulKa = Math.log(mulKa) / Math.log(length);
			
			double addPc = Math.round(((addKa - addPr) / (double) addPr) * 10000)/100;
			double subPc = Math.round(((subKa - subPr) / (double) subPr) * 10000)/100;
			double mulPc = Math.round(((mulKa - mulPr) / (double) mulPr) * 10000)/100;
			double totPc = Math.round(((totKa - totPr) / (double) totPr) * 10000)/100;
			
			System.out.println("Answer Pr.School: " + c + " (base " + base + ")");
			System.out.println("Answer Karatsuba: " + d + " (base " + base + ")");
			System.out.println();
			
			System.out.println("     Pr.School       | Karatsuba       | Difference      | Percent increase\r\n"
					+ String.format(" +   %1$15s | %2$15s | %3$15s | ", addPr, addKa, (addKa - addPr)) + addPc + " % \r\n"
					+ String.format(" -   %1$15s | %2$15s | %3$15s | ", subPr, subKa, (subKa - subPr)) + "N/A \r\n"
					+ String.format(" ×   %1$15s | %2$15s | %3$15s | ", mulPr, mulKa, (mulKa - mulPr)) + mulPc + " % \r\n"
					+ String.format(" tot %1$15s | %2$15s | %3$15s | ", totPr, totKa, (totKa - totPr)) + totPc + " % \r\n");
			
			System.out.println();
			System.out.println("length (n): " + length);
			System.out.println("Pr.School took n^" + lgMulPr + " elementary multiplications");
			System.out.println("Karatsuba took n^" + lgMulKa + " elementary multiplications");
			
			scanner.close();
			return;
		default:
			throw new IllegalStateException("Unknown choice: " + choice);
		}

		System.out.println("Answer: " + c + " (base " + base + ")");
		System.out.println();
		calc.printStats();
		scanner.close();
	}

	/**
	 * Gets the user choice for the main menu item.
	 * 
	 * @param scanner
	 *            Input scanner to use
	 * @return the entered token (either 1, 2, 3, 4, 5 or q)
	 */
	private String getChoice(Scanner scanner) {
		String choice;
		boolean valid = false;

		do {			
			System.out.println("Your choice: [1-4,q]");
			choice = scanner.next().substring(0, 1).toLowerCase();
			if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")
					|| choice.equals("5") || choice.equals("q")) {
				valid = true;
				break;
			}
			System.out.println("Wrong choice. Please enter 1, 2, 3, 4, 5 or q.");
		} while (!valid);

		return choice;
	}

	/**
	 * Gets an input number from the command line.
	 * 
	 * @param scanner
	 *            Input scanner to use
	 * @param base
	 *            The base of the number
	 * @return Number object containing the input number
	 */
	private Number getNumber(Scanner scanner, int base) {
		String inputString;
		String tokens = WordUtils.getTokensAsString(base);
		Number n = null;
		boolean valid = false;

		do {
			System.out.println("Number: [sequence of '" + tokens + "', may be prefixed by -]");
			inputString = scanner.next();
			try {
				n = Number.fromString(base, inputString);
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
				System.out.println(
						"Example usage: to enter the decimal number -145 in base 12, write '-B2' without quotes.");
			}
			valid = (n != null);
		} while (!valid);

		return n;
	}

	/**
	 * Reads an hexadecimal input, and returns it to a decimal, so A = 10...
	 * 
	 * @param scanner
	 *            Input scanner to use
	 * @return Decimal value of the input read from command line
	 */
	private int getBase(Scanner scanner) {
		int base = 0;
		boolean valid = true;

		System.out.println();
		System.out.println("Enter the base of the numbers you will be entering:");
		System.out.println("Base: [2-16]");
		do {
			if (!valid) {
				System.out.println("Enter an integer base between 2 and 16, inclusive.");
			} else {
				valid = false;
			}
			
			try {
				base = scanner.nextInt();
			} catch (InputMismatchException e) {
				valid = false;
				if (scanner.hasNext()) {
					scanner.next();
				}
				continue;
			}
			if (base >= 2 && base <= 16) {
				valid = true;
				break;
			}
		} while (!valid);

		return base;
	}
}
