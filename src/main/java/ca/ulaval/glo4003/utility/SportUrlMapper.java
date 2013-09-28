package ca.ulaval.glo4003.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class SportUrlMapper {
	public static String sport_properties_file = "/sport-url.properties";
	
	public static String getSportUrl(String sportName) throws RuntimeException{
		Properties properties = new Properties();
		try(InputStream input = SportUrlMapper.class.getResourceAsStream(sport_properties_file);) {
			properties.load(input);
			Set<Object> keySet = properties.keySet();
			for(Object key : keySet) {
				if (properties.getProperty(key.toString()).equals(sportName)) {
					return key.toString();
				}
			}
			throw new RuntimeException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static String getSportName(String sportUrl) throws RuntimeException{
		Properties properties = new Properties();
		try(InputStream input = SportUrlMapper.class.getResourceAsStream(sport_properties_file);) {
			properties.load(input);
			String sportName = properties.getProperty(sportUrl);
			if (sportName != null) {
				return sportName;
			}
			throw new RuntimeException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
