package ca.ulaval.glo4003.web.viewmodels.factories;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionForPaymentViewModel;

@Component
public class PaymentViewModelFactory {
	
	@Inject
	SectionForPaymentViewModelFactory sectionForPaymentViewModelFactory;
	
	@Inject
	Calculator calculator;

	public PaymentViewModel createViewModel(ChooseTicketsViewModel chooseTicketsVM, GameDto gameDto, SectionDto sectionDto) {
		SectionForPaymentViewModel sectionForPaymentVM = sectionForPaymentViewModelFactory.createViewModel(chooseTicketsVM,
				gameDto, sectionDto);
		
		Double cumulatedPrice = calculator.calculateCumulativePrice(chooseTicketsVM, sectionDto);
		
		String cumulatedPriceFR = calculator.toPriceFR(cumulatedPrice);
		
		PaymentViewModel paymentVM = new PaymentViewModel();
		
		paymentVM.setSectionForPaymentViewModel(sectionForPaymentVM);
		paymentVM.setCumulativePrice(cumulatedPriceFR);
		
		return paymentVM;
	}
}
