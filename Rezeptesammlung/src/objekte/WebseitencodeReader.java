package objekte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
 
// Webseitencodeleser wird generiert

public class WebseitencodeReader {

	String[] rezeptTags;
	String[] zubereitugnsInfos;
	String bildUrl;
	String rezeptTagsRueck;
	
	
	 public String getRezeptTagsRueck() {
		return rezeptTagsRueck;
	}

	public WebseitencodeReader(String rezeptUrl) { 
		 
		 // Quellcode wird in Variable gespeichert
		 String quellcode = seiteninhaltHolen(rezeptUrl);
		 rezeptTags = rezeptTagsFiltern(quellcode); 		// RezeptTags
		 zubereitugnsInfos = zubereitugnsinformatinFiltern(quellcode);
		 bildUrl = vorschaubildFiltern(quellcode);
		 
//		  Gibt das Array aus
//		 for (int i = 0; i < rezeptTags.length; i++) {
//		   System.out.println(rezeptTags[i]);
//		 }
		 
		 
	 }
	 
	public String seiteninhaltHolen(String url) {
		StringBuilder sb = new StringBuilder();
		try {
			Scanner scanner = new Scanner(new URL(url).openStream());
			while (scanner.hasNextLine()) {
				sb.append(scanner.nextLine() + "\n");

			}
			scanner.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public String umlauteErsetzen(String quellcode){
		 // HTML Umlaute werden ersetzt
		 quellcode = quellcode.replaceAll("&uuml;","ü");
		 quellcode = quellcode.replaceAll("&Uuml;","Ü");
		 quellcode = quellcode.replaceAll("&ouml;","ö");
		 quellcode = quellcode.replaceAll("&Ouml;","Ö");
		 quellcode = quellcode.replaceAll("&auml;","ä");
		 quellcode = quellcode.replaceAll("&Auml;","Ä");
		 quellcode = quellcode.replaceAll("&szlig;","ß");
		 
		 return quellcode;
	}
	
	public String[] rezeptTagsFiltern(String quellcode){
		 // Der Code wird nach bestimmten Wörtern gefiltert, die Differenz wird herausgeschnitten und in eine Variable gespeichert
		 int schnitt1 = (quellcode.indexOf("Tags:") + 6);
		 int schnitt2 = (quellcode.indexOf("robots") - 15);
		 rezeptTagsRueck = (quellcode.substring((schnitt1), (schnitt2)));
		 	rezeptTagsRueck = umlauteErsetzen(rezeptTagsRueck);
		 
		 // Legt die einzelnen Kategorien in ein Array
		 String[] einzelKat = rezeptTagsRueck.split(", ");

		 return einzelKat;
	}
	
	public String[] zubereitugnsinformatinFiltern(String quellcode){
		
		String kochUndBackzeit;
		 int schnitt1 = (quellcode.indexOf("Zubereitung</h2>"));
		 int schnitt2 = (quellcode.indexOf("instructions"));
		 String quellcodeAbschnitt = (quellcode.substring((schnitt1), (schnitt2)));
//		 System.out.println(quellcodeAbschnitt);
		 
		 try{
			 kochUndBackzeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Backzeit:</strong>") + 34), (quellcodeAbschnitt.indexOf("cookTime") - 28));
		 }catch(Exception e){
			 kochUndBackzeit = "keine Angabe";
		 }
		 
		 String arbeitszeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Arbeitszeit:</strong>") + 35), (quellcodeAbschnitt.indexOf("prepTime") - 26));
		 String schwierigkeitsgrad = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Schwierigkeitsgrad:</strong>") + 36), (quellcodeAbschnitt.indexOf("Kalorien") - 29));
		 String kalorienAngabe = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("p. P.") + 38), (quellcodeAbschnitt.indexOf("rezept-zubereitung") - 72));
		 
//			 System.out.println(arbeitszeit);
//		 	 System.out.println(kochUndBackzeit);
//			 System.out.println(schwierigkeitsgrad);
//			 System.out.println(kalorienAngabe);
		 
		String[] zubereitungsinfo = {arbeitszeit, kochUndBackzeit, schwierigkeitsgrad, kalorienAngabe};
		
		
		return zubereitungsinfo;
	}
	
	public String vorschaubildFiltern(String quellcode){
		
		String bild = quellcode.substring((quellcode.indexOf("nivoSlider") + 116), (quellcode.indexOf("slideshow-imagelink") - 32));
//		System.out.println(bild);
		return bild;
	}
	
	
	
	
	public String getBildUrl(){
		return bildUrl;
	}
	
	public String[] getZubereitungsInfos(){
		return zubereitugnsInfos;
	}
}
