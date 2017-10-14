package src;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
		String types;
		try {
			types = checkTypes(this.getWebsite(), 
					new String[] {"мягкая мебель", "мягкой мебели", "диваны", "диванов", "мягкая мебель"},
					new String[] {"матрасы", "матрасов", "матрацы", "матрасы"});
		} catch (IOException e) {
			types = "";
		}
		
		return types;
	}
	
	private String checkTypes(String url, String[]... params) throws IOException {
		String types = "";
		
		if (!url.contains("http://")) {
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
		
		types = searchTypes(html, params);
		
		if (types.equals("")) {
			Elements links = doc.select("a");
			for (Element link : links) {
				if (link.html().toLowerCase().contains("католог")) {
					types = searchTypes(html, params);
					
					if (!types.equals("")) {
						break;
					}
				}
			}
		}
		
		return types;
	}
	
	private String searchTypes(String html, String[]... params) {
		String types = "";
		
		for (int i = 0; i < params.length; i++) {
			for (int j = 0; j < params[i].length - 1; j++) {
				if (html.contains(params[i][j])) {
					if (!types.equals("")) {
						types += ", ";
					}
					
					types += params[i][params[i].length - 1];
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
