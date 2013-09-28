package ca.ulaval.glo4003.web.viewmodel;

import java.util.List;

public class SportViewModel {
	private String name;
	private List<GameSimpleViewModel> games;
	
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
		return games;
	}
	
	public void setGames(List<GameSimpleViewModel> games) {
		this.games = games;
	}
}
