package src;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class Analyst {
	private TreeMap<String, CompanyCard> companies = new TreeMap<String, CompanyCard>();
	
	private LinkedHashMap<String, String[]> positiveFilter = null;
	private LinkedHashMap<String, String[]> negativeFilter = null;
	
	private String request = null;
	private String city = null;
	
	public Analyst(LinkedHashMap<String, String[]> positiveFilter, LinkedHashMap<String, String[]> negativeFilter, String request, String city) {
		this.positiveFilter = positiveFilter;
		this.negativeFilter = negativeFilter;
		this.request = request;
		this.city = city;
	}
	
	public void start() {
		PageViewer pageViewer = new PageViewer(city, request);
		int pageCounter = 0;
		
		for (Page page : pageViewer.getPages()) {
			for (URL url : page.getLinks()) {
				Parser parser = new Parser(url, positiveFilter, negativeFilter);
				
				System.out.print(parser.getName() + "\t\t types: " + parser.getTypes());
				
				if (!companies.containsKey(parser.getName())) {
					CompanyCard card = parser.getCard();
					
					if (card.isValid()) {
						companies.put(card.get(Main.HEADER.get("CompanyName")), card);
						System.out.print(" +");
					}
				}
				
				System.out.println();
			}
			
			System.out.println("-------------------------");
			System.out.println("Page "+ (++pageCounter) + " viewed");
			System.out.println("-------------------------");
		}
	}
	
	public CompanyCard[] getData() {
		return companies.values().toArray(new CompanyCard[companies.size()]);
	}
}
