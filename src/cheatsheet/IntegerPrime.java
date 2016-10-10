package cheatsheet;

/**
 * A class for integers mod p prime.
 * 
 * Jochem Kuijpers
 * 0838617 - 26 October 2015
 */
public class IntegerPrime {
	private int value;
	private int prime;
	
	public IntegerPrime(int value, int prime) {
		this.value = value;
		this.prime = prime;
	}
	
	public int getValue() {
		return value;
	}

	public int getPrime() {
		return prime;
	}
	
	/**
	 * Modular addition
	 * 
	 * Reduction is done without division following Algorithm 2.7
	 * 
	 * @param other	the other object
	 * @return		reduced value of (this.value + other.value)
	 */
	public IntegerPrime add(IntegerPrime other) {
		// addition
		int x = this.value + other.getValue();
		
		// reduction
		if (x >= this.prime) {
			x -= this.prime;
		}
		
		// create and return answer object
		return new IntegerPrime(x, this.prime);
	}
	
	/**
	 * Modular subtraction
	 * 
	 * Reduction is done without division following Algorithm 2.8
	 * 
	 * @param other	the other object
	 * @return		reduced value of (this.value - other.value)
	 */
	public IntegerPrime subtract(IntegerPrime other) {
		// subtraction
		int x = this.value - other.getValue();
		
		// reduction
		if (x < 0) {
			x += this.prime;
		}
		
		// create and return answer object
		return new IntegerPrime(x, this.prime);
	}
	
	/**
	 * Modular multiplication
	 * 
	 * Multiplication is done by integer multiplication as this is a 
	 * single-word operation. It makes no sense to split the integer and 
	 * work with smaller word sizes. Following Algorithm 2.9.
	 * 
	 * @param other	the other object
	 * @return		reduced value of (this.value * other.value)
	 */
	public IntegerPrime multiply(IntegerPrime other) {
		// multiplication of the values
		int x = this.value * other.getValue();
		
		// reduction
		x = this.reduce(x);
		
		// create and return answer object
		return new IntegerPrime(x, this.prime);
	}
	
	/**
	 * Modular inversion
	 * 
	 * Based on Algorithm 2.11 and section 2.2.3
	 * 
	 * Note: 	'value' and 'prime' are coprime as 'prime' is a prime number
	 * Note2: 	a' and m' are represented by variables a_ and m_
	 * 
	 * @return 		the inverse of this object
	 */
	public IntegerPrime inverse() {
		// step 1 and declaration of other variables
		int a_ = this.value;
		int m_ = this.prime;
		int x1 = 1;
		int x2 = 0;
		int q, r, x3;
		
		// step 2
		while (m_ > 0) {
			// calculate quotient and remainder
			q = a_ / m_;
			r = a_ - (q * m_);
			
			// shift values: a <- m <- r 
			a_ = m_;
			m_ = r;
			
			// calculate new x1 and x2
			x3 = x1 - q * x2;
			x1 = x2;
			x2 = x3;
		}
		
		// step 3
		// a' = 1 since a and prime are coprime so we don't need to check
		// reduce x1 as it may be out of range [0, this.prime).
		return new IntegerPrime(this.reduce(x1), this.prime);
	}
	
	/**
	 * Modular Division
	 * 
	 * Calculate c for input a and b such that c = a / b
	 * 
	 * c is calculated by an inversion and an multiplication:
	 * c = a * (b^-1)
	 * 
	 * @param other	the other object
	 * @return		reduced value of (this.value / other.value)
	 */
	public IntegerPrime divide(IntegerPrime other) {
		// calculate inverse = (b^-1)
		IntegerPrime inverse = other.inverse();
		
		// calculate and return a * inverse
		return this.multiply(inverse);
	}

	/**
	 * Modular reduction
	 * 
	 * This is the 'naive' approach from the lecture notes (Algorithm 2.4)
	 * because we're using single-word values (normal integers), so this is
	 * more efficient.
	 * 
	 * input: x, m (m = this.prime)
	 * output: x' reduced by x mod m
	 * ----------------------------
	 * 1. q = floor(x / m)
	 * 2. x' = x - q * m
	 * 3. output x'
	 * 
	 * @param x the value to reduce
	 */
	private int reduce(int x) {
		// integer division that rounds to negative infinity instead of 0
		int q = Math.floorDiv(x, this.prime);
		
		x -= q * this.prime;
		
		return x;
	}
	
	public String toString() {
		return "" + this.value;
	}
}
