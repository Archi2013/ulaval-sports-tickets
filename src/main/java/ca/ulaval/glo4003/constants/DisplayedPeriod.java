package ca.ulaval.glo4003.constants;

import java.util.ArrayList;
import java.util.List;

public enum DisplayedPeriod {
	ONE_DAY, ONE_WEEK, ONE_MONTH, THREE_MONTH, SIX_MONTH, ALL;

	public String toString() {
		String name = "";
		switch (ordinal()) {
		case 0:
			name = "un jour";
			break;
		case 1:
			name = "une semaine";
			break;
		case 2:
			name = "un mois";
			break;
		case 3:
			name = "trois mois";
			break;
		case 4:
			name = "six mois";
			break;
		case 5:
			name = "tout";
			break;
		default:
			name = "Erreur";
			break;
		}
		return name;
	}

	public static List<DisplayedPeriod> getDisplayedPeriods() {
		List<DisplayedPeriod> list = new ArrayList<>();
		for (DisplayedPeriod period : DisplayedPeriod.values()) {
			list.add(period);
		}
		return list;
	}
}