package ca.ulaval.glo4003.presentation.viewmodels.factories;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForPaymentViewModel;
import ca.ulaval.glo4003.utilities.Calculator;

@Component
public class PayableItemsViewModelFactory {
	
	@Inject
	SectionForPaymentViewModelFactory sectionForPaymentViewModelFactory;
	
	@Inject
	Calculator calculator;

	public PayableItemsViewModel createViewModel(ChosenTicketsViewModel chosenTicketsVM, GameDto gameDto, SectionDto sectionDto) {
		SectionForPaymentViewModel sectionForPaymentVM = sectionForPaymentViewModelFactory.createViewModel(chosenTicketsVM,
				gameDto, sectionDto);
		
		Double cumulativePrice = 0.0;
		if (sectionDto.isGeneralAdmission()) {
			cumulativePrice = calculator.calculateCumulativePriceForGeneralAdmission(chosenTicketsVM.getNumberOfTicketsToBuy(), sectionDto.getPrice());
		} else {
			cumulativePrice = calculator.calculateCumulativePriceForWithSeatAdmission(chosenTicketsVM.getSelectedSeats(), sectionDto.getPrice());
		}
		
		String cumulativePriceFR = calculator.toPriceFR(cumulativePrice);
		
		PayableItemsViewModel payableItemsVM = new PayableItemsViewModel();
		
		payableItemsVM.setSectionForPaymentViewModel(sectionForPaymentVM);
		payableItemsVM.setCumulativePrice(cumulativePriceFR);
		
		return payableItemsVM;
	}
}
