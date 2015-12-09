// Klasse um eigene Rezepte einzufügen

package einlesen;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import main.Rezeptesammlung;

public class Rezepte 
{
	//test
	private String quelle;
	private String titel;
	private String tag;
	private String monat;
	private String jahr;
	private String beschreibung;
	private String  bild;
	private String arbeitszeit;
	private String kochbackzeit;
	private String schwierigkeit;
	private String kalorienpp;
	private String tags;
	
	public void neuesRezepteinfügen(String etitel, String ebeschreibung, String ebild, String earbeitszeit, String ekochbackzeit, String eschwierigkeit, String ekalorienpp, String etags ) throws IOException
	{
		DatumAusgabe da = new DatumAusgabe();
		String[] datum = da.getDatum();
		
		quelle = "eigene";
		titel = etitel;
		beschreibung = ebeschreibung;
		bild = ebild;
		arbeitszeit = earbeitszeit;
		kochbackzeit = ekochbackzeit;
		schwierigkeit = eschwierigkeit;
		kalorienpp = ekalorienpp;
		tag = datum[0];
		monat = datum[1];
		jahr = datum[2];
		tags = etags;
		
		Document dokument = new Document();
		
		TextField dokQuelle= new TextField("Quelle", quelle, Field.Store.YES);
		TextField dokTitel = new TextField("Titel", titel, Field.Store.YES);
		TextField dokInhalt = new TextField("Inhalt", "n.A", Field.Store.YES);
		TextField dokLink = new TextField("Link", "n.A.", Field.Store.YES);
		TextField dokTag = new TextField("Tag", tag, Field.Store.YES);
		TextField dokMonat = new TextField("Monat", monat, Field.Store.YES);
		TextField dokJahr = new TextField("Jahr", jahr, Field.Store.YES);
		TextField dokBeschreibung = new TextField("Beschreibung", beschreibung, Field.Store.YES);
		TextField dokBild = new TextField("Bild", bild, Field.Store.YES);
		TextField dokArbeitszeit = new TextField("Arbeitszeit", arbeitszeit, Field.Store.YES);
		TextField dokKochbackzeit = new TextField("KochBackzeit", kochbackzeit, Field.Store.YES);
		TextField dokSchwierigkeit = new TextField("Schwierigkeit", schwierigkeit, Field.Store.YES);
		TextField dokKalorienPp = new TextField("KalorienPP", kalorienpp, Field.Store.YES);
		TextField dokTags = new TextField("Tags", tags, Field.Store.YES);
		
		
//Es wird beim suchen der meiste Wert auf den Titel und den Inhalt gelegt
		dokTitel.setBoost(2.0f);
		dokInhalt.setBoost(1.3f);
		
//Hinzufügen der 
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
//		System.out.println(dokument.get("Tag"));
//		System.out.println(dokument.get("Monat"));
//		System.out.println(dokument.get("Jahr"));
		
//Das fertige Dokument wird zum Index hinzugefügt
		Rezeptesammlung.writer.addDocument(dokument);
		Rezeptesammlung.writer.commit();
		
	}
}
