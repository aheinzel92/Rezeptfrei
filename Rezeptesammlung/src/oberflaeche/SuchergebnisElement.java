
package oberflaeche;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import objekte.WebseitencodeReaderChefkochDe;
import objekte.WebseitencodeReaderLeckerDe;

public class SuchergebnisElement extends HBox {

	private ImageView vorschaubild;
	
	private Label gerichtName = new Label();
	@SuppressWarnings("unused")
	private String beschreibung;
	@SuppressWarnings("unused")
	private String inhalt;
	@SuppressWarnings("unused")
	private String link;
	@SuppressWarnings("unused")
	private String quelle;
	
	private String arbeitszeitLabel = "Arbeitszeit: ";
	private String kochbackzeitLabel = "Koch- und Backzeit: ";
	private String ruhezeitLabel = "Ruhezeit: ";
	private String schwierigkeitLabel = "Schwierigkeitsgrad: ";
	private String kalorienppLabel = "Kalorien p.P.: ";
	
	private Label text;
	
	private boolean isChefkoch;
	
	private Button url_button = new Button("Zum Rezept");;
	
	//design der buttons
	String button_style_chefkoch = "-fx-background-color: linear-gradient(#658d1b 0%, #9ede29 100%)," // aeusserer Rand
														+ "linear-gradient(#9ede29 0%, #658d1b 100%)," // Hauptfläche 
														+ "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));"
									+ "-fx-background-radius: 5;"
									+ "-fx-background-insets: 0,1,2,3,0;"
									+ "-fx-text-fill: #ffffff;"
									+ "-fx-font-weight: bold;"
									+ "-fx-font-size: 12px;"
									+ "-fx-padding: 10 20 10 20;";
	
	String button_style_lecker = "-fx-background-color: linear-gradient(#5cadbd 0%, #71cfe2 100%)," // aeusserer Rand
														+ "linear-gradient(#71cfe2 0%, #5cadbd 100%)," // Hauptfläche 
														+ "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));"
									+ "-fx-background-radius: 5;"
									+ "-fx-background-insets: 0,1,2,3,0;"
									+ "-fx-text-fill: #ffffff;"
									+ "-fx-font-weight: bold;"
									+ "-fx-font-size: 12px;"
									+ "-fx-padding: 10 20 10 20;";
	
//	WebseitencodeReaderChefkochDe quelltextChefkochDe;
//	WebseitencodeReaderLeckerDe quelltextLeckerDe;
	
	
	// Konstruktor der Klasse
	public SuchergebnisElement(String bildurl, 
							String beschreibung, 
							String titel, 
							String inhalt, 
							String link, 
							String tag, 
							String monat, 
							String jahr, 
							String quelle, 
							String kochUndBackzeit, 
							String kalorienPP, 
							String ruhezeit, 
							String schwierigkeit, 
							String arbeitszeit) 
	
	{
		gerichtName.setText(titel);
		gerichtName.setStyle("-fx-font: 14 Arial; -fx-font-weight: bold;");
		
		this.link = link;
		this.isChefkoch = quelle.equals("chefkoch.de");
		
//		try {
//			if(isChefkoch){
//				quelltextChefkochDe = new WebseitencodeReaderChefkochDe(link);
//			}else {
//				quelltextLeckerDe = new WebseitencodeReaderLeckerDe(link);
//			}
//		} catch (Exception e) {
//			System.out.println("Fehler beim Verarbeiten der Daten/Webseite. Code neu an Quelltext anpassen!");
//		}
// bis hier löschen	
		
		// Der "Zur Seite"-Button wird generiert um das volle Rezept auf der entsprechnenden Plattform anzuzeigen.

		url_button.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
				if(isChefkoch){				
				url_button.setStyle(" -fx-background-color: linear-gradient(#658d1b 0%, #88bd26 50%, #9ede29 100%)," // aeusserer Rand
														+ "linear-gradient(#658d1b 0%, #88bd26 50%, #9ede29 100%)," // Hauptfläche 
														+ "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));"
									+ "-fx-background-radius: 5;"
									+ "-fx-background-insets: 0,1,2,3,0;"
									+ "-fx-text-fill: #ffffff;"
									+ "-fx-font-weight: bold;"
									+ "-fx-font-size: 12px;"
									+ "-fx-padding: 10 20 10 20;");
				}else{
				url_button.setStyle("-fx-background-color: linear-gradient(#71cfe2 0%, #5cadbd 100%)," // aeusserer Rand
														+ "linear-gradient(#5cadbd 0%, #71cfe2 100%)," // Hauptfläche 
														+ "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));"
									+ "-fx-background-radius: 5;"
									+ "-fx-background-insets: 0,1,2,3,0;"
									+ "-fx-text-fill: #ffffff;"
									+ "-fx-font-weight: bold;"
									+ "-fx-font-size: 12px;"
									+ "-fx-padding: 10 20 10 20;");
				}
				

			}
		});
		url_button.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(isChefkoch){
					url_button.setStyle(button_style_chefkoch);
				} else {
					url_button.setStyle(button_style_lecker);
				}
				try {
					Desktop.getDesktop().browse(new URI(link));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Das Vorschaubild wird gesetzt, falls keines vorhanden ist wird es nur eine Standardgrafik ersetzt
		try {
			Image thumbnail;
			if(isChefkoch){
				thumbnail = new Image(bildurl);
			}else {
				thumbnail = new Image(bildurl);
			}
//			Image thumbnail = new Image(bildurl);
			this.vorschaubild = new ImageView(thumbnail);
			vorschaubild.setFitWidth(200);
			vorschaubild.setPreserveRatio(true);

		} catch (Exception e) {
			Image bild = new Image("oberflaeche/grafik/null.jpg");
			
			this.vorschaubild = new ImageView();
				vorschaubild.setImage(bild);
		}

		this.text = new Label(  arbeitszeitLabel + arbeitszeit + " Min. / " + 
				kochbackzeitLabel + kochUndBackzeit + "\n" + 
				schwierigkeitLabel + schwierigkeit + "\n" + 
				kalorienppLabel + kalorienPP);
this.text.setFont(new Font("Sylfaen",14));
		
		// Hier wirden alle Daten gepackt und das schlussendliche Suchergebniselement erstellt
		if (isChefkoch) {
			url_button.setStyle(button_style_chefkoch);
			url_button.setText(url_button.getText() + " auf Chefkoch.de");
			
			VBox datenUndButton = new VBox();
				datenUndButton.getChildren().addAll(gerichtName, text, url_button);
				datenUndButton.setSpacing(5);
				datenUndButton.setPadding(new Insets(10,0,0,0));
			this.getChildren().addAll(vorschaubild, datenUndButton);
			this.setSpacing(10);
			this.setStyle("-fx-border-style: solid;"
	                + "-fx-border-width: 2;"
	                + "-fx-border-color: #658d1b");
		} else if (quelle.equals("lecker.de")) {
			url_button.setStyle(button_style_lecker);
			url_button.setText(url_button.getText() + " auf Lecker.de");
			
			VBox datenUndButton = new VBox();
				datenUndButton.getChildren().addAll(gerichtName, text, url_button);
				datenUndButton.setSpacing(5);
				datenUndButton.setPadding(new Insets(10,0,0,0));
			this.getChildren().addAll(vorschaubild, datenUndButton);
			this.setSpacing(10);
			this.setStyle("-fx-border-style: solid;"
	                + "-fx-border-width: 2;"
	                + "-fx-border-color: #5cadbd");
		}
		
//		this.getChildren().addAll(vorschaubild, chefkochWebInfosHolen(link));
	}

	
	
	/* Methode die den String von Arbeitszeit, Koch/Backzeit, Schwierigkeit und Kalorien p.P.
	 * in die jeweiligen Elemente zerlegt, immer bei " / " wird getrennt.
	 * Die Elemente des Arrays, welches durch split() entsteht, werden in einzelne String-Variablen
	 * gespeichert.
	 */
//	private Label chefkochWebInfosHolen(){
//		String arbeitszeit = null;
//		String kochbackzeit = null;
//		String ruhezeit = null;
//		String schwierigkeit = null;
//		String kalorienpp = null;
		
//		try{
//			String[] array = quelltextChefkochDe.getZubereitungsInfos();
//		
//			arbeitszeit = array[0];
//			kochbackzeit = array[1];
//			ruhezeit = array[2];
//			schwierigkeit = array[3];
//			kalorienpp = array[4];
//
//		}catch(Exception e){
////			e.printStackTrace();
//		}
//	
//		this.text = new Label(	arbeitszeitLabel + arbeitszeit + " / " + 
//								kochbackzeitLabel + kochbackzeit + "\n" + 
//								ruhezeitLabel + ruhezeit + "\n" + 
//								schwierigkeitLabel + schwierigkeit + "\n" + 
//								kalorienppLabel + kalorienpp);
//		this.text.setFont(new Font("Sylfaen",14));
//		
//		return text;
//	}
	
	
//	private Label leckerWebInfosHolen(){
//		String arbeitszeit = null;
//		String kochbackzeit = null;
//		String schwierigkeit = null;
//		String kalorienpp = null;
//		
//		try{
//			String[] array = quelltextLeckerDe.getZubereitungsInfos();
//		
//			arbeitszeit = array[0];
//			kochbackzeit = array[1];
//			schwierigkeit = array[2];
//			kalorienpp = array[3];
//
//		}catch(Exception e){
////			e.printStackTrace();
//		}
//	
//		this.text = new Label(  arbeitszeitLabel + arbeitszeit + " Min. / " + 
//								kochbackzeitLabel + kochbackzeit + "\n" + 
//								schwierigkeitLabel + schwierigkeit + "\n" + 
//								kalorienppLabel + kalorienpp);
//		this.text.setFont(new Font("Sylfaen",14));
//		
//		return text;
//	}
	
}
