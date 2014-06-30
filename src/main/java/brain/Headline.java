/***********************************************************************************************************************
 * Copyright (C) 2014 Jonathan Hasenburg
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **********************************************************************************************************************/

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
			throw new BrokenCSVFileException("The delivered line for the new headline was null");
		}
		this.line = new ArrayList<String>();
		//-1 because last element is "empty"
		for (int i = 0; i < line.length -1; i++) {
			this.line.add(line[i]);
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
	
	public String toJson() {
		return "";
	}

}
