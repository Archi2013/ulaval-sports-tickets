package ca.ulaval.glo4003.web.viewmodels.factories;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionForPaymentViewModel;

@Component
public class PaymentViewModelFactory {

	public PaymentViewModel createViewModel(ChooseTicketsViewModel chooseTicketsVM, GameDto gameDto, SectionDto sectionDto) {
		SectionForPaymentViewModel sectionForPaymentVM = new SectionForPaymentViewModel();
		sectionForPaymentVM.setNumberOfTicketsToBuy(chooseTicketsVM.getNumberOfTicketsToBuy());
		sectionForPaymentVM.setSelectedSeats(chooseTicketsVM.getSelectedSeats());
		if (sectionDto.isGeneralAdmission()) {
			sectionForPaymentVM.setTicketKind(TicketKind.GENERAL_ADMISSION);
		} else {
			sectionForPaymentVM.setTicketKind(TicketKind.WITH_SEAT);
		}
		sectionForPaymentVM.setAdmissionType(sectionDto.getAdmissionType());
		sectionForPaymentVM.setSectionName(sectionDto.getSectionName());
		sectionForPaymentVM.setDate(gameDto.getGameDate());
		sectionForPaymentVM.setOpponents(gameDto.getOpponents());
		sectionForPaymentVM.setSport(gameDto.getSportName());
		
		Double cumulatedPrice = 0.0;
		
		if (sectionDto.isGeneralAdmission()) {
			cumulatedPrice = chooseTicketsVM.getNumberOfTicketsToBuy() * sectionDto.getPrice();
		} else {
			cumulatedPrice = chooseTicketsVM.getSelectedSeats().size() * sectionDto.getPrice();
		}
		
		PaymentViewModel paymentVM = new PaymentViewModel();
		
		paymentVM.setSectionForPaymentViewModel(sectionForPaymentVM);
		paymentVM.setCumulatedPrice(cumulatedPrice);
		
		return paymentVM;
	}

}
