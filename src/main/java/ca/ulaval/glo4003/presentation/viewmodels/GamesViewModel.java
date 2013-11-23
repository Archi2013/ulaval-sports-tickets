package ca.ulaval.glo4003.presentation.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class GamesViewModel {

	private String sportName;
	private List<GameViewModel> games;

	public GamesViewModel() {
		this.sportName = "";
		this.games = new ArrayList<GameViewModel>();
	}

	public GamesViewModel(String sportName) {
		this.sportName = sportName;
		this.games = new ArrayList<GameViewModel>();
	}

	public List<GameViewModel> getGames() {
		return games;
	}

	public boolean hasGames() {
		return !games.isEmpty();
	}

	public String getSportName() {
		return sportName;
	}
}
