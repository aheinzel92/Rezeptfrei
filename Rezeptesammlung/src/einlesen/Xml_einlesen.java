package einlesen;

/**
 * @author Stephan Maltan
 * 
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.text.BadLocationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import main.Rezeptesammlung;

public class Xml_einlesen {

	public static void Einlesen(String pfad) throws IOException, ParseException, BadLocationException,
			org.apache.lucene.queryparser.classic.ParseException, ParserConfigurationException, SAXException

	{
		/*
		 * Auslesen der Relevanten Informationen aus dem XML File und speichern
		 * dieser in Strings
		 */
		DocumentBuilderFactory dbf;
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		dbf.setIgnoringComments(true);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setExpandEntityReferences(true);

		DocumentBuilder db;
		db = dbf.newDocumentBuilder();
		db.setErrorHandler(new DefaultHandler());
		org.w3c.dom.Document doku = null;
		doku = db.parse(new File(pfad));

		Element text = (Element) doku.getElementsByTagName("ExtractedText").item(0);
		Element items = (Element) doku.getElementsByTagName("item").item(0);
		Element image = (Element) doku.getElementsByTagName("image").item(0);
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = df.getCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
       

		String titel = items.getElementsByTagName("title").item(0).getTextContent();
		String beschreibung = items.getElementsByTagName("description").item(0).getTextContent();
		String link = items.getElementsByTagName("link").item(0).getTextContent();
		String pubDat = items.getElementsByTagName("pubDate").item(0).getTextContent();
		String bild = image.getElementsByTagName("url").item(0).getTextContent();
		String inhalt = text.getTextContent();
		String quelle;
		String tag;
		String monat;
		String jahr;
		if(link.contains("www.lecker.de"))
		{
			quelle = "lecker.de";
			tag = pubDat.substring(8, 10);
			monat = pubDat.substring(5,7);
			jahr = pubDat.substring(0,4);
		}
		else
		{
			quelle = "chefkoch.de";
			tag = pubDat.substring(5, 7);
			jahr = pubDat.substring(12,16);
			String monNam = pubDat.substring(8,11);
			System.out.println(monNam);
			switch(monNam.toLowerCase())
			{
			case "jan":
				monat = "1";
				break;
			case "feb":
				monat = "2";
				break;
			case "mar":
				monat = "3";
				break;
			case "apr":
				monat = "4";
				break;
			case "may":
				monat = "5";
				break;
			case "jun":
				monat = "6";
				break;
			case "jul":
				monat = "7";
				break;
			case "aug":
				monat = "8";
				break;
			case "sep":
				monat = "9";
				break;
			case "oct":
				monat = "10";
				break;
			case "nov":
				monat = "11";
				break;
			case "dec":
				monat = "12";
				break;
			default:
				monat="0";
				break;
			}
		}
		if(tag.equals("0") || monat.equals("0") || jahr.equals("0"))
		{
			System.out.println("TAG WAR 0!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			tag = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			monat= Integer.toString(c.get(Calendar.MONTH));
			jahr = Integer.toString(c.get(Calendar.YEAR));
			
		}
		System.out.println(tag + " " + monat + " " + jahr);
		System.out.println(quelle);

		/*
		 * Einfügen der Daten in ein Lucene-Dokument Hinzufügen des Dokuments
		 * zum Index
		 */

		Document dokument = new Document();
		dokument.add(new TextField("Quelle", quelle, Field.Store.YES));
		dokument.add(new TextField("Titel", titel, Field.Store.YES));
		dokument.add(new TextField("Inhalt", inhalt, Field.Store.YES));
		dokument.add(new TextField("Link", link, Field.Store.YES));
		dokument.add(new TextField("Tag", tag, Field.Store.YES));
		dokument.add(new TextField("Monat", monat, Field.Store.YES));
		dokument.add(new TextField("Jahr", jahr, Field.Store.YES));
		dokument.add(new TextField("Beschreibung", beschreibung, Field.Store.YES));
		dokument.add(new TextField("Bild", bild, Field.Store.YES));
		Rezeptesammlung.writer.addDocument(dokument);
		Rezeptesammlung.writer.commit();
	}
}
