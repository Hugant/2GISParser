package src;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Parser {
	private Document doc = null;
	
	public Parser(URL url, String domain) throws Exception {
		this.doc = Jsoup.parse(url.openStream(), "UTF-8", domain);
	}
	
	public String getName() {
		return this.doc.getElementsByClass("cardHeader__headerNameText").get(0).text();
	}
	
	public String getTypes() {
		String types = "";
		String domain = this.getWebsite();
		
		if (!domain.contains("http://")) {
			domain = "http://" + domain;
		}
		System.out.println(domain);
		
		if (domain != null && !domain.equals("")) {
			Document doc;
			try {
				doc = Jsoup.parse(new URL(domain).openStream(), "UTF-8", domain);
			} catch (IOException e) {
				return "";
			}
			
			String html = doc.html().toLowerCase();
			
			
			if (html.contains("мягкая мебель") ||
					html.contains("мягкой мебели") ||
					html.contains("диваны")) {
				types += "мягкая мебель,";
			}
			
			if (html.contains("матрасы") || 
					html.contains("матрацы")) {
				types += "матрасы,";
			}
			
			if (html.contains("корпусная мебель") || 
					html.contains("корпусной мебели")) {
				types += "корпусная мебель";
			}
			
			if (!types.equals("")) {
				types = types.substring(0, types.length() - 1);
				types = types.replace("корпусная мебель", "");
			} else { 
				
			}
			
			
			
				
			
			
			
		}
		
		return "";
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
		String sites = "";
		
		for (int i = 0; i < elSites.size(); i++) {
			if (i != 0) 
				sites += ", ";
			
			if (elSites.get(i).select(".contact__linkText").size() == 0) {
				break;
			} else {
				sites += elSites.get(i).select(".contact__linkText").get(0).text();
			}
			
		}
		
		return sites;
	}
	
	public String getCity() {
		return "";
	}
}
