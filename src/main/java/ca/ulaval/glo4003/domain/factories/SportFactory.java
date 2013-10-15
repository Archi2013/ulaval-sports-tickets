package ca.ulaval.glo4003.domain.factories;

import java.util.List;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;

public class SportFactory {

	public Sport instantiateSport(String sportName, List<Game> gameList) {

		return new Sport(sportName, gameList);
	}

}
