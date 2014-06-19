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
			assertEquals("{CPU=50}", i.toString());
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
			assertEquals("{Time=Jetzt, Date=Heute, CPU=50}", i.toString());
		} catch (BrokenCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		} catch (EndOfCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}

}
