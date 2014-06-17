package meinHeim.sensorClient;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

import brain.Configuration;

public class ConfigurationTest {
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Configuration.loadProperties();
	}
	
	@Test
	public void test() {
		assertEquals("src/test/resources/TestLog.CSV", Configuration.getLogPath());
		Configuration.setLogPath("meinPath");
		assertEquals("meinPath", Configuration.getLogPath());
	}

}
