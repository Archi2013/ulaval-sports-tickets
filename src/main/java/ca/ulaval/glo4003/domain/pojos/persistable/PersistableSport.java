package ca.ulaval.glo4003.domain.pojos.persistable;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.SportDto;

public class PersistableSport implements IPersistable<SportDto> {

	private List<PersistableGame> gameCalendar;
	private String sportName;

	public PersistableSport(String sportName, List<PersistableGame> gameCalendar) {
		this.sportName = sportName;
		this.gameCalendar = gameCalendar;
	}

	public void addGameToCalendar(PersistableGame game) {
		gameCalendar.add(game);
	}

	@Override
	public SportDto saveDataInDTO() {
		return new SportDto(sportName);
	}
}
