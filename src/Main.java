package src;


import java.util.TreeMap;
import java.util.LinkedHashMap;

public class Main {
	public static final String GIS_URL = "https://2gis.ru";
	
	public static final String CITY_RU = "Иркутск";
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
		
		HEADER.put("Name", "Название лида");
		HEADER.put("CompanyName", "Название компании");
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
				
				card.setProperty(HEADER.get("CompanyName"),	parser.getName());
				card.setProperty(HEADER.get("Phone"), 		parser.getPhones());
				card.setProperty(HEADER.get("Email"), 		parser.getEmails());
				card.setProperty(HEADER.get("City"), 		Main.CITY_RU);
				card.setProperty(HEADER.get("Website"),		parser.getWebsite());
				card.setProperty(HEADER.get("Address"), 	parser.getAddress());
				card.setProperty(HEADER.get("Sphere"), 		parser.getTypes());
				
				if (card.isValid()) {
					table.fillRow(card);
					addedCounter++;
				}
				
				counter++;
				System.out.printf("%-25s%s%n",
						card.getProperties().get("Название компании"),
						card.getProperties().get("Город"));
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
	
	
	public static String lat2cyr(String s){
		StringBuilder sb = new StringBuilder(s.length());
		
		int i = 0;
		while (i < s.length()) {
			char ch = s.charAt(i);
			
			if (ch == 'J'){
				i++; 
				ch = s.charAt(i);
				
				switch (ch) {
					case 'E': sb.append('Ё'); break;
					case 'S':
						sb.append('Щ');
						i++; 
						if(s.charAt(i) != 'H') 
							throw new IllegalArgumentException("Illegal transliterated symbol at position "+i);
						break;
					case 'H': sb.append('Ь'); break;
					case 'U': sb.append('Ю'); break;
					case 'A': sb.append('Я'); break;
					default: 
						throw new IllegalArgumentException("Illegal transliterated symbol at position "+i);
				}
			} else if (i+1 < s.length() && s.charAt(i+1)=='H' 
					&& !(i+2 < s.length() && s.charAt(i+2)=='H')) {
				switch (ch) {
					case 'Z': sb.append('Ж'); break;
					case 'K': sb.append('Х'); break;
					case 'C': sb.append('Ч'); break;
					case 'S': sb.append('Ш'); break;
					case 'E': sb.append('Э'); break;
					case 'H': sb.append('Ъ'); break;
					case 'I': sb.append('Ы'); break;
					default:
						throw new IllegalArgumentException("Illegal transliterated symbol at position "+i);
				}
				
				i++;
			} else {
				switch (ch) {
					case 'A': sb.append('А'); break;
					case 'B': sb.append('Б'); break;
					case 'V': sb.append('В'); break;
					case 'G': sb.append('Г'); break;
					case 'D': sb.append('Д'); break;
					case 'E': sb.append('Е'); break;
					case 'Z': sb.append('З'); break;
					case 'I': sb.append('И'); break;
					case 'Y': sb.append('Й'); break;
					case 'K': sb.append('К'); break;
					case 'L': sb.append('Л'); break;
					case 'M': sb.append('М'); break;
					case 'N': sb.append('Н'); break;
					case 'O': sb.append('О'); break;
					case 'P': sb.append('П'); break;
					case 'R': sb.append('Р'); break;
					case 'S': sb.append('С'); break;
					case 'T': sb.append('Т'); break;
					case 'U': sb.append('У'); break;
					case 'F': sb.append('Ф'); break;
					case 'C': sb.append('Ц'); break;
					default: sb.append(ch);
				}
			}

			i++;
		}
		return sb.toString();
	}
}
