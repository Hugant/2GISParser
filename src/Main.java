package src;


import java.util.TreeMap;
import java.util.LinkedHashMap;

public class Main {
	public static final String GIS_URL = "https://2gis.ru";
	
	public static final String CITY = "irkutsk";
	public static final String REQUEST = "������ ������";
	
	private static final int START_PAGE = 1;
	private static final int END_PAGE = 2;
	
	private static final LinkedHashMap<String, String> HEADER = new LinkedHashMap<String, String>();
	private static final TreeMap<String, CompanyCard> COMPANIES= new TreeMap<String, CompanyCard>();
	
	
	public static void main(String[] args) throws Exception {
		long startWork = System.currentTimeMillis();
		int counter = 0;
		int addedCounter = 0;
		
		HEADER.put("Name", "��������");
		HEADER.put("Phone", "�������");
		HEADER.put("Email", "�����");
		HEADER.put("City", "�����");
		HEADER.put("Website", "����");
		HEADER.put("Address", "�����");
		HEADER.put("Sphere", "����� ������������");

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
				System.out.printf("%-25s%s%n",card.getProperties().get("��������"),
						card.getProperties().get("����"));
			}
			
			System.out.println("�������� " + i + " ������������");
		}
		
		table.write("test");
		
		long endWork = System.currentTimeMillis();
		long work = endWork - startWork;
		System.out.println("������ ���������. ������������ " + counter + " ��������."
				+ " � ���� �������� " + addedCounter + ". ��������� " + 
				(work / 1000 / 60) + " ����� " + (work / 1000 % 60) + " ������");
	}
}
