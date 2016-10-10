package assignment2;

public class Runner {

	public static void main(String[] args) {
		IntegerMod x = new IntegerMod(104, 5);
		System.out.println(x.getValue());
		System.out.println(x.inverse().getValue());
	}
}
