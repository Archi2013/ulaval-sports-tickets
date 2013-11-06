package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Cart;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.PaymentViewModelFactory;

@Service
public class PaymentService {
	
	@Inject
	GameDao gameDao;
	
	@Inject
	SectionDao sectionDao;
	
	@Inject
	PaymentViewModelFactory paymentViewModelFactory;
	
	@Inject
	Calculator calculator;
	
	@Autowired
	public Cart currentCart;

	public PaymentViewModel getPaymentViewModel(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getGameId());
		SectionDto sectionDto = sectionDao.get(chooseTicketsVM.getGameId(), chooseTicketsVM.getSectionName());
		
		return paymentViewModelFactory.createViewModel(chooseTicketsVM, gameDto, sectionDto);
	}

	public void saveToCart(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getGameId());
		SectionDto sectionDto = sectionDao.get(chooseTicketsVM.getGameId(), chooseTicketsVM.getSectionName());
		
		Double cumulativePrice = calculator.calculateCumulativePrice(chooseTicketsVM, sectionDto);
		
		currentCart.setGameDto(gameDto);
		currentCart.setSectionDto(sectionDto);
		currentCart.setCumulativePrice(cumulativePrice);
	}

}
