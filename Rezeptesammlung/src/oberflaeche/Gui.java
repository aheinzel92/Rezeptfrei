package oberflaeche;

import einlesen.Methoden;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
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

public class Gui extends VBox{

	private int suchtreffer;
	SuchergebnisElement tempRezept;
	einlesen.Methoden meth;
	TextField suchfeld = new TextField();
	VBox suchergebnisse = new VBox();
	Label ergebnisanzahl = new Label();
	
	RadioButton oderSuche;
	RadioButton undSuche;
//	
	RadioMenuItem vorspeise = new RadioMenuItem("Vorspeise");
	RadioMenuItem hauptspeise = new RadioMenuItem("Hauptspeise");
	RadioMenuItem dessert = new RadioMenuItem("Dessert");
	RadioMenuItem beilage = new RadioMenuItem("Beilage");
	RadioMenuItem salat = new RadioMenuItem("Salat");
	RadioMenuItem suppen = new RadioMenuItem("Suppen");
	RadioMenuItem backUndSuess = new RadioMenuItem("Back-und Süßspeisen");
	RadioMenuItem getraenke = new RadioMenuItem("Getränke");
	RadioMenuItem fruehstueck = new RadioMenuItem("Frühstück");
	
	ToggleGroup suchkategorienGruppe = new ToggleGroup();
	
	
	// Constructor
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


		
		suchkategorienGruppe.getToggles().addAll(vorspeise, hauptspeise, dessert, beilage, salat, suppen, backUndSuess, getraenke, fruehstueck);
		
		suchoptionen.getItems().addAll(vorspeise, hauptspeise, dessert, beilage, salat, suppen, backUndSuess, getraenke, fruehstueck);

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
		VBox.setVgrow(scrollpane, Priority.ALWAYS);
	}

	private void suchen(){
		try{
			
			if(!suchergebnisse.getChildren().isEmpty()){
				suchergebnisse.getChildren().clear();
			}
			
			
			// hole aktivierte Suchkritieren aus auswahl[] BEGINN
			String ausgewaehlteSuchkriterien = "";

			for(int i = 0; i < suchkategorienGruppe.getToggles().size(); i++){
				RadioMenuItem chkbox = (RadioMenuItem) suchkategorienGruppe.getToggles().get(i);
				if(chkbox.isSelected()){
					ausgewaehlteSuchkriterien += (chkbox.getText() + ", ");
				}
			}
			
			// hole aktivierte Suchkritieren aus auswahl[] ENDE
			String sucheingabe = suchfeld.getText();
			Methoden suchenKategorie = new Methoden();

			Suchobjekt[] gefundeneRezepte = suchenKategorie.kategorie(meth.tildeHinzufuegen(sucheingabe, oderSuche.isSelected()), ausgewaehlteSuchkriterien);
			suchtreffer = gefundeneRezepte.length;
			ergebnisanzahl.setText(String.format(
				"Ihre Suche ergab %1$d Treffer", suchtreffer));


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
			suchergebnisse.setPrefWidth(778);
		}catch(Exception e){
			e.printStackTrace();
		};
	}
	

}