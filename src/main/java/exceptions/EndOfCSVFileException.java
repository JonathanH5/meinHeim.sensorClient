package exceptions;

public class EndOfCSVFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EndOfCSVFileException(String message) {
		super(message);
	}

}
