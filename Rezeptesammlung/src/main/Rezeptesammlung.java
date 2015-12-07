package main;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;

import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.xml.sax.SAXException;

import einlesen.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oberflaeche.Gui;
import suche.Suche;



public class Rezeptesammlung extends Application{

	public static NIOFSDirectory indexDir;
	public static Analyzer analyzer;
	public static IndexWriter writer;

	public static void main(String[] args) throws IOException, ParseException, BadLocationException,
			org.apache.lucene.queryparser.classic.ParseException, ParserConfigurationException, SAXException {

		launch();

		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		indexDir = new NIOFSDirectory(new File("prodIndexDir"));
		analyzer = new GermanAnalyzer(Version.LUCENE_45);
		writer = new IndexWriter(indexDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
		
		einlesen.Methoden meth = new einlesen.Methoden();
//		Suche such = new Suche();
		
		File file = new File("C:/SWP/Archive/rssfiles");
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
//		Methoden.deleteDir(file);
		writer.close();	
//		String versuchZumTilde = "flachs tomaen";
//		such.suchen(meth.tildeHinzufuegen(versuchZumTilde));
		
		
		
		// gui
		Scene scene = new Scene(new Gui(meth));

		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Omnomnom");
		primaryStage.show();
		
	}

}
