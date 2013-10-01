package ca.ulaval.glo4003.web.viewmodel;

import java.util.List;

public class GameViewModel {
	public Long id;
	public String opponents;
	public String date;
	public List<SectionViewModel> sections;

	public GameViewModel(Long id, String opponents, String date, List<SectionViewModel> sections) {
		this.id = id;
		this.opponents = opponents;
		this.date = date;
		this.sections = sections;
	}

	public Long getId() {
		return id;
	}

	public String getOpponents() {
		return opponents;
	}

	public String getDate() {
		return date;
	}

	public List<SectionViewModel> getSections() {
		return sections;
	}

}
