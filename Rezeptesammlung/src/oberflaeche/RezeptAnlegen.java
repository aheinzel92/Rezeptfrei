package oberflaeche;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RezeptAnlegen extends VBox {

	public RezeptAnlegen(){
		TextField titel = new TextField();
			titel.setPromptText("Titel");
		TextArea beschreibung = new TextArea();
			beschreibung.setPromptText("Beschreibung");
		TextField tags = new TextField();
			tags.setPromptText("Tags durch Komma getrennt einegben");
			
		Button speichern = new Button("speichern");
		Button abbrechen = new Button("abbrechen");
		
		HBox buttonGruppe = new HBox();
			buttonGruppe.getChildren().addAll(speichern, abbrechen);

		this.getChildren().addAll(titel, beschreibung, tags, buttonGruppe);
		
	}

}
