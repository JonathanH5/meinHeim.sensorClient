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
