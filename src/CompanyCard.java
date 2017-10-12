package src;


import java.util.HashMap;

public class CompanyCard {
	private HashMap<String, String> properties = new HashMap<String, String>();

	public CompanyCard() {}

	public HashMap<String, String> getProperties() {
		return this.properties;
	}
	
	public void setProperty(String key, String value) {
		properties.put(key, value);
	}
	
	public boolean isValid() {
		return true;
	}
}
