package brain;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Ostermiller.util.CSVParser;

import exceptions.BrokenCSVFileException;
import exceptions.EndOfCSVFileException;

/**
 * The Data class reads a CSV file at a given position and provides methods to read the lines of the csv file.
 * @author jonathanhasenburg
 *
 */

public class Data {
	
	private Headline headline;
	private List<InformationLine> lines = new ArrayList<InformationLine>();
	private String logPath;

	public static final String[] neededValues = {"Date", "Time"};
	private static final String[] viableValues = {"Date", "Time", "Ram Used", "Ram Available", "CPU Usage", "CPU Temperature", "CPU Fan", "Read Rate SSD", "Write Rate SSD", "Read Rate SSD", "Write Rate SSD", "Read Rate HDD", "Write Rate HDD", "GPU Usage", "GPU Temperatur", "GPU Fan", "Download Rate", "Upload Rate"};
	private List<String> foundValues = new ArrayList<String>();
	
	/**
	 * Creates a new Data object which reads the csv file specified in the config.properties.
	 */
	public Data() {
		logPath = Configuration.getLogPath();
	}
	
	/**
	 * Creates a new Data object which reads the csv file at the logPath.
	 * @param logPath
	 */
	public Data(String logPath) {
		this.logPath = logPath;
	}
	
	/**
	 * Parses the csv file and activates the Getter for all values if no needed values are missing.
	 * @return
	 */
	public boolean startUp() {
		boolean success;
		success = parseFile();
		if (!success) {
			return false;
		}
		success = fillFoundValuesList();
		if (!success) {
			return false;
		}
		return success;
	}
	
	/**
	 * Returns all found values that are in the csv file.
	 * @return the above specified String
	 */
	public List<String> getFoundValues() {
		return foundValues;
	}
	
	/**
	 * Returns the headline to the csv file.
	 * @return the above specified headline.
	 */
	public Headline getHeadline(){
		return headline;
	}
	
	/**
	 * Returns the informationLine at the given index.
	 * @param index
	 * @return The above specified informationline.
	 */
	public InformationLine getInformationLine(int index) throws IndexOutOfBoundsException {
		return lines.get(index);
	}
	
	//TODO Create a copy first
	//TODO Test whether file was already parsed
	/**
	 * Parses the hole CSV file and fills the InformationLine List with data.
	 * Returns true, if parsing worked
	 * @return boolean
	 */
	private boolean parseFile() {
		CSVParser csvParser;
		try {
			csvParser = new CSVParser(new FileInputStream(logPath));
			headline = new Headline(csvParser.getLine());
			while (true) {
				try {
					lines.add(new InformationLine(csvParser.getLine(), headline));
				} catch (EndOfCSVFileException e) {
					lines.remove(lines.size()-1);
					System.err.println(e.getMessage());
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (BrokenCSVFileException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Recognizes which values are found in the csv file. Returns true, if all needed Values are found.
	 * Fills the found values list.
	 * If there are values which are not allowed it returns false.
	 * Returns false, if a needed value misses.
	 * @return boolean
	 */
	private boolean fillFoundValuesList() {
		//Check whether headline contains all needed values
		for (String s : neededValues) {
			if (!headline.hasValue(s)) {
				return false;
			}
		}
		//Check wheather headline only has viable Values
		for (int i = 0; i < headline.getCount(); i++) {
			boolean found = false;
			for (String s : viableValues) {
				if (headline.get(i).equals(s)) {
					found = true;
					break;
				}
			}
			if (found == false) {
				return false;
			}
		}
		//Add all found Values to the list of found values
		for (String s : viableValues) {
			if (headline.hasValue(s)) {
				foundValues.add(s);
			}
		}
		return true;
	}
	
	/**
	 * Returns the hole csv file as string.
	 * @return the above specified String
	 */
	@Override
	public String toString() {
		String out = "";
		for (InformationLine s : lines) {
			out += s + "\n";
		}
		return out;
	}
}
