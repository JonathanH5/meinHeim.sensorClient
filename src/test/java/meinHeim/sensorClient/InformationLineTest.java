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

package meinHeim.sensorClient;

import static org.junit.Assert.*;

import org.junit.Test;

import brain.Headline;
import brain.InformationLine;
import exceptions.BrokenCSVFileException;
import exceptions.EndOfCSVFileException;

public class InformationLineTest {

	@Test
	public void emptyLineTest() {
		String[] line = {};
		String[] hline = {"CPU", "Date", "Time", ""};
		try {
			Headline h = new Headline(hline);
			@SuppressWarnings("unused")
			InformationLine i = new InformationLine(line, h);
			fail("Should not get that far");
		} catch (BrokenCSVFileException e) {
			
		} catch (EndOfCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}
	
	@Test
	public void emptyHeadlineTest() {
		String[] line = {"1", "5", ""};
		String[] hline = {};
		try {
			Headline h = new Headline(hline);
			@SuppressWarnings("unused")
			InformationLine i = new InformationLine(line, h);
			fail("Should not get that far");
		} catch (BrokenCSVFileException e) {
			
		} catch (EndOfCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}
	
	@Test
	public void NullLineTest() {
		String[] line = null;
		String[] hline = {"CPU", "Date", "Time", ""};
		try {
			Headline h = new Headline(hline);
			@SuppressWarnings("unused")
			InformationLine i = new InformationLine(line, h);
			fail("Should not get that far");
		} catch (BrokenCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		} catch (EndOfCSVFileException e) {
			assertEquals("CSV File completely parsed", e.getMessage());
		}
	}
	
	@Test
	public void NullHeadlineTest() {
		String[] line = {"5", "2", "mega", ""};
		try {
			@SuppressWarnings("unused")
			InformationLine i = new InformationLine(line, null);
			fail("Should not get that far");
		} catch (BrokenCSVFileException e) {
			assertEquals("The given headline was null", e.getMessage());	
		} catch (EndOfCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}
	
	@Test
	public void OneElementTest() {
		String[] line = {"50", ""};
		String[] hline = {"CPU", ""};
		try {
			Headline h = new Headline(hline);
			InformationLine i = new InformationLine(line, h);
			assertEquals(1, i.getCount());
			assertEquals(i.getValue("CPU"), "50");
			assertNull(i.getValue("Date"));
			assertEquals("{CPU=50}\n", i.toString());
		} catch (BrokenCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		} catch (EndOfCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}
	
	@Test
	public void ManyElementTest() {
		String[] line = {"50", "Heute", "Jetzt", ""};
		String[] hline = {"CPU", "Date", "Time", ""};
		try {
			Headline h = new Headline(hline);
			InformationLine i = new InformationLine(line, h);
			assertEquals(3, i.getCount());
			assertEquals(i.getValue("CPU"), "50");
			assertEquals(i.getValue("Date"), "Heute");
			assertEquals(i.getValue("Time"), "Jetzt");
			assertNull(i.getValue("GPU"));
			assertEquals("{CPU=50, Date=Heute, Time=Jetzt}\n", i.toString());
		} catch (BrokenCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		} catch (EndOfCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}

}
