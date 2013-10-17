package ca.ulaval.glo4003.domain.factories;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;

@Component
public class SportFactory {

	public PersistableSport instantiateSport(String sportName, List<Game> gameList) {

		return new PersistableSport(sportName, gameList);
	}

}
