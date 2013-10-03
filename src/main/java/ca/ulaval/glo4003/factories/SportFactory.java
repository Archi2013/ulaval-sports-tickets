package ca.ulaval.glo4003.factories;

import java.util.ArrayList;

import ca.ulaval.glo4003.pojos.Game;
import ca.ulaval.glo4003.pojos.Sport;

public class SportFactory {

	public Sport instantiateSport(String sportName) {

		return new Sport(sportName, new ArrayList<Game>());
	}

}
