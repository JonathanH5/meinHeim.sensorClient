package meinHeim.sensorClient;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.BrokenCSVFileException;
import exceptions.EndOfCSVFileException;
import brain.Data;
import brain.InformationLine;

public class DataTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Data d = new Data();
		assertTrue(d.startUp());
		String[] s = {"12.6.2014","15:20:38.398","2013","1.8","32.0","951","0.000","0.001","0.000","0.007","31.0","0.0","48.0","1.378","0.000",""};
		assertSame(s.length, d.getHeadline().getCount());
		try {
			assertEquals(new InformationLine(s, d.getHeadline()).toString(), d.getInformationLine(2).toString().toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
	}
	
	@Test
	public void brokenCSV() {
		Data d = new Data();
		d.setLogPath("src/test/resources/BrokenCSV.CSV");
		assertFalse(d.startUp());
	}
	
	@Test
	public void missingAttributeCSV() {
		Data d = new Data();
		d.setLogPath("src/test/resources/NotNeededValues.CSV");
		assertFalse(d.startUp());
		
	}
	
	@Test
	public void notSupportedAttributeCSV() {
		Data d = new Data();
		d.setLogPath("src/test/resources/NotSupportedAttribute.CSV");
		assertFalse(d.startUp());
	}

}
