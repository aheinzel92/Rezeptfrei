package objekte;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
 
// Webseitencodeleser wird generiert

public class WebseitencodeReaderChefkochDe {

	String[] rezeptTags;
	String[] zubereitungsInfos;
	String bildUrl;
	String rezeptTagsRueck;
	
	
	 public String getRezeptTagsRueck() {
		return rezeptTagsRueck;
	}

	public WebseitencodeReaderChefkochDe(String rezeptUrl) { 
		 
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
	 * Quellcode der Rezeptseite zu gelangen und als einfachen String zur�ck gibt 
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
		 quellcode = quellcode.replaceAll("&uuml;","�");
		 quellcode = quellcode.replaceAll("&Uuml;","�");
		 quellcode = quellcode.replaceAll("&ouml;","�");
		 quellcode = quellcode.replaceAll("&Ouml;","�");
		 quellcode = quellcode.replaceAll("&auml;","�");
		 quellcode = quellcode.replaceAll("&Auml;","�");
		 quellcode = quellcode.replaceAll("&szlig;","�");
		 
		 return quellcode;
	}
	
	// Extrahiert die zum Rezept geh�rigen Tags, die im Quellcode stehen
	public String[] rezeptTagsFiltern(String quellcode){
		
		 // Der Code wird nach bestimmten W�rtern gefiltert, die Differenz wird herausgeschnitten und in eine Variable gespeichert
		 rezeptTagsRueck = (quellcode.substring((quellcode.indexOf("Tags:") + 6), (quellcode.indexOf("robots") - 15)));
		 rezeptTagsRueck = umlauteErsetzen(rezeptTagsRueck);
		 
		 // Legt die einzelnen Kategorien in ein Array
		 String[] einzelKat = rezeptTagsRueck.split(", ");

		 return einzelKat;
	}
	
	// Extrahiert die Zubereitungsinformationen Arbeitszeit, Koch/Backzeit, Schwierigkeitsgrad und Kalorienangabe falls verf�gbar 
	public String[] zubereitugnsinformatinFiltern(String quellcode){
		String kochUndBackzeit;
		String kalorienAngabe;
		
		//Grobes zurechtschneiden des Bereichs mit allen Informationen, damit nicht mehrmals der gesamte Quellcode durchsucht werden muss
		 String quellcodeAbschnitt = quellcode.substring(quellcode.indexOf("Zubereitung</h2>"), (quellcode.indexOf("instructions")));
//		 System.out.println(quellcodeAbschnitt);
		 
		 try{
			 kochUndBackzeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Backzeit:</strong>") + 34), (quellcodeAbschnitt.indexOf("cookTime") - 28));
		 }catch(Exception e){
			 kochUndBackzeit = "keine Angabe";
		 }
		 
		 String arbeitszeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Arbeitszeit:</strong>") + 35), (quellcodeAbschnitt.indexOf("prepTime") - 26));
		 String schwierigkeitsgrad = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Schwierigkeitsgrad:</strong>") + 36), (quellcodeAbschnitt.indexOf("Kalorien") - 29));
		 try{
			 kalorienAngabe = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("p. P.") + 107), (quellcodeAbschnitt.indexOf("rezept-zubereitung") - 100));
		 }catch(Exception e)
		 {
			 kalorienAngabe = "keine Angabe";
		 }
//			 System.out.println(arbeitszeit);
//		 	 System.out.println(kochUndBackzeit);
//			 System.out.println(schwierigkeitsgrad);
//			 System.out.println(kalorienAngabe);
		 
		String[] zubereitungsinfo = {arbeitszeit, kochUndBackzeit, schwierigkeitsgrad, kalorienAngabe};
		
		
		return zubereitungsinfo;
	}
	
	// Extrahiert das erste Rezeptbild aus dem Bilder-Slider der Webseite
	public String vorschaubildFiltern(String quellcode){
		String bild = quellcode.substring((quellcode.indexOf("nivoSlider") + 116), (quellcode.indexOf("slideshow-imagelink") - 32));
//		System.out.println(bild);
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
