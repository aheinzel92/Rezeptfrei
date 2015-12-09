package einlesen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatumAusgabe 
{
	
//	public static void main(String[] args)
//	{
//		DatumAusgabe da = new DatumAusgabe();
//		String[] datum = da.getDatum();
//		String tag = datum [0];
//		String monat = datum [1];
//		String jahr = datum [2];
//		
//		System.out.println(tag + " " + monat + " " + jahr);
//	}
	public String[] getDatum()
	{
		String[] datum = new String[3];
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Calendar kalender = df.getCalendar();
        kalender.setTimeInMillis(System.currentTimeMillis());
        
		String tag = Integer.toString(kalender.get(Calendar.DAY_OF_MONTH));
		String monat= Integer.toString(kalender.get(Calendar.MONTH) + 1);
		String jahr = Integer.toString(kalender.get(Calendar.YEAR));
        
		datum[0] = tag;
		datum[1] = monat;
		datum[2] = jahr;
		
		return datum;
	}
	
	

}
