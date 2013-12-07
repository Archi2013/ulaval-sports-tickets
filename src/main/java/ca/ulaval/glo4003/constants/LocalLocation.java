package ca.ulaval.glo4003.constants;

import java.util.HashSet;
import java.util.Set;

public class LocalLocation {

	public static Set<String> getSet() {
		Set<String> result = new HashSet<String>() {
			private static final long serialVersionUID = 1L;

		{
			add("Stade TELUS-UL");
			add("PEPS Terrain 6");
		}};
		return result;
	}

}
