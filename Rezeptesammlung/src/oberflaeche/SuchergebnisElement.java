
package oberflaeche;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import objekte.WebseitencodeReader;

public class SuchergebnisElement extends HBox {

	private ImageView vorschaubild;
	
	private String titel;
	private String beschreibung;
	private String tag;
	private String monat;
	private String jahr;
	private String inhalt;
	private String link;
	private String quelle;
	
	private String arbeitszeitLabel = "Arbeitszeit: ";
	private String kochbackzeitLabel = "Koch- und Backzeit: ";
	private String schwierigkeitLabel = "Schwierigkeitsgrad: ";
	private String kalorienppLabel = "Kalorien p.P.: ";
	private Label text;
	WebseitencodeReader quelltext;
	
	/* Konstruktor der Klasse, der falls kein Bild verf�gbar ist, ein Standardbild anzeigt
	 * 
	 */
	public SuchergebnisElement(String bildurl, 
							String beschreibung, 
							String titel, 
							String inhalt, 
							String link, 
							String tag, 
							String monat, 
							String jahr, 
							String quelle) 
	
	{
		this.titel = titel;
		this.link = link;
		try {
			quelltext = new WebseitencodeReader(link);
		} catch (Exception e) {
			System.out.println("Rezept von Lecker.de kann noch nicht verarbeitet werden.");
		}
		
		try {
			Image thumbnail = new Image(bildurl);
			this.vorschaubild = new ImageView(thumbnail);
			vorschaubild.setFitWidth(200);
			vorschaubild.setPreserveRatio(true);

		} catch (Exception e) {
			Image bild = new Image("oberflaeche/grafik/null.jpg");
			
			this.vorschaubild = new ImageView();
				vorschaubild.setImage(bild);
		}

		if (quelle.equals("chefkoch.de")) {
			this.getChildren().addAll(vorschaubild, chefkochWebInfosHolen());
		} else if (quelle.equals("lecker.de")) {
//			generiereLecker(inhalt, beschreibung);
		}
		
//		this.getChildren().addAll(vorschaubild, chefkochWebInfosHolen(link));
	}

	
	
	/* Methode die den String von Arbeitszeit, Koch/Backzeit, Schwierigkeit und Kalorien p.P.
	 * in die jeweiligen Elemente zerlegt, immer bei " / " wird getrennt.
	 * Die Elemente des Arrays, welches durch split() entsteht, werden in einzelne String-Variablen
	 * gespeichert.
	 */
	private Label chefkochWebInfosHolen(){
		String arbeitszeit = null;
		String kochbackzeit = null;
		String schwierigkeit = null;
		String kalorienpp = null;
		
		try{

//			System.out.println("tut");
			String[] array = quelltext.getZubereitungsInfos();
		
			arbeitszeit = array[0];
			kochbackzeit = array[1];
			schwierigkeit = array[2];
			kalorienpp = array[3];

		}catch(Exception e){
			e.printStackTrace();
//			System.out.println("nix zum zeilen umbrechen gefunden");
		}
	
		this.text = new Label( titel + "\n" + 
								arbeitszeitLabel + arbeitszeit + " / " + 
								kochbackzeitLabel + kochbackzeit + "\n" + 
								schwierigkeitLabel + schwierigkeit + "\n" + 
								kalorienppLabel + kalorienpp);
		this.text.setFont(new Font("Sylfaen",14));
		
		return text;
	}
	
	
//	private void generiereChefkoch(String inhalt, String beschreibung){
//		
//		this.inhalt = inhalt;
//		this.beschreibung = beschreibung;
//		System.out.println("chefkoch.de");
//	}
	
	private void generiereLecker(String inhalt, String beschreibung){

		this.inhalt = inhalt;
		this.beschreibung = beschreibung;
		System.out.println("lecker.de");
	}
	
	/* Eine Methode die mittels PixelReader() und WritableImage() ein �bergebenes Bild beschneidet und
	 * in den Ausschnitt mittig auf das Bild anwendet.
	 */
//	private Image cropImage(Image orgPic){
//		
//		int widthhalf = (int)(orgPic.getWidth()/2) - 110;
//		int heighthalf = (int)(orgPic.getHeight()/2) - 83;
//		
//		PixelReader reader = orgPic.getPixelReader();
//		WritableImage croppedImage = new WritableImage(reader, widthhalf, heighthalf, 220, 165);
//		
//		return croppedImage;
//	}
	
}
