package src;

import java.util.ArrayList;

public class PageViewer {
	private ArrayList<Page> pages = new ArrayList<Page>();

	public PageViewer(String city, String request) {
		System.out.print("Parsing of pages: ");
		for (int i = 1; ; i++) {
			try {
				pages.add(new Page(city, request, i));
			} catch (java.io.IOException e) {
				break;
			}
			System.out.print("|");
		}
		
		System.out.println("\nNumber of pages: " + pages.size());
	}
	
	public Page[] getPages() {
		return pages.toArray(new Page[pages.size()]);
	}
}
