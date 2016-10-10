package assignment2;

/**
 * 
 * @author D Kortleven
 *	Class for integers mod m
 */
public class IntegerMod {
	
	private int value;
	private int mod;
	
	public IntegerMod(int v, int m){
		value = v;
		mod = m;
		
		value = reduce(value);
	}
	
	
	/**
	 * 
	 * @return the value of this int
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * 
	 * @return the modulus of this int
	 */
	public int getMod(){
		return mod;
	}
	
	/**
	 * Checks if moduli are the same, if so it adds this number to the input number.
	 * @param x 	The input integer to be added
	 * @return		The reduced result of the addition 
	 */
	public IntegerMod add(IntegerMod x){
		
		if(x.getMod() != mod){
			throw new IllegalArgumentException("Addition not allowed - The input integer does not have the same modulus.");
		}
		
		int v = value + x.getValue();
		return new IntegerMod(reduce(v), mod);
	}
	
	/**
	 * Checks if moduli are the same, if so it subtracts the input number from this number.
	 * @param x 	The input integer to be subtracted
	 * @return		The reduced result of the subtraction 
	 */
	public IntegerMod subtract(IntegerMod x){
		if(x.getMod() != mod){
			throw new IllegalArgumentException("Subtraction not allowed - The input integer does not have the same modulus.");
		}
		
		int v = value - x.getValue();
		return new IntegerMod(reduce(v), mod);
	}
	
	/**
	 * Checks if moduli are the same, if so it multiplies the input number from this number.
	 * @param x 	The input integer to be multiplied
	 * @return		The reduced result of the multiplication 
	 */
	public IntegerMod multiply(IntegerMod x){
		if(x.getMod() != mod){
			throw new IllegalArgumentException("Multiplication not allowed - The input integer does not have the same modulus.");
		}
		
		int v = value * x.getValue();
		return new IntegerMod(reduce(v), mod);
	}
	
	/**
	 * Calculates the inverse of this integer, using algorithm 2.11 as described in the lecture notes.
	 * @return The inverse of this integer
	 */
	public IntegerMod inverse(){
		
		// Step 1
		int a_ = value;
		int m_ = mod;
		int x1 = 1;
		int x2 = 0;
		int q, r, x3;
		
		// Step 2
		while (m_ > 0) {
			q = a_ / m_;
			r = a_ - (q * m_);
			
			a_ = m_;
			m_ = r;
			
			x3 = x1 - q * x2;
			
			x1 = x2;
			x2 = x3;
		}
		
		// Step 3
		if(a_ == 1){
			return new IntegerMod(reduce(x1), mod);
		}else{
			throw new IllegalArgumentException("No inverse available of the number " + value + " (mod " + mod + ")"
					+ " Would give " + x1);
		}
		
	}
	/**
	 * Calculates the division this/x by calculating the inverse of x and multiplying.
	 * @param x
	 * @return
	 */
	public IntegerMod divide(IntegerMod x){
		
		if(x.getMod() != mod){
			throw new IllegalArgumentException("Division not allowed - The input integer does not have the same modulus.");
		}
		
		IntegerMod a = x.inverse();
		return multiply(a);
	}
	
	/**
	 * Reduce this Integer the 'naive' way, not efficient.  
	 */
	private int reduce(int v){
		
		int q = Math.floorDiv(v, mod);
		
		v -= q * mod;
		
		return v;
	}
	
	
}
