package einlesen;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

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
//					System.out.print(" (Ordner)\n");
					result.addAll(listDir(files[i]));
				}
//				else{
//					System.out.print(" (Datei)\n");
//				}
				if(files[i].getAbsolutePath().contains("xml")){
					System.out.println(files[i].getAbsolutePath());
					result.add(files[i]);
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
	
	// Methode um für jeden Begriff im String eine Tilde anzuhängen
		public String tildeHinzufuegen(String suchbegriff){
			String suchbegriff2 = suchbegriff.replaceAll(" ", "~ ");
			return suchbegriff2;
		}

}
