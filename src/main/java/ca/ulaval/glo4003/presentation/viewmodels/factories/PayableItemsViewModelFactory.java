package ca.ulaval.glo4003.presentation.viewmodels.factories;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForPaymentViewModel;
import ca.ulaval.glo4003.utilities.Calculator;

@Component
public class PayableItemsViewModelFactory {
	
	@Inject
	SectionForPaymentViewModelFactory sectionForPaymentViewModelFactory;
	
	@Inject
	Calculator calculator;

	public PayableItemsViewModel createViewModel(ChooseTicketsViewModel chooseTicketsVM, GameDto gameDto, SectionDto sectionDto) {
		SectionForPaymentViewModel sectionForPaymentVM = sectionForPaymentViewModelFactory.createViewModel(chooseTicketsVM,
				gameDto, sectionDto);
		
		Double cumulativePrice = 0.0;
		if (sectionDto.isGeneralAdmission()) {
			cumulativePrice = calculator.calculateCumulativePriceForGeneralAdmission(chooseTicketsVM.getNumberOfTicketsToBuy(), sectionDto.getPrice());
		} else {
			cumulativePrice = calculator.calculateCumulativePriceForWithSeatAdmission(chooseTicketsVM.getSelectedSeats(), sectionDto.getPrice());
		}
		
		String cumulativePriceFR = calculator.toPriceFR(cumulativePrice);
		
		PayableItemsViewModel payableItemsVM = new PayableItemsViewModel();
		
		payableItemsVM.setSectionForPaymentViewModel(sectionForPaymentVM);
		payableItemsVM.setCumulativePrice(cumulativePriceFR);
		
		return payableItemsVM;
	}
}
