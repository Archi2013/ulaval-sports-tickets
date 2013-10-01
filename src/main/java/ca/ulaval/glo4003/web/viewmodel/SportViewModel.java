package ca.ulaval.glo4003.web.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class SportViewModel {
	public String name;
	public List<GameSimpleViewModel> games;

	public SportViewModel(String name, List<GameSimpleViewModel> games) {
		this.name = name;
		this.games = games;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GameSimpleViewModel> getGames() {
		if (games == null) {
			games = new ArrayList<GameSimpleViewModel>();
		}
		return games;
	}
}
