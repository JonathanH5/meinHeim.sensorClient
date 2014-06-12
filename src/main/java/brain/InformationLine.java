package brain;

import exceptions.EndOfCSVFileException;

public class InformationLine {

	private String[] line;
	
	public InformationLine(String[] line) throws EndOfCSVFileException {
		if (line == null) {
			throw new EndOfCSVFileException("CSV File completely parsed");
		}
		this.line = line;
	}
	
	@Override
	public String toString() {
		String out = "";
		int i = 0;
		for (String s : line) {
			out += s;
			i++;
			if  (i < line.length - 1) {
				out += ", ";
			}
		}
		return out;
	}
}
