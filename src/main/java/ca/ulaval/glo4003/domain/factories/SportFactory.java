package ca.ulaval.glo4003.domain.factories;

import java.util.ArrayList;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;

public class SportFactory {

	public Sport instantiateSport(String sportName) {

		return new Sport(sportName, new ArrayList<Game>());
	}

}
