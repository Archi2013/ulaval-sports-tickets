package ca.ulaval.glo4003.web.viewmodels;


public class PaymentViewModel {
	
	public Double cumulatedPrice;
	public SectionForPaymentViewModel sectionForPaymentViewModel;
	
	public Double getCumulatedPrice() {
		return cumulatedPrice;
	}

	public void setCumulatedPrice(Double cumulatedPrice) {
		this.cumulatedPrice = cumulatedPrice;
	}

	public SectionForPaymentViewModel getSectionForPaymentViewModel() {
		return sectionForPaymentViewModel;
	}
	
	public void setSectionForPaymentViewModel(SectionForPaymentViewModel sectionForPaymentViewModel) {
		this.sectionForPaymentViewModel = sectionForPaymentViewModel;
	}
}
