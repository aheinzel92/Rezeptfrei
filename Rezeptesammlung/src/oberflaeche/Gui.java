// Bitte String[] als Rückgabe für Kategorien. Danke! :)

package oberflaeche;

import einlesen.Methoden;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import objekte.Suchobjekt;
import suche.Suche;

public class Gui extends VBox{

	private int suchtreffer;
	SuchergebnisElement tempRezept;
	TextField suchfeld = new TextField();
	VBox suchergebnisse = new VBox();
	einlesen.Methoden meth;
	Label ergebnisanzahl = new Label();
	
	RadioButton oderSuche;
	RadioButton undSuche;

	String[] suchkategorien = { "Vorspeise", 
			"Hauptspeise", 
			"Dessert",
			"Beilage", 
			"Salat", 
			"Suppen", 
			"Back-/Süßspeisen", 
			"Getränke",	
			"Frühstück" };

CheckBox[] auswahl = new CheckBox[suchkategorien.length];
	
	
	
	
	
	public Gui(einlesen.Methoden meth) {

		this.meth = meth;
		Button suchtaste = new Button("Suchen");
			suchtaste.setOnAction(e -> suchen());

		suchfeld.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					suchen();
					keyEvent.consume();
				}
			}
		});

		Label zutatenLabel = new Label("Zutaten: ");

		MenuBar menueLeiste = new MenuBar();
		Menu datei = new Menu("Datei");
		MenuItem beenden = new MenuItem("Beenden");
				beenden.setMnemonicParsing(true);
				beenden.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Platform.exit();
					}
				});
				
		MenuItem neuesRezept = new MenuItem("Rezept eintragen");
				neuesRezept.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event){
						Scene neuesRezeptScene = new Scene(new RezeptAnlegen());
						Stage neuesRezeptStage = new Stage();
						neuesRezeptStage.setScene(neuesRezeptScene);
						neuesRezeptStage.show();
					}
				});

		datei.getItems().addAll(beenden, neuesRezept);
		
		Menu suchoptionen = new Menu("Suchoptionen");
//		Menu webseite;



		for (int i = 0; i < suchkategorien.length; i++) {

			CheckBox cb = new CheckBox(suchkategorien[i]);
				cb.setStyle("-fx-text-fill: -fx-text-base-color");

			CustomMenuItem cmi = new CustomMenuItem(cb);
				cmi.setHideOnClick(false);
				
			auswahl[i] = cb;
			suchoptionen.getItems().add(cmi);
		}

		suchoptionen.getItems().add(new MenuItem("Auswahlmenü schließen"));

		menueLeiste.getMenus().addAll(datei, suchoptionen);
		
		oderSuche = new RadioButton("Oder-Suche");
		undSuche = new RadioButton("Und-Suche");
			undSuche.setSelected(true);
		ToggleGroup radioButtonGruppe = new ToggleGroup();
			radioButtonGruppe.getToggles().addAll(undSuche, oderSuche);

		ToolBar obereWerkzeugleiste = new ToolBar();
			obereWerkzeugleiste.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth());
			obereWerkzeugleiste.getItems().addAll(menueLeiste, new Separator(), zutatenLabel, suchfeld, suchtaste, undSuche, oderSuche);
//			obereWerkzeugleiste.setStyle("-fx-background-color: bisque;");

		ToolBar untereWerkzeugleiste = new ToolBar();
			untereWerkzeugleiste.getItems().add(ergebnisanzahl);
			untereWerkzeugleiste.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth());
//			untereWerkzeugleiste.setStyle("-fx-background-color: bisque;");


		ScrollPane scrollpane = new ScrollPane();
			scrollpane.setContent(suchergebnisse);

		this.getChildren().addAll(obereWerkzeugleiste, scrollpane, untereWerkzeugleiste);
		this.setVgrow(scrollpane, Priority.ALWAYS);
	}

	private void suchen(){
		try{
			
			if(!suchergebnisse.getChildren().isEmpty()){
				suchergebnisse.getChildren().clear();
			}
			
			
			// hole aktivierte Suchkritieren aus auswahl[] BEGINN
			String ausgewaehlteSuchkriterien[] = new String[auswahl.length];
			for(int i = 0; i < auswahl.length; i++){
				CheckBox chkbox = auswahl[i];
				if(chkbox.isSelected()){
					ausgewaehlteSuchkriterien[i] = chkbox.getText();
					System.out.println(ausgewaehlteSuchkriterien[i]);
				}
			}
			// hole aktivierte Suchkritieren aus auswahl[] ENDE
			
			String sucheingabe = suchfeld.getText();
			Suche neueSuche = new Suche();
			Methoden suchenKategorie = new Methoden();

//			Suchobjekt[] gefundeneRezepte = neueSuche.suchen(meth.tildeHinzufuegen(sucheingabe, oderSuche.isSelected()));
			Suchobjekt[] gefundeneRezepte = suchenKategorie.kategorie(meth.tildeHinzufuegen(sucheingabe, oderSuche.isSelected()), ausgewaehlteSuchkriterien);
			suchtreffer = gefundeneRezepte.length;
			ergebnisanzahl.setText(String.format(
				"Ihre Suche ergab %1$d Treffer", suchtreffer));
//			System.out.println("SUCHTREFFER: " + suchtreffer);

			for (int i = 0; i < suchtreffer; i++) {
				tempRezept = new SuchergebnisElement(gefundeneRezepte[i].getBild(),
														gefundeneRezepte[i].getBeschreibung(),
														gefundeneRezepte[i].getTitel(),
														gefundeneRezepte[i].getInhalt(),
														gefundeneRezepte[i].getLink(),
														gefundeneRezepte[i].getTag(),
														gefundeneRezepte[i].getMonat(),
														gefundeneRezepte[i].getJahr(),
														gefundeneRezepte[i].getQuelle());

				suchergebnisse.getChildren().add(tempRezept);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		};
	}
	

}