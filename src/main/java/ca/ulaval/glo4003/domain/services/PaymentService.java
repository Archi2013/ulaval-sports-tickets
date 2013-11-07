package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Cart;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.CreditCardType;
import ca.ulaval.glo4003.domain.utilities.payment.CreditCard;
import ca.ulaval.glo4003.domain.utilities.payment.CreditCardFactory;
import ca.ulaval.glo4003.domain.utilities.payment.InvalidCardException;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.PayableItemsViewModelFactory;

@Service
public class PaymentService {
	
	@Inject
	GameDao gameDao;
	
	@Inject
	SectionDao sectionDao;
	
	@Inject
	PayableItemsViewModelFactory payableItemsViewModelFactory;
	
	@Inject
	Calculator calculator;
	
	@Inject
	Constants constants;
	
	@Inject
	CreditCardFactory creditCardFactory;
	
	@Autowired
	public Cart currentCart;

	public Boolean isValidPayableItemsViewModel(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException, SectionDoesntExistException {
		SectionDto sectionDto = sectionDao.get(chooseTicketsVM.getGameId(), chooseTicketsVM.getSectionName());
		
		if (sectionDto.isGeneralAdmission()) {
			Integer numberOfTickets = chooseTicketsVM.getNumberOfTicketsToBuy();
			if (numberOfTickets < 1 || numberOfTickets > sectionDto.getNumberOfTickets()) {
				return false;
			}
		} else {
			Integer numberOfTickets = chooseTicketsVM.getSelectedSeats().size();
			if (numberOfTickets < 1 || numberOfTickets > sectionDto.getNumberOfTickets()) {
				return false;
			}
			for (String seat : chooseTicketsVM.getSelectedSeats()) {
				if (!sectionDto.getSeats().contains(seat)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public PayableItemsViewModel getPayableItemsViewModel(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getGameId());
		SectionDto sectionDto = sectionDao.get(chooseTicketsVM.getGameId(), chooseTicketsVM.getSectionName());
		
		return payableItemsViewModelFactory.createViewModel(chooseTicketsVM, gameDto, sectionDto);
	}

	public void saveToCart(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getGameId());
		SectionDto sectionDto = sectionDao.get(chooseTicketsVM.getGameId(), chooseTicketsVM.getSectionName());
		
		Double cumulativePrice = calculator.calculateCumulativePrice(chooseTicketsVM, sectionDto);
		
		currentCart.setNumberOfTicketsToBuy(chooseTicketsVM.getNumberOfTicketsToBuy());
		currentCart.setSelectedSeats(chooseTicketsVM.getSelectedSeats());
		currentCart.setGameDto(gameDto);
		currentCart.setSectionDto(sectionDto);
		currentCart.setCumulativePrice(cumulativePrice);
	}

	public List<CreditCardType> getCreditCardTypes() {
		return constants.getCreditCardTypes();
	}

	public String getCumulativePriceFR() {
		if (currentCart.containTickets()) {
			return calculator.toPriceFR(currentCart.getCumulativePrice());
		} else {
			return "Erreur : il n'y a pas de tickets dans le panier d'achats. Le montant ne peut être affiché.";
		}
	}

	public void payAmount(PaymentViewModel paymentVM) throws InvalidCardException {
		CreditCard creditCard = creditCardFactory.createCreditCard(paymentVM);
		creditCard.pay(currentCart.getCumulativePrice());
	}

	public void emptyCart() {
		currentCart.empty();
	}

}
