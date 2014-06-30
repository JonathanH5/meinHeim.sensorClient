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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import brain.Configuration;
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
		Configuration.loadProperties();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Data d = new Data();
		assertTrue(d.startUp());
		String[] s = {"12.6.2014","15:20:38.398","2013","1.8","32.0","951","0.000","0.001","0.000","0.007","31.0","0.0","48.0","1.378","0.000",""};
		assertSame(d.getHeadline().getCount(), s.length - 1);
		assertSame(s.length - 1, d.getFoundValues().size());
		assertSame(58, d.getInformationLineCount());
		assertEquals(0, d.readNewLines()[0]);
		assertEquals(59, d.readNewLines()[1]);

		try {
			assertEquals(new InformationLine(s, d.getHeadline()).toString(), d.getInformationLine(2).toString().toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
	}
	
	@Test
	public void constructorTest() {
		Data d = new Data("src/test/resources/TestLog.CSV");
		assertTrue(d.startUp());
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
