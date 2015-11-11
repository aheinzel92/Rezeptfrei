package main;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.xml.sax.SAXException;

import Einlesen.*;

public class Rezeptesammlung {

	public static NIOFSDirectory indexDir;
	public static Analyzer analyzer;
	public static IndexWriter writer;

	public static void main(String[] args) throws IOException, ParseException,
			BadLocationException,
			org.apache.lucene.queryparser.classic.ParseException,
			ParserConfigurationException, SAXException {

		indexDir = new NIOFSDirectory(new File("testIndexDir"));
		analyzer = new StandardAnalyzer(Version.LUCENE_45);
		writer = new IndexWriter(indexDir, new IndexWriterConfig(
				Version.LUCENE_45, analyzer));
		Einlesen.Methoden meth = new Einlesen.Methoden();

		File file = new File(
				"H:\\Dokumente\\PraktikumProgrammieren\\Archive\\rssfiles");
		LinkedList<File> dirList = meth.listDir(file);

		meth.listDir(file);
		System.out.println(dirList.size());
		for (int i = 0; i < dirList.size(); i++) {
			try {
				Xml_einlesen.Einlesen(dirList.get(i).getAbsolutePath());
				System.out.println("Datei eingelesen! " + i);
			} catch (NullPointerException e) {

			}
		}

		// Methoden.deleteDir(file);
		writer.close();
		DirectoryReader dr = DirectoryReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(dr);
		QueryParser qp = new QueryParser(Version.LUCENE_45, "content", analyzer);
		String search = "und";
		Query query = qp.parse(search);

		TopDocs td = searcher.search(query, 10);
		ScoreDoc[] sd = td.scoreDocs;
		for (int i = 0; i < sd.length; i++) {
			Document doc = searcher.doc(sd[i].doc);
			System.out.println(doc.get("description"));
		}
		dr.close();

	}

}
