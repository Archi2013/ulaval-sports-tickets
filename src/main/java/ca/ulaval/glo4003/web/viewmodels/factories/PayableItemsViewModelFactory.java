package ca.ulaval.glo4003.web.viewmodels.factories;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionForPaymentViewModel;

@Component
public class PayableItemsViewModelFactory {
	
	@Inject
	SectionForPaymentViewModelFactory sectionForPaymentViewModelFactory;
	
	@Inject
	Calculator calculator;

	public PayableItemsViewModel createViewModel(ChooseTicketsViewModel chooseTicketsVM, GameDto gameDto, SectionDto sectionDto) {
		SectionForPaymentViewModel sectionForPaymentVM = sectionForPaymentViewModelFactory.createViewModel(chooseTicketsVM,
				gameDto, sectionDto);
		
		Double cumulatedPrice = calculator.calculateCumulativePrice(chooseTicketsVM, sectionDto);
		
		String cumulatedPriceFR = calculator.toPriceFR(cumulatedPrice);
		
		PayableItemsViewModel payableItemsVM = new PayableItemsViewModel();
		
		payableItemsVM.setSectionForPaymentViewModel(sectionForPaymentVM);
		payableItemsVM.setCumulativePrice(cumulatedPriceFR);
		
		return payableItemsVM;
	}
}
