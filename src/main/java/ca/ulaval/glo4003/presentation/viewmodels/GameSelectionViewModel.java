package ca.ulaval.glo4003.presentation.viewmodels;

import ca.ulaval.glo4003.utilities.time.DisplayDate;

public class GameSelectionViewModel {

	private DisplayDate gameDate;

	private String opponents;

	public GameSelectionViewModel(String opponents, DisplayDate gameDate) {
		this.opponents = opponents;
		this.gameDate = gameDate;
	}

	public DisplayDate getGameDate() {
		return gameDate;
	}

	public void setGameDate(DisplayDate gameDate) {
		this.gameDate = gameDate;
	}

	public String getOpponents() {
		return opponents;
	}

	public void setOpponents(String opponents) {
		this.opponents = opponents;
	}

}
