package objekte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
 
// Webseitencodeleser wird generiert

public class WebseitencodeReaderLeckerDe {

	String[] rezeptTags;
	String[] zubereitungsInfos;
	String bildUrl;
	String rezeptTagsRueck;
	
	
	 public String getRezeptTagsRueck() {
		return rezeptTagsRueck;
	}

	public WebseitencodeReaderLeckerDe(String rezeptUrl) { 
		 
		 // Quellcode wird in Variable gespeichert
		 String quellcode = quelltextHolen(rezeptUrl);
		 rezeptTags = rezeptTagsFiltern(quellcode); 		// RezeptTags
		 zubereitungsInfos = zubereitugnsinformatinFiltern(quellcode);
		 bildUrl = vorschaubildFiltern(quellcode);
		 
//		  Gibt das Array aus
//		 for (int i = 0; i < rezeptTags.length; i++) {
//		   System.out.println(rezeptTags[i]);
//		 }
		 
		 
	 }
	 
	
	/* 
	 * Methode die, die Rezept URL aus den RSS-Feeds nutzt um an den 
	 * Quellcode der Rezeptseite zu gelangen und als einfachen String zurück gibt 
	 */
	public String quelltextHolen(String url) {
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
	
	// Methode die, die HTML-Code Umlaute und das scharfe S durch die entsprechenden Zeichen ersetzt
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
	
	// Extrahiert die zum Rezept gehörigen Tags, die im Quellcode stehen
	public String[] rezeptTagsFiltern(String quellcode){
		 // Der Code wird nach bestimmten Wörtern gefiltert, die Differenz wird herausgeschnitten und in eine Variable gespeichert
		 rezeptTagsRueck = (quellcode.substring((quellcode.indexOf("recipeType")), (quellcode.indexOf("duration"))));
		 rezeptTagsRueck = umlauteErsetzen(rezeptTagsRueck);
		 
		 // Legt die einzelnen Kategorien in ein Array
		 String[] einzelKat = {rezeptTagsRueck};
		 System.out.println(einzelKat[0]);

		 return einzelKat;
	}
	
	// Extrahiert die Zubereitungsinformationen Arbeitszeit, Koch/Backzeit, Schwierigkeitsgrad und Kalorienangabe falls verfügbar 
	public String[] zubereitugnsinformatinFiltern(String quellcode){

		String kalorienAngabe;
		
		//Grobes zurechtschneiden des Bereichs mit allen Informationen, damit nicht mehrmals der gesamte Quellcode durchsucht werden muss
		 int schnitt1 = (quellcode.indexOf("<!-- Typ -->"));
		 int schnitt2 = (quellcode.indexOf("<!-- Video -->"));
		 
		 String quellcodeAbschnitt = (quellcode.substring((schnitt1), (schnitt2)));
//		 System.out.println(quellcodeAbschnitt);
		 
		 String schwierigkeitsgrad = "Keine Angabe auf Lecker.de";
		 String kochUndBackzeit = "Keine Angabe auf Lecker.de";
		 String arbeitszeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("ilCooktime") + 35), (quellcodeAbschnitt.indexOf("&nbsp;min")));

		 try{
			 kalorienAngabe = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("recCal rightdotline") + 41), (quellcodeAbschnitt.indexOf("&nbsp;kcal")));
		 }catch(Exception e)
		 {
			 kalorienAngabe = "keine Angabe";
		 }
			 System.out.println("Lecker.de: " + arbeitszeit);
		 	 System.out.println("Lecker.de: " + kochUndBackzeit);
			 System.out.println("Lecker.de: " + schwierigkeitsgrad);
			 System.out.println("Lecker.de: " + kalorienAngabe);
		 
		String[] zubereitungsinfo = {arbeitszeit, kochUndBackzeit, schwierigkeitsgrad, kalorienAngabe};
		
		
		return zubereitungsinfo;
	}
	
	// Extrahiert das erste Rezeptbild aus dem Bilder-Slider der Webseite
	public String vorschaubildFiltern(String quellcode){
		String bild = quellcode.substring((quellcode.indexOf("og:image") + 19), (quellcode.indexOf("<!-- END contentview -->") - 5));
		System.out.println("Lecker.de: " + bild);
		return bild;
	}
	
	
	
	// GETTER
	public String getBildUrl(){
		return bildUrl;
	}
	
	public String[] getZubereitungsInfos(){
		return zubereitungsInfos;
	}
}
