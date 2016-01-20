package oberflaeche;

import java.io.IOException;

import einlesen.Rezepte;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RezeptAnlegen extends VBox {

	public RezeptAnlegen() {
		TextField titel = new TextField();
			titel.setPromptText("Titel");
		TextArea beschreibung = new TextArea();
			beschreibung.setPromptText("Beschreibung");
		TextField tags = new TextField();
			tags.setPromptText("Tags durch Komma getrennt einegben");
		Label arbeitszeitLabel = new Label("Arbeitszeit:");
		Label kochUndBackzeitLabel = new Label("Koch-/Backzeit:");
		Label schwierigkeitLabel = new Label("Schwierigkeit:");
		Label kalorienLabel = new Label("Kalorien:");
		TextField arbeitszeit = new TextField("Keine Angabe");
		TextField kochBackZeit = new TextField("Keine Angabe");
		ComboBox schwierigkeit = new ComboBox();
			schwierigkeit.getItems().addAll("einfach", "normal", "schwierig");
			schwierigkeit.setValue("normal");
			
		TextField kalorien = new TextField("Keine Angabe");
		
		Button speichern = new Button("Speichern");
			speichern.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {

					Rezepte neuesRezept = new Rezepte();
					try {
						neuesRezept.neuesRezeptEinfügen(titel.getText(),beschreibung.getText(),arbeitszeit.getText(),kochBackZeit.getText(),schwierigkeit.getValue().toString(),kalorien.getText(),tags.getText());
					} catch (IOException e) {
						System.out.println("Fehler beim Einlesen!\n" + e.toString());
					}
					event.consume();
				}
			});
		
		Button abbrechen = new Button("Abbrechen");

		HBox buttonGruppe = new HBox();
			buttonGruppe.getChildren().addAll(arbeitszeitLabel, arbeitszeit, kochUndBackzeitLabel, kochBackZeit, schwierigkeitLabel, schwierigkeit, kalorienLabel, kalorien, speichern, abbrechen);
			buttonGruppe.setSpacing(5);
			buttonGruppe.setPadding(new Insets(5, 5, 5, 5));
			buttonGruppe.alignmentProperty().set(Pos.CENTER);
		this.getChildren().addAll(titel, beschreibung, tags, buttonGruppe);

	}

}
