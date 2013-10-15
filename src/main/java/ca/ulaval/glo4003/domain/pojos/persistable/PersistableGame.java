package ca.ulaval.glo4003.domain.pojos.persistable;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.GameDto;

public class PersistableGame implements IPersistable<GameDto> {
	public static final String DEFAULT_SPORT = "nosport";
	public static final long DEFAULT_ID = -1;
	private long id;
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
		this.sportName = DEFAULT_SPORT;
	}

	@Override
	public GameDto saveDataInDTO() {
		return new GameDto(id, opponents, gameDate, sportName);
	}

}
