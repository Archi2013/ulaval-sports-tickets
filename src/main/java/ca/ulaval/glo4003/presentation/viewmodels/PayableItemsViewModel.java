package ca.ulaval.glo4003.presentation.viewmodels;

import java.util.List;


public class PayableItemsViewModel {
	
	public String cumulativePrice;
	public List<SectionForCartViewModel> sections;
	
	public List<SectionForCartViewModel> getSections() {
		return sections;
	}

	public void setSections(List<SectionForCartViewModel> sections) {
		this.sections = sections;
	}

	public String getCumulativePrice() {
		return cumulativePrice;
	}

	public void setCumulativePrice(String cumulativePrice) {
		this.cumulativePrice = cumulativePrice;
	}
}
