package main;

public class Number {	
	private final int base;
	private final int[] words;
	
	public Number(int base, int[] words) {
		this.base = base;
		this.words = words;
	}

	public int getBase() {
		return base;
	}

	public int[] getWords() {
		return words;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int word : words) {
			sb.append(WordUtils.IntToToken(word));
		}
		sb.append(" (base " + base + ")");
		return sb.toString();
	}
}
