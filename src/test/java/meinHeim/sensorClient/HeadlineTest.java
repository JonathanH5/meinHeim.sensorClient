package meinHeim.sensorClient;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.BrokenCSVFileException;
import brain.Headline;

public class HeadlineTest {
	
	@Test
	public void emptyLineTest() {
		String[] line = {};
		try {
			Headline h = new Headline(line);
			assertFalse(h.hasValue("Bla"));
			assertSame(0, h.getCount());
			String ausgabe = "";
			assertSame(h.toString() + "was not " + ausgabe, h.toString(), ausgabe);
		} catch (BrokenCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}
	
	@Test
	public void NullLineTest() {
		String[] line = null;
		try {
			@SuppressWarnings("unused")
			Headline h = new Headline(line);
			fail("Should not get that far");
		} catch (BrokenCSVFileException e) {
			assertSame(e.getMessage(), "The delivered line for the new headline was null");
			return;
		}
		fail("The Headline should be broken because it got a null");
	}
	
	@Test
	public void OneElementTest() {
		String[] line = {"Bla"};
		try {
			Headline h = new Headline(line);
			assertTrue(h.hasValue("Bla"));
			assertFalse(h.hasValue("Date"));
			assertSame(1, h.getCount());
			assertSame("Bla", h.get(0));
			String ausgabe = "Bla";
			assertEquals(h.toString(), ausgabe);
		} catch (BrokenCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}
	
	@Test
	public void ManyElementTest() {
		String[] line = {"Date", "TimeZone", "CPU"};
		try {
			Headline h = new Headline(line);
			assertFalse(h.hasValue("Bla"));
			assertTrue(h.hasValue("CPU"));
			assertSame(3, h.getCount());
			assertSame("Date", h.get(0));
			assertSame("TimeZone", h.get(1));
			assertSame("CPU", h.get(2));			
			String ausgabe = "Date, TimeZone, CPU";
			assertEquals(h.toString(), ausgabe);
		} catch (BrokenCSVFileException e) {
			fail("There was an unexpeted exception" + e.getMessage());
		}
	}

}
