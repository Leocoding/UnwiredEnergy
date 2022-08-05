package fr.unwired.nrj.util;

public class IntParser {
	
	private final static String[] unities = {"","k","M","B"};
	
	public static String getParsedValue(int value) {
		if (value == 0) return "0";
		String str = "";
		int v = value;
		while(v != 0) {
			str = (v/1000 == 0?(""+Integer.toString(v))
								:(","+ getFilledIntString(v%1000))) 
								+ str;
			v /= 1000;
		}
		return str;
	}
	
	public static String getShortenedValue(int value, int precision) {
		if (value == 0) return "0";
		String[] parsedValueSplit = getParsedValue(value).split(",");
		String str = parsedValueSplit[0] + (precision > 0 ? ",":"");
		for (int i = 0; i < Math.min(precision, (parsedValueSplit.length - 1)*3) ; i++) {
			str += parsedValueSplit[(i/3)+1].charAt(i%3);
		}
		return str + unities[parsedValueSplit.length-1];
	}
	
	private static String getFilledIntString(int value) {
		String str = "";
		for (int i = 0; i < 3-Integer.toString(value).length(); i++) {
			str += 0;
		}
		return str + Integer.toString(value);
	}
}
