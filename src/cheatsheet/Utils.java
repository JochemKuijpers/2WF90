package cheatsheet;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Utilities class with static methods.
 * 
 * Jochem Kuijpers
 * 0838617 - 26 October 2015
 */
public class Utils {
	
	/**
	 * Maximum width of console output
	 */
	private static final int lineLength = 80;
	
	/**
	 * Default output
	 */
	private static final PrintStream out = System.out;
	
	/**
	 * Quick test to check if n is a prime number.
	 * 
	 * @param n		the value to check
	 * @return		whether or not n is a prime number
	 */
	public static boolean isPrime(int n) {
		
		// if n <= 1, return false
		if (n <= 1) {
			return false;
		}
		
		// if n is 2, return true
		if (n == 1 || n == 2) {
			return true;
		}
		
		// if n is even and larger than 2 we can directly stop this
		if (n % 2 == 0) { return false; }
		
		// check if n is divisible by m, where 3 <= m <= sqrt(n)+1 and m is odd
		for (int m = 3; m < (Math.sqrt(n) + 1); m += 2) {
			if (n % m == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Get a number with the following optional conditions:
	 * 
	 * if min and max form a valid range, that is: min < max, the returned
	 * number will be within the range [min, max] (including min and max)
	 * 
	 * if boolean prime is set, the returned value will be a prime number
	 * 
	 * @param scanner	the input scanner
	 * @param min		the lowerbound of the range
	 * @param max		the upperbound of the range  
	 * @param prime		whether or not to check if the number is prime
	 * @return			a number entered by the user, within range [min, max]
	 * 					if this range is set, and prime if (prime == true)
	 */
	public static int getNumber(Scanner scanner, int min, int max,
			boolean prime) {
		int n;
		String range = "[" + min + ", " + max + "]";
		String msg;
		
		// prepare the message
		msg = "Enter a";
		if (prime) {
			msg += " prime";
		}
		msg += " number";
		if (min < max) {
			msg += " in range " + range;
		}
		msg += ": ";
		
		while (true) {
			// ask for a (prime) number (in range [min, max])
			Utils.printWrapped(msg, System.out, false);
			n = scanner.nextInt();
			
			Utils.printWrapped("");
			
			if (min < max) {
				if (n < min || n > max) {
					Utils.printWrapped(n + " out of range " + range, System.err);
					Utils.printWrapped("");
					
					// skip the rest of the line
					scanner.nextLine();
					continue;
				}
			}
			
			if (prime) {
				if (!Utils.isPrime(n)) {
					Utils.printWrapped(n + " is not prime.", System.err);
					Utils.printWrapped("");
					
					// skip the rest of the line
					scanner.nextLine();
					continue;
				}
			}
			
			break;
		}
		
		// skip the rest of the line
		scanner.nextLine();
		
		return n;
	}

	/**
	 * Asks for a list of coefficients and returns a valid polynomial object.
	 * 
	 * @param scanner	the input scanner
	 * @param order		the order of the polynomial
	 * @param prime		the prime modulo for the coefficients
	 * @return
	 */
	public static Polynomial getPolynomial(Scanner scanner, int order,
			int prime) {
		ArrayList<IntegerPrime> coefficients = new ArrayList<IntegerPrime>();
		
		Polynomial polynomial;
		int coefficient;
		
		while (coefficients.size() == 0) {
			Utils.printWrapped("Give a space separated list of coefficients"
					+ " in [0, " + (prime - 1) + "].");
			Utils.printWrapped("Enter a list of " + (order + 1)
					+ " coefficient(s): ", System.out, false);
			
			for(int n = 0; n <= order; n += 1) {
				coefficient = scanner.nextInt();
				
				if (coefficient < 0 || coefficient >= prime) {
					
					Utils.printWrapped("");
					Utils.printWrapped("Coefficient " + n + " (value: "
							+ coefficient + ") is out of range [0, "
							+ (prime - 1) + "]", System.err);
					
					// skip the rest of the line
					scanner.nextLine();
					
					coefficients.clear();
					break;
				}
				
				coefficients.add(new IntegerPrime(coefficient, prime));
			}
			
			Utils.printWrapped("");
		}
		
		polynomial = new Polynomial(coefficients, prime);
		
		// skip the rest of the line
		scanner.nextLine();
		
		return polynomial;
	}
	
	/**
	 * Prints a horizontal line across the console
	 */
	public static void printHr() {
		String hr = "";
		for (int n = 0; n < Utils.lineLength; n += 1) {
			hr += "-";
		}
		Utils.printWrapped(hr);
	}
	
	/**
	 * Prints a horizontal line ending with an operand
	 * 
	 * @param operand	the operand to end the line with
	 */
	public static void printOperandLine(String operand) {
		String line = "";
		for (int n = 0; n < Utils.lineLength - (4 + operand.length()); n += 1) {
			line += "-";
		}
		line += " (" + operand + ")";
		Utils.printWrapped(line);
	}
	
	/**
	 * Prints a string to System.out. May replace spaces with newlines to
	 * ensure no single line exceeds 80 characters in length. Ends with a 
	 * carriage return and a newline character.
	 * 
	 * Fun fact: Utils.printWrapped(str) is exactly as long as
	 * System.out.println(str) so I didn't have to reformat any strings when
	 * replacing these method calls.
	 * 
	 * @param string 	the string to print
	 */
	public static void printWrapped(String string) {
		Utils.printWrapped(string, Utils.out, true);
	}
	

	/**
	 * Prints a string to System.out. May replace spaces with newlines to
	 * ensure no single line exceeds 80 characters in length. Ends with a 
	 * carriage return and a newline character.
	 * 
	 * @param string
	 * @param out
	 */
	public static void printWrapped(String string, PrintStream out) {
		Utils.printWrapped(string, out, true);
	}
	
	/**
	 * Prints a string to out. May replace spaces with newlines to
	 * ensure no single line exceeds 80 characters in length.
	 * 
	 * Needs to be synchronized to ensure System.out and System.err merge
	 * correctly in the console.
	 * 
	 * This method was based on a Stack Overflow answer linked below:
	 * 
	 *   http://stackoverflow.com/a/4212726/1806348
	 *   
	 * @param string	the string to print
	 * @param out		the stream to print to (System.out, System.err, etc.)
	 * @param newline	whether or not to end with a carriage return and a 
	 * 					newline character
	 */
	synchronized public static void printWrapped(String string, 
			PrintStream out, boolean newline) {
		
		// since windows doesn't support colored CLI output, we'll prefix
		// errors with an exclamation mark
		if (out == System.err) {
			string = "! " + string;
		}
		
		StringBuilder builder = new StringBuilder(string);

		int i = 0;
		while (i + Utils.lineLength < builder.length()
				&& (i = builder.lastIndexOf(" ", i + Utils.lineLength)) != -1) {
		    builder.replace(i, i + 1, "\r\n");
		}

		out.print(builder.toString() + (newline?"\r\n":""));
	}
	
	/**
	 * Wait for a given number of seconds
	 * @param seconds	seconds to wait.
	 */
	public static void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			return;
		}
	}
}
