package ca.ulaval.glo4003.domain.pojos;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.GameDto;

public class Game implements IPersistable<GameDto> {

	public Game(String opponents, DateTime gameDate) {
	}

	@Override
	public GameDto saveDataInDTO() {
		return null;

	}

}
