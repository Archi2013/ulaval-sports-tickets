package ca.ulaval.glo4003.web.viewmodel;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class SportsViewModel {

	private List<SportSimpleViewModel> sports;

	public SportsViewModel() {
		this.sports = newArrayList();
	}

	public List<SportSimpleViewModel> getSports() {
		return sports;
	}

}
