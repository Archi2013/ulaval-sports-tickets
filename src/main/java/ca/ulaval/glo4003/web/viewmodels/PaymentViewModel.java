package ca.ulaval.glo4003.web.viewmodels;


public class PaymentViewModel {
	
	public String cumulativePrice;
	public SectionForPaymentViewModel sectionForPaymentViewModel;
	
	public String getCumulativePrice() {
		return cumulativePrice;
	}

	public void setCumulativePrice(String cumulativePrice) {
		this.cumulativePrice = cumulativePrice;
	}

	public SectionForPaymentViewModel getSectionForPaymentViewModel() {
		return sectionForPaymentViewModel;
	}
	
	public void setSectionForPaymentViewModel(SectionForPaymentViewModel sectionForPaymentViewModel) {
		this.sectionForPaymentViewModel = sectionForPaymentViewModel;
	}
}
