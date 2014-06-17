package brain;

import java.util.HashMap;

import exceptions.BrokenCSVFileException;
import exceptions.EndOfCSVFileException;

/**
 * The InformationLine represents a single line in the csv file which is not a Headline.
 * The InformationLine has always the same amount of elements as the corresponing headline.
 * @author jonathanhasenburg
 *
 */

public class InformationLine {
	
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public InformationLine(String[] line, Headline headline) throws EndOfCSVFileException, BrokenCSVFileException {
		if (line == null) {
			throw new EndOfCSVFileException("CSV File completely parsed");
		}
		if (headline == null) {
			throw new BrokenCSVFileException("The given headline was null");
		}
		if (line.length != headline.getCount()) {
			throw new BrokenCSVFileException("Could not create information line because " + line + "was shorter than " + headline.toString());
		}
		for (int i = 0; i < line.length; i++) {
			map.put(headline.get(i), line[i]);
		}
	}
	
	/**
	 * Returns the value with with the given key. Returns null if the value does not exist.
	 * @param key
	 * @return the above specified boolean
	 */
	public String getValue(String key) {
		return map.get(key);
	}
	
	/**
	 * Returns all key values in the information line, separated by commas.
	 * @return the above specified String
	 */
	@Override
	public String toString() {
		return map.toString();
	}
}
