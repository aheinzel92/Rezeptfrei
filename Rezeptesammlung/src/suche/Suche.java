//
package suche;


import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import main.Rezeptesammlung;
import objekte.Suchobjekt;

public class Suche {
	public Suchobjekt[] suchen(String suchbegriff) throws IOException,
			ParseException {
		String[] felder = {"Quelle", "Titel", "Inhalt", "Link", "Tag", "Monat", "Jahr", "Beschreibung", "Bild", "Arbeitszeit", "KochBackzeit", "Schwierigkeit", "KalorienPP", "Tags"};
		DirectoryReader dr = DirectoryReader.open(Rezeptesammlung.indexDir);
		IndexSearcher searcher = new IndexSearcher(dr);
		MultiFieldQueryParser qp = new MultiFieldQueryParser(Version.LUCENE_45, felder, Rezeptesammlung.analyzer);
		Query query = qp.parse(suchbegriff);
		TopDocs td = searcher.search(query, 10, Sort.INDEXORDER);
		ScoreDoc[] sd = td.scoreDocs;
		Suchobjekt[] ergebnisObjekt = new Suchobjekt[sd.length];
		
		for (int i = 0; i < sd.length; i++) {
			Document doc = searcher.doc(sd[i].doc);
			
			ergebnisObjekt[i] = new Suchobjekt(doc.get("Quelle"),
											doc.get("Titel"), 
											doc.get("Bild"), 
											doc.get("Beschreibung"),
											doc.get("Tag"), 
											doc.get("Monat"), 
											doc.get("Jahr"),
											doc.get("Inhalt"), 
											doc.get("Link"),
											doc.get("Arbeitszeit"),
											doc.get("KochBackzeit"),
											doc.get("Schwierigkeit"),
											doc.get("KalorienPP"),
											doc.get("Tags"));

		}
		dr.close();
		return ergebnisObjekt;
	}
	
	public Suchobjekt[] suchenNachKategorien(String suchbegriff, String ausgewählteKategorien) throws IOException,
			ParseException {
		String[] felder = {"Quelle", "Titel", "Inhalt", "Link", "Tag", "Monat", "Jahr", "Beschreibung", "Bild", "Arbeitszeit", "KochBackzeit", "Schwierigkeit", "KalorienPP", "Tags"};
		DirectoryReader dr = DirectoryReader.open(Rezeptesammlung.indexDir);
		IndexSearcher searcher = new IndexSearcher(dr);
		MultiFieldQueryParser qp = new MultiFieldQueryParser(Version.LUCENE_45, felder, Rezeptesammlung.analyzer);
		Query query = qp.parse(suchbegriff);
		TopDocs td = searcher.search(query, 10, Sort.INDEXORDER);
		ScoreDoc[] sd = td.scoreDocs;
		
		ArrayList<Suchobjekt> tempArrayList = new ArrayList<Suchobjekt>();
		
		for (int i = 0; i < sd.length; i++) {
			Document doc = searcher.doc(sd[i].doc);

			if (doc.get("Tags").toLowerCase().contains(ausgewählteKategorien.toLowerCase())) {
				System.out.println("ok");
				tempArrayList.add(new Suchobjekt(doc.get("Quelle"), 
													doc.get("Titel"), 
													doc.get("Bild"),
													doc.get("Beschreibung"), 
													doc.get("Tag"), 
													doc.get("Monat"), 
													doc.get("Jahr"), 
													doc.get("Inhalt"),
													doc.get("Link"), 
													doc.get("Arbeitszeit"), 
													doc.get("KochBackzeit"), 
													doc.get("Schwierigkeit"),
													doc.get("KalorienPP"), 
													doc.get("Tags")));
				
			}
		}
		dr.close();
		
		// umspulen der Arraylist in ein Array
		Suchobjekt[] ergebnisObjekt = new Suchobjekt[tempArrayList.size()];
		for(int i = 0; i < tempArrayList.size();i++){
			ergebnisObjekt[i] = tempArrayList.get(i);
		}
		
		return ergebnisObjekt;
	}
}
