package br.com.maricamed.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public final class Utils {
	
	public static String converteInstantToDate(Instant dateInstant) {
		
		Date dtInscricaoInstant = Date.from(dateInstant);
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		return formatter.format(dtInscricaoInstant);
	}

}
