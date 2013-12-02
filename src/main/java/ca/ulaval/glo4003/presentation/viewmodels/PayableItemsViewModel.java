package ca.ulaval.glo4003.presentation.viewmodels;

import java.util.List;


public class PayableItemsViewModel {
	
	public String cumulativePrice;
	public List<SectionForPaymentViewModel> sections;
	
	public List<SectionForPaymentViewModel> getSections() {
		return sections;
	}

	public void setSections(List<SectionForPaymentViewModel> sections) {
		this.sections = sections;
	}

	public String getCumulativePrice() {
		return cumulativePrice;
	}

	public void setCumulativePrice(String cumulativePrice) {
		this.cumulativePrice = cumulativePrice;
	}
}
