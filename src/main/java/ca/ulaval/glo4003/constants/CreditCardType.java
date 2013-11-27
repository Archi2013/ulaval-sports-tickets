package ca.ulaval.glo4003.constants;

import java.util.ArrayList;
import java.util.List;

public enum CreditCardType {
	MISTERCARD, VASI, AMERICANEXPRESSO;

	public String toString() {
		String name = "";
		switch (ordinal()) {
		case 0:
			name = "Mistercard";
			break;
		case 1:
			name = "Vasi";
			break;
		case 2:
			name = "AmericanExpresso";
			break;
		default:
			name = "Erreur";
			break;
		}
		return name;
	}

	public static List<CreditCardType> getCreditCardTypes() {
		List<CreditCardType> list = new ArrayList<>();
		for (CreditCardType type : CreditCardType.values()) {
			list.add(type);
		}
		return list;
	}
}