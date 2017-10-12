package src;


import java.util.TreeMap;
import java.util.LinkedHashMap;

public class Main {
	public static final String GIS_URL = "https://2gis.ru";
	
	public static final String CITY = "irkutsk";
	public static final String REQUEST = "мягкая мебель";
	
	private static final int START_PAGE = 1;
	private static final int END_PAGE = 2;
	
	private static final LinkedHashMap<String, String> HEADER = new LinkedHashMap<String, String>();
	private static final TreeMap<String, CompanyCard> COMPANIES= new TreeMap<String, CompanyCard>();
	
	
	public static void main(String[] args) throws Exception {
		long startWork = System.currentTimeMillis();
		int counter = 0;
		int addedCounter = 0;
		
		HEADER.put("Name", "Название");
		HEADER.put("Phone", "Телефон");
		HEADER.put("Email", "Почта");
		HEADER.put("City", "Город");
		HEADER.put("Website", "Сайт");
		HEADER.put("Address", "Адрес");
		HEADER.put("Sphere", "Сфера деятельности");

		Table table = new Table();
		table.setHeader(HEADER.values().toArray());
		
		for (int i = START_PAGE; i < END_PAGE; i++) {
			Page page;
			
			try {
				page = new Page(i);
			} catch (org.jsoup.HttpStatusException | java.io.FileNotFoundException e) {
				break;
			}
			
			for (int j = 0; j < page.getLinks().length; j++) {
				Parser parser = new Parser(page.getLinks()[j], GIS_URL);
				CompanyCard card = new CompanyCard();
				
				card.setProperty(HEADER.get("Name"), 	parser.getName());
				card.setProperty(HEADER.get("Phone"), 	parser.getPhones());
				card.setProperty(HEADER.get("Email"), 	parser.getEmails());
				card.setProperty(HEADER.get("City"), 	parser.getCity());
				card.setProperty(HEADER.get("Website"), parser.getWebsite());
				card.setProperty(HEADER.get("Address"), parser.getAddress());
				card.setProperty(HEADER.get("Sphere"), 	parser.getTypes());
				
				if (card.isValid()) {
					table.fillRow(card);
					addedCounter++;
				}
				
				counter++;
				System.out.printf("%-25s%s%n",card.getProperties().get("Название"),
						card.getProperties().get("Сайт"));
			}
			
			System.out.println("Страница " + i + " просмотренна");
		}
		
		table.write("test");
		
		long endWork = System.currentTimeMillis();
		long work = endWork - startWork;
		System.out.println("Работа завершена. Просмотренно " + counter + " компаний."
				+ " В базу занесено " + addedCounter + ". Затрачено " + 
				(work / 1000 / 60) + " минут " + (work / 1000 % 60) + " секунд");
	}
}
