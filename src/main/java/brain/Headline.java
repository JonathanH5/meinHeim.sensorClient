package brain;

import java.util.ArrayList;

import exceptions.BrokenCSVFileException;

/**
 * The Headline represents the line with the specifiers for each collumn of the CSV-File.
 * The checks whether the values are valid are done in the Data class.
 * @author jonathanhasenburg
 *
 */

public class Headline {

	private ArrayList<String> line;
	
	public Headline(String[] line) throws BrokenCSVFileException {
		if (line == null) {
			throw new BrokenCSVFileException("The delivered headline was null");
		}
		this.line = new ArrayList<String>();
		for (String s : line) {
			this.line.add(s);
		}
	}

	/**
	 * Checks, whether the informationLine contains the given value.
	 * @param value
	 * @return the above specified boolean
	 */
	public boolean hasValue(String value) {
		for (String s : line) {
			if (value.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the headline string at the given index.
	 * @param index
	 * @return the above specified String
	 */
	public String get(int index) {
		return line.get(index);
	}
	
	/**
	 * Returns the count of headline Strings.
	 * @return the above specified int
	 */
	public int getCount() {
		return line.size();
	}

	/**
	 * Returns all Strings in the headline in one comma separated string.
	 * @return the above specified String
	 */
	@Override
	public String toString() {
		String out = "";
		int i = 0;
		for (String s : line) {
			out += s;
			i++;
			if  (i < line.size()) {
				out += ", ";
			}
		}
		return out;
	}

}
