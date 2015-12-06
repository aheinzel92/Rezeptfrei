
package suche;


import java.io.IOException;

import objekte.Suchobjekt;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import main.Rezeptesammlung;

public class Suche {
	public Suchobjekt[] suchen(String suchbegriff) throws IOException,
			ParseException {
		String[] felder = {"Quelle", "Titel", "Inhalt", "Link", "Tag", "Monat", "Jahr", "Beschreibung", "Bild"};
		DirectoryReader dr = DirectoryReader.open(Rezeptesammlung.indexDir);
		IndexSearcher searcher = new IndexSearcher(dr);
		MultiFieldQueryParser qp = new MultiFieldQueryParser(Version.LUCENE_45, felder, Rezeptesammlung.analyzer);
		Query query = qp.parse(suchbegriff);
		TopDocs td = searcher.search(query, 10, Sort.INDEXORDER);
		ScoreDoc[] sd = td.scoreDocs;
		Suchobjekt[] ergebnisObjekt = new Suchobjekt[sd.length];
		
		for (int i = 0; i < sd.length; i++) {
			Document doc = searcher.doc(sd[i].doc);
			System.out.println(doc.get("Beschreibung"));
			System.out.println(doc.get("Tag") + " " + doc.get("Monat") + " " + doc.get("Jahr"));
			
			ergebnisObjekt[i] = new Suchobjekt(doc.get("Quelle"),
											doc.get("Titel"), 
											doc.get("Bild"), 
											doc.get("Beschreibung"),
											doc.get("Tag"), 
											doc.get("Monat"), 
											doc.get("Jahr"),
											doc.get("Inhalt"), 
											doc.get("Link"));
		}
		dr.close();
		return ergebnisObjekt;
	}
}
