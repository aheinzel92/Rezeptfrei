// Klasse um eigene Rezepte einzufügen

package einlesen;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import main.Rezeptesammlung;

public class Rezepte {
	private String quelle = "eigene";
	private String bild = "oberflaeche/grafik/null.jpg";
	private String tag;
	private String monat;
	private String jahr;


	public void neuesRezeptEinfügen(String etitel, String ebeschreibung, String earbeitszeit,
			String ekochbackzeit, String eschwierigkeit, String ekalorienpp, String etags) throws IOException {
		DatumAusgabe da = new DatumAusgabe();
		String[] datum = da.getDatum();

		tag = datum[0];
		monat = datum[1];
		jahr = datum[2];

		Document dokument = new Document();

		TextField dokQuelle = new TextField("Quelle", quelle, Field.Store.YES);
		TextField dokTitel = new TextField("Titel", etitel, Field.Store.YES);
		TextField dokInhalt = new TextField("Inhalt", "Keine Angabe", Field.Store.YES);
		TextField dokLink = new TextField("Link", "Keine Angabe.", Field.Store.YES);
		TextField dokTag = new TextField("Tag", tag, Field.Store.YES);
		TextField dokMonat = new TextField("Monat", monat, Field.Store.YES);
		TextField dokJahr = new TextField("Jahr", jahr, Field.Store.YES);
		TextField dokBeschreibung = new TextField("Beschreibung", ebeschreibung, Field.Store.YES);
		TextField dokBild = new TextField("Bild", bild, Field.Store.YES);
		TextField dokArbeitszeit = new TextField("Arbeitszeit", earbeitszeit, Field.Store.YES);
		TextField dokKochbackzeit = new TextField("KochBackzeit", ekochbackzeit, Field.Store.YES);
		TextField dokSchwierigkeit = new TextField("Schwierigkeit", eschwierigkeit, Field.Store.YES);
		TextField dokKalorienPp = new TextField("KalorienPP", ekalorienpp, Field.Store.YES);
		TextField dokTags = new TextField("Tags", etags, Field.Store.YES);

		// Es wird beim suchen der meiste Wert auf den Titel und den Inhalt
		// gelegt
		dokTitel.setBoost(2.0f);
		dokInhalt.setBoost(1.3f);

		// Hinzufügen der
		dokument.add(dokQuelle);
		dokument.add(dokTitel);
		dokument.add(dokInhalt);
		dokument.add(dokLink);
		dokument.add(dokTag);
		dokument.add(dokMonat);
		dokument.add(dokJahr);
		dokument.add(dokBeschreibung);
		dokument.add(dokBild);
		dokument.add(dokArbeitszeit);
		dokument.add(dokKochbackzeit);
		dokument.add(dokSchwierigkeit);
		dokument.add(dokKalorienPp);
		dokument.add(dokTags);

		// Das fertige Dokument wird zum Index hinzugefügt
		Rezeptesammlung.writer.addDocument(dokument);
		Rezeptesammlung.writer.commit();

	}
}
