package exceptions;

public class BrokenCSVFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BrokenCSVFileException(String message) {
		super(message);
	}

}
