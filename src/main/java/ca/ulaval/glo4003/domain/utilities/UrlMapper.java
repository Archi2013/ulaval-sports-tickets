package ca.ulaval.glo4003.domain.utilities;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class UrlMapper {

	protected String createUrl(String source) {
		String nfdNormalizedString = Normalizer.normalize(source, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    String result = pattern.matcher(nfdNormalizedString).replaceAll("");
	    result = result.toLowerCase(Locale.ENGLISH);
	    result = result.replace("œ", "oe");
	    result = result.replaceAll("[^a-zA-Z0-9-]", "-").replaceAll("-{2,}", "-").replaceAll("-$", "").replaceAll("^-", "");
	    return result;
	}
}
