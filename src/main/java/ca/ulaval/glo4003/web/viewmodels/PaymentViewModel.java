package ca.ulaval.glo4003.web.viewmodels;


public class PaymentViewModel {
	
	public String cumulatedPrice;
	public SectionForPaymentViewModel sectionForPaymentViewModel;
	
	public String getCumulatedPrice() {
		return cumulatedPrice;
	}

	public void setCumulatedPrice(String cumulatedPrice) {
		this.cumulatedPrice = cumulatedPrice;
	}

	public SectionForPaymentViewModel getSectionForPaymentViewModel() {
		return sectionForPaymentViewModel;
	}
	
	public void setSectionForPaymentViewModel(SectionForPaymentViewModel sectionForPaymentViewModel) {
		this.sectionForPaymentViewModel = sectionForPaymentViewModel;
	}
}
