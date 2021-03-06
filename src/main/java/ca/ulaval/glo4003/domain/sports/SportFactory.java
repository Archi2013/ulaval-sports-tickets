package ca.ulaval.glo4003.domain.sports;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.Game;

@Component
public class SportFactory {

	public PersistableSport instantiateSport(String sportName, List<Game> gameList) {

		return new PersistableSport(sportName, gameList);
	}

}
