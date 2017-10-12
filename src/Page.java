package src;

import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Page {
	private URL[] cardsLinks;
	
	public Page(int number) throws Exception {
		URL pageUrl = new URL(	Main.GIS_URL + "/" + Main.CITY + "/search/" + 
								URLEncoder.encode(Main.REQUEST, "UTF-8") + "/page/" + 
								number + "/tab/firms?queryState=zoom%2F11");

		Document doc = Jsoup.parse(pageUrl.openStream(), "UTF-8", Main.GIS_URL);
		
		Elements articles = doc.select("._type_branch");
		cardsLinks = new URL[articles.size()];
		for (int i = 0; i < cardsLinks.length; i++) {
			cardsLinks[i] = new URL(Main.GIS_URL + articles.get(i).select("h3 a").attr("href"));
		}
	}
	
	public URL[] getLinks() {
		return cardsLinks;
	}
}
