package objekte;


/*
 * Objekt das für einfacheren Übergabe der Suchergebnisse an die GUI verwendet wird
 */
public class Suchobjekt 
{
	String titel = "k.A.";
	String bild = "k.A.";
	String beschreibung = "k.A.";
	String tag = "k.A.";
	String monat = "k.A.";
	String jahr = "k.A.";
	String inhalt = "k.A.";
	String link = "k.A.";
	String quelle = "k.A.";
	String arbeitszeit = "k.A.";
	String kochBackzeit = "k.A";
	String schwierigkeit = "k.A";
	String kalorienPP = "k.A.";
	String tags = "k.A";

	
	public Suchobjekt(String quelle, String titel, String bild, String beschreibung, String tag, String monat, String jahr, String inhalt, String link, String arbeitszeit, String kochbackzeit, String schwierigkeit, String kalorienPP, String tags)
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
		this.arbeitszeit = arbeitszeit;
		this.kochBackzeit = kochbackzeit;
		this.schwierigkeit=schwierigkeit;
		this.kalorienPP = kalorienPP;
		this.tags = tags;
	}

	public String toString() {
		return "Suchobjekt [titel=" + titel + ", bild=" + bild + ", beschreibung=" + beschreibung + ", Datum=" + tag
				+ "-" + monat + "-" + jahr + ", link=" + link + ", quelle=" + quelle
				+ ", arbeitszeit=" + arbeitszeit + ", kochBackzeit=" + kochBackzeit + ", schwierigkeit=" + schwierigkeit
				+ ", kalorienPP=" + kalorienPP + ", tags=" + tags + "]";
	}

	public String getArbeitszeit() {
		return arbeitszeit;
	}

	public String getKochBackzeit() {
		return kochBackzeit;
	}

	public String getSchwierigkeit() {
		return schwierigkeit;
	}

	public String getKalorienPP() {
		return kalorienPP;
	}

	public String getTags() {
		return tags;
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
