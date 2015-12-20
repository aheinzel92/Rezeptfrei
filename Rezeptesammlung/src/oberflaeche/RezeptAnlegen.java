package oberflaeche;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		Label azeit = new Label("Arbeitszeit:");
		Label kbzeit = new Label("Koch-/Backzeit:");
		Label schwierig = new Label("Schwierigkeit:");
		Label kalor = new Label("Kalorien:");
		TextField arbeitszeit = new TextField("Keine Angabe");
		TextField kochBackZeit = new TextField("Keine Angabe");
		ComboBox schwierigkeit = new ComboBox();
		schwierigkeit.getItems().addAll("einfach", "normal", "schwierig");
		schwierigkeit.setValue("normal");
		TextField kalorien = new TextField("Keine Angabe");
		Button speichern = new Button("speichern");
		speichern.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println(titel.getText() + " " + beschreibung.getText() + " " + arbeitszeit.getText() + " "
						+ kochBackZeit.getText() + " " + schwierigkeit.getValue().toString() + " " + kalorien.getText()
						+ " " + tags.getText());
				// neuesRezeptEinfügen(titel.getText(),beschreibung.getText(),arbeitszeit.getText(),kochBackZeit.getText(),schwierigkeit.getValue(),kalorien.getText(),tags.getText());
				event.consume();
			}
		});
		Button abbrechen = new Button("abbrechen");

		HBox buttonGruppe = new HBox();
		buttonGruppe.getChildren().addAll(azeit, arbeitszeit, kbzeit, kochBackZeit, schwierig, schwierigkeit, kalor,
				kalorien, speichern, abbrechen);
		this.getChildren().addAll(titel, beschreibung, tags, buttonGruppe);

	}

}
