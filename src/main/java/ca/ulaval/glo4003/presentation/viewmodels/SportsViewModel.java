package ca.ulaval.glo4003.presentation.viewmodels;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class SportsViewModel {

	private List<SportViewModel> sports;

	public SportsViewModel() {
		this.sports = newArrayList();
	}

	public List<SportViewModel> getSports() {
		return sports;
	}

}
