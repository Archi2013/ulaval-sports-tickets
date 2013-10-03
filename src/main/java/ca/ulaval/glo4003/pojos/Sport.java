package ca.ulaval.glo4003.pojos;

import java.util.List;

public class Sport {

	private List<Game> gameCalendar;

	public Sport(String sportName, List<Game> gameCalendar) {
		this.gameCalendar = gameCalendar;
	}

	public void addGameToCalendar(Game game) {
		gameCalendar.add(game);
	}
}
