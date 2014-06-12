package brain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Ostermiller.util.CSVParser;

import exceptions.EndOfCSVFileException;

public class Data {
	
	private CSVParser csvParser;
	private List<InformationLine> lines;
	
	public Data() {
		try {
			csvParser = new CSVParser(new FileInputStream(Configuration.getLogPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		lines = new ArrayList<InformationLine>();
	}
	
	//TODO Create a copy first
	//TODO Test whether file was already parsed
	public void parseFile() {
		try {
			while (true) {
				try {
					lines.add(new InformationLine(csvParser.getLine()));
					//TODO no syso
				} catch (EndOfCSVFileException e) {
					System.err.println(e.getMessage());
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		String out = "";
		for (InformationLine s : lines) {
			out += s + "\n";
		}
		return out;
	}
}
