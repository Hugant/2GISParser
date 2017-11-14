package src;

import java.util.LinkedHashMap;

public class Main {
	public static final String GIS_URL = "https://2gis.ru";
	
	public static final String CITY_RU = "Москва";
	private static final String CITY = "moscow";
	
	private static final String REQUEST = "мягкая мебель";
	
	private static final int START_PAGE = 1;
	private static final int END_PAGE = 0;
	
	public static final LinkedHashMap<String, String> HEADER = new LinkedHashMap<String, String>();
	
	private static LinkedHashMap<String, String[]> POSITIVE_FILTER = new LinkedHashMap<String, String[]>();
	private static LinkedHashMap<String, String[]> NEGATIVE_FILTER = new LinkedHashMap<String, String[]>();
	
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
		

		Analyst analyst = new Analyst(POSITIVE_FILTER, NEGATIVE_FILTER, REQUEST, CITY);
		analyst.start();
		
		long endWork = System.currentTimeMillis();
		
		Table table = new Table();
		table.setHeader(HEADER.values().toArray());
		table.fillTable(analyst.getData());
		table.write(CITY_RU + "_" + REQUEST + "_" + START_PAGE + "-" + END_PAGE + "_TEST2");
		
		
		long work = endWork - startWork;
		System.out.println("Работа завершена. Просмотренно " + "???" + " компаний."
				+ " В базу занесено " + analyst.getData().length + ". Затрачено " + 
				(work / 1000 / 60) + " минут " + (work / 1000 % 60) + " секунд");
	}
}
