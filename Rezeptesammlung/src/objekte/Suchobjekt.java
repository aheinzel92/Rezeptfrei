package objekte;

public class Suchobjekt 
{
	//tet
	String titel = "k.A.";
	String bild = "k.A.";
	String beschreibung = "k.A.";
	String tag = "k.A.";
	String monat = "k.A.";
	String jahr = "k.A.";
	String inhalt = "k.A.";
	String link = "k.A.";
	String quelle = "k.A.";

	
	public Suchobjekt(String quelle, String titel, String bild, String beschreibung, String tag, String monat, String jahr, String inhalt, String link)
	{
		this.quelle = quelle;
		this.titel = titel;
		this.bild = bild;
		this.beschreibung = beschreibung;
		this.tag = tag;
		this.monat = monat;
		this.jahr = jahr;
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

	public String getTag() {
		return tag;
	}

	public String getMonat() {
		return monat;
	}

	public String getJahr() {
		return jahr;
	}

	public String getQuelle() {
		return quelle;
	}

	public String getInhalt() {
		return inhalt;
	}

	public String getLink() {
		return link;
	}
}
