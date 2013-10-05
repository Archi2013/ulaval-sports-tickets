package ca.ulaval.glo4003.web.viewmodel;

import java.util.List;

public class SectionsViewModel {
	public String opponents;
	public String date;
	public List<SectionViewModel> sections;

	public SectionsViewModel(String opponents, String date, List<SectionViewModel> sections) {
		this.opponents = opponents;
		this.date = date;
		this.sections = sections;
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
