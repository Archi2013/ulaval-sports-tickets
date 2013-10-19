package ca.ulaval.glo4003.domain.pojos.persistable;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.pojos.Game;

public class PersistableGame implements Game, Persistable<GameDto> {
	public static final String NO_SPORT_SET = "The sport has not yet been set in this new game";
	public static final Long DEFAULT_ID = null;
	private Long id;
	private String opponents;
	private DateTime gameDate;
	private String sportName;

	public PersistableGame(long id, String opponents, DateTime gameDate, String sportName) {
		this.id = id;
		this.opponents = opponents;
		this.gameDate = gameDate;
		this.sportName = sportName;
	}

	public PersistableGame(String opponents, DateTime gameDate) {
		this.id = DEFAULT_ID;
		this.opponents = opponents;
		this.gameDate = gameDate;
		this.sportName = NO_SPORT_SET;
	}

	@Override
	public GameDto saveDataInDTO() {
		return new GameDto(id, opponents, gameDate, sportName);
	}

	@Override
	public boolean acceptsToBeScheduled() {
		if (sportName == NO_SPORT_SET) {
			return true;
		}
		return false;

	}

	@Override
	public void beScheduledToThisSport(String newSportName) {
		if (acceptsToBeScheduled()) {
			sportName = newSportName;
		}

	}

}
