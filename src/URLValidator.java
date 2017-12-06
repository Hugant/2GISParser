package src;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLValidator {
	URL url = null;
	
	public URLValidator(String link) {
		if (!link.contains("http")) {
			link = "https://" + link;
		}
		
		try {
			URL tempURL = new URL(link);
			String[] files = tempURL.getFile().split("/");
			String[] hostes = tempURL.getHost().split("\\.");
			
			for (int i = 0; i < files.length; i++) {
				files[i] = URLDecoder.decode(files[i], "UTF-8");
				files[i] = URLEncoder.encode(files[i], "UTF-8");
			}
			
			for (int i = 0; i < hostes.length; i++) {
				hostes[i] = java.net.IDN.toASCII(hostes[i]);
			}
			
			System.out.print(String.join(".", hostes));
			System.out.println(String.join("/", files));
			
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public URL getURL() {
		return this.url;
	}
}
