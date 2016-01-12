
package einlesen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.lucene.queryparser.classic.ParseException;

import objekte.Suchobjekt;
import suche.Suche;

//egal


public class Methoden {
	
	// public File[] files;

//	
	//Methode zum Auflisten der XML-Dateien 
	public LinkedList<File> listDir(File dir){
		LinkedList<File> result = new LinkedList<File>();
		File[] files = dir.listFiles();
		if(files != null){
			for(int i = 0; i< files.length; i++){
//				System.out.printf(files[i].getAbsolutePath());
				if(files[i].isDirectory()){
					if(files[i].getAbsolutePath().contains("timeless")){
						
					}
					else{
//					System.out.print(" (Ordner)\n");
					result.addAll(listDir(files[i]));
					}
				}
//				else{
//					System.out.print(" (Datei)\n");
//				}
				if(files[i].getAbsolutePath().contains("xml")){
					if(files[i].getAbsolutePath().contains("timeless")){
						
					}
					else{
					System.out.println(files[i].getAbsolutePath());
					result.add(files[i]);
					}
				}
//				else{
//					files[i].delete();
//				}
			}
		}
		return result;
	}
	
	
	// Methode zum Löschen der Liste mit den XML-Dateien
	public static void deleteDir(File dir){
		File[] files = dir.listFiles();
		if(files != null){
			for(int i = 0; i<files.length; i++){
				if(files[i].isDirectory()){
					deleteDir(files[i]);
				}
				else{
					files[i].delete();
				}
			}
			dir.delete();
		}
	}
	
	// Methode um für jeden Begriff im String eine Tilde anzuhängen und die Auswahl für eine or bzw and Suche
		public String tildeHinzufuegen(String suchbegriff, boolean istOderSuche){
//			boolean hakenGesetzt = false;
			String suchbegriff2;
			if(istOderSuche == true){
				// Oder-Suche-Einstellungen
				suchbegriff2 = suchbegriff.replaceAll(" ", "~ "); //+"~"
				suchbegriff2 = suchbegriff2 + "~";
				return suchbegriff2;
			}
			else{
				// Und-Suche-Einstellungen
				suchbegriff = "+" + suchbegriff;
				suchbegriff2 = suchbegriff.replaceAll(" ", "~ +");
				suchbegriff2 = suchbegriff2 + "~";
				return suchbegriff2;
			}	
		}
		
		// Methode zur Kategorisierung
		public Suchobjekt[] kategorie(String suchbegriff, String ausgewählteKategorien) throws IOException, ParseException{
			Suche such = new Suche();
			Suchobjekt[] ergebnis;
			System.out.println(ausgewählteKategorien.length());
			if(ausgewählteKategorien.length() == 0){
				return such.suchen(suchbegriff);
			}
			else{
				return such.suchenNachKategorien(suchbegriff, ausgewählteKategorien);	
			}

		}
		
		// Methode um aus dem Stringarray mit den ganzen Tags einen einzelnen String zu machen
		public static String arrayToString(String[] tagsArray){
			String tagsString = "";
			for(int i = 0; i<tagsArray.length; i++){
				tagsString = tagsString + tagsArray[i];
				if(i < (tagsArray.length-1)){
					tagsString += ", ";
				}
			}
			return tagsString;
		}
		
}
