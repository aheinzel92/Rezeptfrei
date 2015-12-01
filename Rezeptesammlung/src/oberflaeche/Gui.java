package oberflaeche;

import objekte.Suchobjekt;
import suche.Suche;
import javafx.application.Application;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Gui extends Application {

	private int suchtreffer;

	public void start(Stage primaryStage) {

		TextField suchfeld = new TextField();
		Button suchtaste = new Button("Suchen");
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

		datei.getItems().add(beenden);
		
		SuchergebnisElement tempRezept;

		Menu suchoptionen = new Menu("Suchoptionen");
		Menu webseite;

		String[] auflistung = { "Vorspeise", "Hauptspeise", "Dessert",
								"Beilage", "Salat", "Suppen", 
								"Back-/Süßspeisen", "Getränke",	"Frühstück" };
		CheckBox[] auswahl = new CheckBox[auflistung.length];

		for (int i = 0; i < auflistung.length; i++) {

			CheckBox cb = new CheckBox(auflistung[i]);
			cb.setStyle("-fx-text-fill: -fx-text-base-color");

			CustomMenuItem cmi = new CustomMenuItem(cb);
			cmi.setHideOnClick(false);
			auswahl[i] = cb;
			suchoptionen.getItems().add(cmi);
		}

		suchoptionen.getItems().add(new MenuItem("Auswahlmenü schließen"));


		menueLeiste.getMenus().addAll(datei, suchoptionen);

		ToolBar obereWerkzeugleiste = new ToolBar();
			obereWerkzeugleiste.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth());
			obereWerkzeugleiste.getItems().addAll(menueLeiste, new Separator(), zutatenLabel, suchfeld, suchtaste);
			obereWerkzeugleiste.setStyle("-fx-background-color: bisque;");

		Label ergebnisanzahl = new Label(String.format(
				"Ihre Suche ergab %1$d Treffer", suchtreffer));

		ToolBar untereWerkzeugleiste = new ToolBar();
			untereWerkzeugleiste.getItems().add(ergebnisanzahl);
			untereWerkzeugleiste.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth());
			untereWerkzeugleiste.setStyle("-fx-background-color: bisque;");

		VBox suchergebnisse = new VBox();


		/*
		 * try{ suchtreffer = rezepteFoundList.length; for(int i = 0; i <
		 * rezepteFoundList.length; i++){
		 * 
		 * tempRezept = rezeptearray[i];
		 * suchergebnisse.getChildren().add(tempRezept);
		 * 
		 * } catch (Exception e){
		 * 
		 * }
		 */
		try{
		String sucheingabe = suchfeld.getText();
		Suche neueSuche = new Suche();
		
			Suchobjekt[] gefundeneRezepte = neueSuche.suchen(sucheingabe);
			suchtreffer = gefundeneRezepte.length;
			
			for(int i = 0; i < suchtreffer; i++){
						
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
			
		};

		ScrollPane scrollpane = new ScrollPane();
			scrollpane.setContent(suchergebnisse);

		VBox mainlayout = new VBox();
			mainlayout.getChildren().addAll(obereWerkzeugleiste, scrollpane, untereWerkzeugleiste);

		Scene scene = new Scene(mainlayout);

		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Omnomnom");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch();
	}

}