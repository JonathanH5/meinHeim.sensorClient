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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	static {
		loadProperties();
	}
	
	private static Properties properties;
	private static String logPath;

	/**
	 * Loads the Configuratoin file.
	 */
	public static void loadProperties() {
		properties = new Properties();
		InputStream is = Configuration.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(is);
			logPath = properties.getProperty("logPath", "");
		} catch (IOException e) {
			System.err.println("Error while loading property file");
			e.printStackTrace();
		}
	}

	/**
	 * @return the logPath
	 */
	public static String getLogPath() {
		return logPath;
	}

	/**
	 * @param logPath the logPath to set
	 */
	public static void setLogPath(String logPath) {
		Configuration.logPath = logPath;
	}
	
	
}
