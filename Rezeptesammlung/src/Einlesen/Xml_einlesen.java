package Einlesen;


/**
 * @author Stephan Maltan
 * 
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

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
	
	public static Analyzer analyzer;
	public static NIOFSDirectory indexDir;
	
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
		org.w3c.dom.Document docu = null;
		docu = db.parse(new File(pfad));

		Element text = (Element) docu.getElementsByTagName("ExtractedText").item(0);
		Element items = (Element) docu.getElementsByTagName("item").item(0);

		String title = items.getElementsByTagName("title").item(0).getTextContent();
		String description = items.getElementsByTagName("description").item(0).getTextContent();
		String link = items.getElementsByTagName("link").item(0).getTextContent();
		String pubDate = items.getElementsByTagName("pubDate").item(0).getTextContent();
		String extractedText = text.getTextContent();

		/*
		 * Einfügen der Daten in ein Lucene-Dokument
		 * Hinzufügen des Dokuments zum Index
		 */

		Document document = new Document();
		document.add(new TextField("title", title, Field.Store.YES));
		document.add(new TextField("content", extractedText, Field.Store.YES));
		document.add(new TextField("link", link, Field.Store.YES));
		document.add(new TextField("pubDate", pubDate, Field.Store.YES));
		document.add(new TextField("description", description, Field.Store.YES));

//		indexDir = new NIOFSDirectory(new File("testIndexDir"));
//		analyzer = new StandardAnalyzer(Version.LUCENE_45);
//		IndexWriter writer = new IndexWriter(indexDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
		Rezeptesammlung.writer.addDocument(document);
		Rezeptesammlung.writer.commit();
	}
}
