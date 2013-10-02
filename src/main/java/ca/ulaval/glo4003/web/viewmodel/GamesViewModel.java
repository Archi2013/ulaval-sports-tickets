package ca.ulaval.glo4003.web.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class GamesViewModel {

	private String sportName;
	private List<GameSimpleViewModel> games;

	public GamesViewModel() {
		this.sportName = "";
		this.games = new ArrayList<GameSimpleViewModel>();
	}

	public GamesViewModel(String sportName) {
		this.sportName = sportName;
		this.games = new ArrayList<GameSimpleViewModel>();
	}

	public List<GameSimpleViewModel> getGames() {
		return games;
	}

	public boolean hasGames() {
		return !games.isEmpty();
	}

	public String getSportName() {
		return sportName;
	}
}
