package Oberfläche;

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

	int suchtreffer;

	public void start(Stage primaryStage) {

		TextField searchfield = new TextField();
		Button engage = new Button("Suchen");
		Label label = new Label("Zutaten: ");

		MenuBar menubar = new MenuBar();
		Menu file = new Menu("Datei");
		MenuItem exit = new MenuItem("Beenden");
		exit.setMnemonicParsing(true);
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});

		file.getItems().add(exit);

		final Menu searchOptions = new Menu("Suchoptionen");

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
			searchOptions.getItems().add(cmi);
		}

		searchOptions.getItems().add(new MenuItem("Auswahlmenü schließen"));
		searchOptions.getItems().add(new MenuItem("Bezugsseite"));

		menubar.getMenus().addAll(file, searchOptions);

		ToolBar toptoolbar = new ToolBar();
		toptoolbar
				.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth());
		toptoolbar.getItems().addAll(menubar, new Separator(), label,
				searchfield, engage);
		toptoolbar.setStyle("-fx-background-color: bisque;");

		Label ergebnisanzahl = new Label(String.format(
				"Ihre Suche ergab %1$d Treffer", suchtreffer));

		ToolBar bottomtoolbar = new ToolBar();
		bottomtoolbar.getItems().add(ergebnisanzahl);
		bottomtoolbar.setMinWidth(Screen.getPrimary().getVisualBounds()
				.getWidth());
		bottomtoolbar.setStyle("-fx-background-color: bisque;");

		VBox suchergebnisse = new VBox();

		// SuchergebnisElement test = new SuchergebnisElement(
		// "http://static.chefkoch-cdn.de/ck.de/cms-uploads/chefkoch/2244/GaensekeulePrint598.jpg",
		// "Arbeitszeit: ca. 45 Min. / Koch-/Backzeit: ca. 1 Std. 45 Min. / Schwierigkeitsgrad: normal / Kalorien p. P.: ca. 656 kcal ");
		//
		// SuchergebnisElement test2 = new SuchergebnisElement(
		// "",
		// "test2");
		// SuchergebnisElement test3 = new SuchergebnisElement(
		// "http://static.chefkoch-cdn.de/ck.de/cms-uploads/chefkoch/2244/GaensekeulePrint598.jpg",
		// "test3");
		//
		// SuchergebnisElement test4 = new SuchergebnisElement(
		// "http://static.chefkoch-cdn.de/ck.de/cms-uploads/chefkoch/2244/GaensekeulePrint598.jpg",
		// "test4");
		// SuchergebnisElement test5 = new SuchergebnisElement(
		// "http://static.chefkoch-cdn.de/ck.de/cms-uploads/chefkoch/2244/GaensekeulePrint598.jpg",
		// "test5");
		//
		// SuchergebnisElement test6 = new SuchergebnisElement(
		// "http://static.chefkoch-cdn.de/ck.de/cms-uploads/chefkoch/2244/GaensekeulePrint598.jpg",
		// "test6");
		// SuchergebnisElement test7 = new SuchergebnisElement(
		// "http://static.chefkoch-cdn.de/ck.de/cms-uploads/chefkoch/2244/GaensekeulePrint598.jpg",
		// "test7");
		//
		// SuchergebnisElement test8 = new SuchergebnisElement(
		// "http://static.chefkoch-cdn.de/ck.de/cms-uploads/chefkoch/2244/GaensekeulePrint598.jpg",
		// "test8");
		//
		// suchergebnisse.getChildren().addAll(test,test2,test3,test4,test5,test6,test7,test8);

		/*
		 * try{ suchtreffer = rezepteFoundList.length; for(int i = 0; i <
		 * rezepteFoundList.length; i++){
		 * 
		 * tempRezept = rezeptearray[i];
		 * suchergebnisse.getChildren().add(tempRezept)
		 * 
		 * } catch (Exception e){
		 * 
		 * }
		 */

		ScrollPane scrollpane = new ScrollPane();
		scrollpane.setContent(suchergebnisse);

		VBox mainlayout = new VBox();
		mainlayout.getChildren().addAll(toptoolbar, scrollpane, bottomtoolbar);

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