package brain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	private int nextReadedNumber = 1;

	public static final String[] neededValues = {"Date", "Time"};
	private static final String[] viableValues = {"Date", "Time", "Ram Used", "Ram Available", "CPU Usage", "CPU Temperature", "CPU Fan", "Read Rate SSD", "Write Rate SSD", "Read Rate HDD", "Write Rate HDD", "GPU Usage", "GPU Temperature", "GPU Fan", "Download Rate", "Upload Rate"};
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
	
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	
	/**
	 * Parses the csv file and fills the found values list if no needed values are missing.
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
	 * Returns the count of saved information lines.
	 * @return
	 */
	public int getInformationLineCount() {
		return lines.size();
	}
	
	/**
	 * Returns the information line at the given index.
	 * @param index
	 * @return The above specified informationline.
	 */
	public InformationLine getInformationLine(int index) throws IndexOutOfBoundsException {
		return lines.get(index);
	}
	
	/**
	 * Returns the information line from the index firstIndex to lastIndex.
	 * @param firstIndex
	 * @param lastIndex
	 * @return The above specified information lines.
	 */
	public List<InformationLine> getInformationLines(int firstIndex, int lastIndex) throws IndexOutOfBoundsException{
		List<InformationLine> r = new ArrayList<InformationLine>();
		for (int i = firstIndex; i<=lastIndex; i++) {
			r.add(lines.get(i));
		}
		return r;
	}
	
	/**
	 * Looks for new lines at the end of the file. Ignores the "headline" line at the end.
	 * Array[0] Count of files which were added to the file. If something went wrong: -1
	 * Array[1] Next readed line number
	 * @return the above specified int
	 */
	public int[] readNewLines() {
		int[] r = {-1, nextReadedNumber};
		CSVParser csvParser = null;
		try {
			csvParser = new CSVParser(new FileInputStream(logPath));
			for (int i = 0; i < nextReadedNumber; i++) {
				csvParser.getLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return r;
		} catch (IOException e) {
			e.printStackTrace();
			return r;
		}
		int count = 0;
		while (true) {
			try {
				lines.add(new InformationLine(csvParser.getLine(), headline));
				nextReadedNumber++;
				count++;
			} catch (EndOfCSVFileException e) {
				lines.remove(lines.size()-1);
				nextReadedNumber--;
				count--;
				System.err.println(e.getMessage());
				break;
			} catch (IOException e) {
				e.printStackTrace();
				return r;
			} catch (BrokenCSVFileException e1) {
				return r;
			}
		}
		r[0] = count;
		r[1] = nextReadedNumber;
		return r;
		
	}
	
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
					nextReadedNumber++;
				} catch (EndOfCSVFileException e) {
					lines.remove(lines.size()-1);
					nextReadedNumber--;
					System.err.println(e.getMessage());
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (BrokenCSVFileException e1) {
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
		//Check whether headline only has viable Values
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
