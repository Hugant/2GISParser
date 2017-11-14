package src;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Parser {
	private Document doc = null;
	
	private LinkedHashMap<String, String[]> positiveFilter = null;
	private LinkedHashMap<String, String[]> negativeFilter = null;
	
	public Parser(URL url, LinkedHashMap<String, String[]> positiveFilter,
			LinkedHashMap<String, String[]> negativeFilter) {
		this.positiveFilter = positiveFilter;
		this.negativeFilter = negativeFilter;
		
		try {
			this.doc = Jsoup.parse(url.openStream(), "UTF-8", Main.GIS_URL);
		} catch (IOException e) {
			System.out.println("Cannot open stream with " + url.toString());
			return;
		}
	}
	
	public String getName() {
		return this.doc.getElementsByClass("cardHeader__headerNameText").get(0).text();
	}
	
	public String getTypes() {
		String types;
		try {
			types = checkTypes(this.getWebsite(), positiveFilter.values().toArray(new String[positiveFilter.size()][]));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			types = "";
		}
		
		return types;
	}
	
	private String checkTypes(String url, String[]... params) throws IOException {
		String types = "";
		
		if (!url.contains("http")) {
			url = "http://" + url;
		}
		
		Document doc;
		try {
			doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
		} catch (IOException e) {
			throw new IOException("Bad url request: " + url);
		} catch (IllegalArgumentException e) {
			return "";
		}
		
		String html = doc.html().toLowerCase();
		
		for (Map.Entry<String, String[]> entry : negativeFilter.entrySet()) {
			for (String str : entry.getValue()) {
				html.replaceAll(str, "");
			}
		}
		
		types = searchTypes(html, params);
		
		
//		if (types.equals("")) {
//			Elements links = doc.select("a");
//			for (Element link : links) {
//				if (link.html().toLowerCase().contains("католог") ||
//						link.html().toLowerCase().contains("мебель для дома") ||
//						link.html().toLowerCase().contains("для дома") ||
//						link.html().toLowerCase().contains("категории")) {
//					types = searchTypes(html, params);
//					
//					if (!types.equals("")) {
//						break;
//					}
//				}
//			}
//		}
		
		return types;
	}
	
	private String searchTypes(String html, String[]... params) {
		String types = "";
		
		for (Map.Entry<String, String[]> entry : positiveFilter.entrySet()) {
			for (String str : entry.getValue()) {
				if (html.contains(str)) {
					if (!types.equals("")) {
						types += ", ";
					}
					
					types += entry.getKey();
					break;
				}
			}
		}
		
		return types;
	}
	
	public String getAddress() {
		String address = this.doc.getElementsByClass("card__addressLink").get(0).text();
		address = address.replaceAll("&nbsp;", " ");
		address = address.replaceAll("<br>", "");
		
		return address;
	}
	
	public String getPhones() {
		Elements elPhones = this.doc.getElementsByClass("contact__phonesItemLinkNumber");
		String phones = "";
		
		for (int i = 1; i < elPhones.size(); i++) {
			if (i != 1) phones += ", ";
			
			phones += elPhones.get(i).text();
		}
		
		return phones.replaceAll("\\+7", "8");
	}
	
	public String getEmails() {
		Elements elMails = this.doc.select("._type_email");
		String mails = "";
		
		for (int i = 0; i < elMails.size(); i++) {
			if (i != 0) 
				mails += ", ";
			
			mails += elMails.get(i).select(".contact__linkText").get(0).text();
		}
		
		return mails;
	}
	
	public String getWebsite() {
		Elements elSites = this.doc.select("._type_website");
		String sites = elSites.get(0).select(".contact__linkText").get(0).text();
		
//		for (int i = 0; i < elSites.size(); i++) {
//			if (i != 0) 
//				sites += ", ";
//			
//			if (elSites.get(i).select(".contact__linkText").size() == 0) {
//				break;
//			} else {
//				sites += elSites.get(i).select(".contact__linkText").get(0).text();
//			}
//			
//		}
		
		return sites;
	}
	
	public String getCity() {
		return "";
	}
	
	public CompanyCard getCard() {
		CompanyCard card = new CompanyCard();
		
		card.setProperty(Main.HEADER.get("CompanyName"), getName());
		card.setProperty(Main.HEADER.get("Phone"), 		 getPhones());
		card.setProperty(Main.HEADER.get("Email"), 		 getEmails());
		card.setProperty(Main.HEADER.get("City"), 		 Main.CITY_RU);
		card.setProperty(Main.HEADER.get("Website"),	 getWebsite());
		card.setProperty(Main.HEADER.get("Address"), 	 getAddress());
		card.setProperty(Main.HEADER.get("Sphere"), 	 getTypes());
		return card;
	}
}
