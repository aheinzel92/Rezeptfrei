package objekte;

// suchobjekt muss noch angepasst werden

public class Suchobjekt 
{
	String titel;
	String bild;
	String beschreibung;
	String datum;
	String inhalt;
	String link;
	
	public Suchobjekt(String titel, String bild, String beschreibung, String datum, String inhalt, String link)
	{
		this.titel = titel;
		this.bild = bild;
		this.beschreibung = beschreibung;
		this.datum = datum;
		this.inhalt = inhalt;
		this.link = link;
	}

	public String getTitel() {
		return titel;
	}

	public String getBild() {
		return bild;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public String getDatum() {
		return datum;
	}

	public String getInhalt() {
		return inhalt;
	}

	public String getLink() {
		return link;
	}
}
