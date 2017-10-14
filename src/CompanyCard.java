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
		if (properties.get(Main.HEADER.get("CompanyName")).equals("")) 	return false;
		if (properties.get(Main.HEADER.get("Website")).equals("")) 		return false;
		if (properties.get(Main.HEADER.get("Address")).equals("")) 		return false;
		if (properties.get(Main.HEADER.get("Sphere")).equals("")) 		return false;
		if (properties.get(Main.HEADER.get("Phone")).equals("")) 		return false;
		if (properties.get(Main.HEADER.get("City")).equals("")) 		return false;
		
		return true;
	}
}
