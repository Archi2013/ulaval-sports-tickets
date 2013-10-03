package ca.ulaval.glo4003.factories;

import ca.ulaval.glo4003.pojos.Sport;

public class SportFactory {

	public Sport instantiateSport(String sportName) {

		return new Sport(sportName);
	}

}
