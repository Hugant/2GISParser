package src;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;

public class Main {
	public static final String GIS_URL = "https://2gis.ru";
	
	public static final String CITY_RU = "Астана";
	private static final String CITY = "astana";
	
	private static final String REQUEST = "мягкая мебель";
	
	public static final LinkedHashMap<String, String> HEADER = new LinkedHashMap<String, String>();
	
	private static final LinkedHashMap<String, String[]> POSITIVE_FILTER = new LinkedHashMap<String, String[]>();
	private static final LinkedHashMap<String, String[]> NEGATIVE_FILTER = new LinkedHashMap<String, String[]>();
	
	public static void main(String[] args) throws Exception {
		long startWork = System.currentTimeMillis();
		
		HEADER.put("Name", "Название лида");
		HEADER.put("CompanyName", "Название (компания)");
		HEADER.put("Phone", "Рабочий телефон (компания)");
		HEADER.put("Email", "Рабочий email (компания)");
		HEADER.put("City", "Город");
		HEADER.put("Website", "Web");
		HEADER.put("Address", "Адрес");
		HEADER.put("Sphere", "Сфера деятельности");
		
		
		POSITIVE_FILTER.put("мягкая мебель", new String[] {"мягкой мебели", "диваны",
				"диванов", "диван", "мягкая мебель"});
		POSITIVE_FILTER.put("матрасы", new String[] {"матрасов", "матрацы", "матрас", "матрасы"});
		POSITIVE_FILTER.put("корпусная мебель", new String[] {"корпусная мебель", "корпусной мебели"});
		
		NEGATIVE_FILTER.put("детская мягкая мебель", new String[] {"детская мягкая мебель", 
				"детской мягкой мебели", "мягкая мебель для детей", "детские диваны"});
		NEGATIVE_FILTER.put("итальянская мягкая мебель", new String[] {"итальянская мягкая мебель",
				"мягкая мебель из италии", "мягкая итальянская мебель"});
		NEGATIVE_FILTER.put("офисная мягкая мебель", new String[] {"офисные диваны", "офисный диван", "диван офисный",
				"диван для офиса", "офисные диваны", "офисная мягкая мебель", "мягкая мебель для офиса", "мягкая офисная мебель",
				"офисной мягкой мебели", "офисных диванов"});
		
//		URL u = new URL("https://москва.рф/asdf&asd/f?asdf");
//		URLValidator v = new URLValidator("егэ-русский.рф/zadanie-vtoroe-ege-russkij/");
//		System.out.println(u.getHost());
//		System.out.println(u.getPath());
//		System.out.println(u.getProtocol());
//		System.out.println(u.getQuery());
//		System.out.println(u.getFile());
//	    String host = u.getHost();
//
//	    String[] labels = host.split("\\.");
//	    for (int i = 0; i < labels.length; i++) {
//	        labels[i] = java.net.IDN.toASCII(labels[i]);
//	        System.out.println(labels[i]);
//	    }
//	    host = String.join(".", labels);
//		
//		URL url = new URL(host);
//		System.out.println(url.getPath());
//		String c = "https://www.mos.ru/something=рак/";
//		c = URLEncoder.encode(c, "UTF-8");
//		String a = java.net.IDN.toASCII("https://москва.рф");
//	    System.out.println(c);
		
		
		Analyst analyst = new Analyst(POSITIVE_FILTER, NEGATIVE_FILTER, REQUEST, CITY);
		analyst.start();
		
		long endWork = System.currentTimeMillis();
		
		Table table = new Table();
		table.setHeader(HEADER.values().toArray());
		table.fillTable(analyst.getData());
		table.write(CITY_RU + "_" + REQUEST);
		
		
		long work = endWork - startWork;
		System.out.println("Работа завершена. Просмотренно " + "???" + " компаний."
				+ " В базу занесено " + analyst.getData().length + ". Затрачено " + 
				(work / 1000 / 60) + " минут " + (work / 1000 % 60) + " секунд");
	}
}
