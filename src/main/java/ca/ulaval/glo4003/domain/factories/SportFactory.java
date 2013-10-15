package ca.ulaval.glo4003.domain.factories;

import java.util.List;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;

public class SportFactory {

	public PersistableSport instantiateSport(String sportName, List<PersistableGame> gameList) {

		return new PersistableSport(sportName, gameList);
	}

}
