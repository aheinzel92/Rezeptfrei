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
		try{
		 rezeptTagsRueck = (quellcode.substring((quellcode.indexOf("Tags:") + 6), (quellcode.indexOf("robots") - 15)));
		 } catch (Exception e){
			 System.out.println("Fehler beim Tags lesen");
		 }
		try{
		 rezeptTagsRueck = umlauteErsetzen(rezeptTagsRueck);
		} catch (Exception e){
			 System.out.println("Fehler beim Umlaute ersetzen");
		}
		 
		 // Legt die einzelnen Kategorien in ein Array
		 String[] einzelKat = rezeptTagsRueck.split(", ");

		 return einzelKat;
	}
	
	// Extrahiert die Zubereitungsinformationen Arbeitszeit, Koch/Backzeit, Schwierigkeitsgrad und Kalorienangabe falls verfügbar 
	public String[] zubereitugnsinformatinFiltern(String quellcode){
		String kochUndBackzeit;
		String kalorienAngabe;
		String arbeitszeit = "";
		String schwierigkeitsgrad = "";
		String quellcodeAbschnitt = "";
		String ruhezeit = "";
		
		//Grobes zurechtschneiden des Bereichs mit allen Informationen, damit nicht mehrmals der gesamte Quellcode durchsucht werden muss
		try{
			quellcodeAbschnitt = quellcode.substring(quellcode.indexOf("Zubereitung</h2>"), (quellcode.indexOf("instructions")));
//			System.out.println(quellcodeAbschnitt);
		 } catch (Exception e){
			 System.out.println("Fehler beim quellcode holen");
		 }

		 try{
		 arbeitszeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Arbeitszeit:</strong>") + 35), (quellcodeAbschnitt.indexOf("Koch-") - 39));
		 } catch (Exception e){
//			 System.out.println(quellcodeAbschnitt);
			 arbeitszeit = "keine Angabe";
//			 System.out.println(arbeitszeit);
//			 System.out.println("Fehler beim arbeitszeit holen");
		 }
		 
		 try{
			try {
				kochUndBackzeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Backzeit:</strong>") + 34),	(quellcodeAbschnitt.indexOf("<strong>Ruhezeit:") - 36));
				ruhezeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Ruhezeit:</strong>") + 34), (quellcodeAbschnitt.indexOf("<strong>Schwierigkeitsgrad") - 28));
			} catch (Exception e) {
				ruhezeit = "keine Angabe";
				kochUndBackzeit = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Backzeit:</strong>") + 34), (quellcodeAbschnitt.indexOf("<strong>Schwierigkeitsgrad") - 41));
			}
		 }catch(Exception e){
//			 System.out.println("Fehler beim KochUndBackzeit holen");
			 kochUndBackzeit = "keine Angabe";
		 }

		 
		 try{
		 schwierigkeitsgrad = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("Schwierigkeitsgrad:</strong>") + 36), (quellcodeAbschnitt.indexOf("Kalorien") - 29));
		 } catch (Exception e){
			 schwierigkeitsgrad = "keine Angabe"; 
//			 System.out.println("Fehler beim schwierigkeitsgrad holen");
		 }
		 try{
			 kalorienAngabe = quellcodeAbschnitt.substring((quellcodeAbschnitt.indexOf("p. P.") + 38), (quellcodeAbschnitt.indexOf("rezept-zubereitung") - 46));
		 }catch(Exception e)
		 {
			 kalorienAngabe = "keine Angabe";
//			 System.out.println("Fehler beim Kalorien holen");
		 }

		 String[] zubereitungsinfo = {arbeitszeit, kochUndBackzeit, ruhezeit, schwierigkeitsgrad, kalorienAngabe};

		return zubereitungsinfo;
	}
	
	// Extrahiert das erste Rezeptbild aus dem Bilder-Slider der Webseite
	public String vorschaubildFiltern(String quellcode){
		String bild = quellcode.substring((quellcode.indexOf("nivoSlider") + 59), (quellcode.indexOf("slideshow-imagelink") - 32));
		System.out.println(bild);

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
