package ca.ulaval.glo4003.domain.pojos.persistable;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;

public class PersistableSport implements Sport, Persistable<SportDto> {

	private List<Game> gameCalendar;
	private String sportName;

	public PersistableSport(String sportName, List<Game> gameCalendar) {
		this.sportName = sportName;
		this.gameCalendar = gameCalendar;
	}

	public void addGameToCalendar(Game gameToAdd) {
		if (gameToAdd.acceptsToBeScheduled()) {
			gameToAdd.beScheduledToThisSport(sportName);
			gameCalendar.add(gameToAdd);
		}
	}

	@Override
	public SportDto saveDataInDTO() {
		return new SportDto(sportName);
	}
}
