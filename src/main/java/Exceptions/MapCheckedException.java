package Exceptions;

public class MapCheckedException extends Exception {

	int count = 0;

	public MapCheckedException(String message, int length) {
		super(message);
		count = length;
	}

	public MapCheckedException(String message) {
		super(message);
	}

	public int getCount() {
		return count;
	}

}
