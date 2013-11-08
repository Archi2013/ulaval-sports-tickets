package ca.ulaval.glo4003.domain.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.payment.Cart;
import ca.ulaval.glo4003.domain.utilities.payment.CreditCardFactory;
import ca.ulaval.glo4003.domain.utilities.payment.InvalidCardException;
import ca.ulaval.glo4003.domain.utilities.payment.MisterCard;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.PayableItemsViewModelFactory;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

	private static final int NUMBER_OF_TICKETS_TO_BUY = 4;

	private static final String SECTION_NAME = "Turquoise";

	private static final Long GAME_ID = 23L;

	private static final String ERROR_MESSAGE = "Erreur : il n'y a pas de tickets dans le panier d'achats. Le montant ne peut être affiché.";

	private static final String PRICE_FR = "36,70";

	private static final Double PRICE = 36.7;

	private static final Double CUMULATIVE_PRICE = 46.7;

	@Mock
	private GameDao gameDao;
	
	@Mock
	private SectionDao sectionDao;
	
	@Mock
	private PayableItemsViewModelFactory payableItemsViewModelFactory;
	
	@Mock
	private Calculator calculator;
	
	@Mock
	private Constants constants;
	
	@Mock
	private CreditCardFactory creditCardFactory;
	
	@Mock
	private Cart currentCart;
	
	@InjectMocks
	private PaymentService paymentService;
	
	private ChooseTicketsViewModel chooseTicketsVM;
	
	private List<String> selectedSeats;
	
	@Before
	public void setUp() throws Exception {
		chooseTicketsVM = mock(ChooseTicketsViewModel.class);
		selectedSeats = new ArrayList<>();
		selectedSeats.add("X3");
		selectedSeats.add("X7");
		when(chooseTicketsVM.getGameId()).thenReturn(GAME_ID);
		when(chooseTicketsVM.getSectionName()).thenReturn(SECTION_NAME);
		when(chooseTicketsVM.getNumberOfTicketsToBuy()).thenReturn(NUMBER_OF_TICKETS_TO_BUY);
		when(chooseTicketsVM.getSelectedSeats()).thenReturn(selectedSeats);
	}
	
	@Test
	public void saveToCart_should_save_choose_tickets_to_current_cart() throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = mock(GameDto.class);
		SectionDto sectionDto = mock(SectionDto.class);
		
		when(gameDao.get(GAME_ID)).thenReturn(gameDto);
		when(sectionDao.get(GAME_ID, SECTION_NAME)).thenReturn(sectionDto);
		when(calculator.calculateCumulativePrice(chooseTicketsVM, sectionDto)).thenReturn(CUMULATIVE_PRICE);
		
		paymentService.saveToCart(chooseTicketsVM);
		
		verify(currentCart).setNumberOfTicketsToBuy(NUMBER_OF_TICKETS_TO_BUY);
		verify(currentCart).setSelectedSeats(selectedSeats);
		verify(currentCart).setGameDto(gameDto);
		verify(currentCart).setSectionDto(sectionDto);
		verify(currentCart).setCumulativePrice(CUMULATIVE_PRICE);
	}
	
	@Test(expected=GameDoesntExistException.class)
	public void saveToCart_should_raise_GameDoesntExistException_when_gameDao_raise_GameDoesntExistException() throws GameDoesntExistException, SectionDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDao.get(GAME_ID, SECTION_NAME)).thenReturn(sectionDto);
		
		when(gameDao.get(GAME_ID)).thenThrow(new GameDoesntExistException());
		
		paymentService.saveToCart(chooseTicketsVM);
	}
	
	@Test(expected=SectionDoesntExistException.class)
	public void saveToCart_should_raise_SectionDoesntExistException_when_sectionDao_raise_SectionDoesntExistException() throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = mock(GameDto.class);
		when(gameDao.get(GAME_ID)).thenReturn(gameDto);
		
		when(sectionDao.get(GAME_ID, SECTION_NAME)).thenThrow(new SectionDoesntExistException());
		
		paymentService.saveToCart(chooseTicketsVM);
	}
	
	@Test
	public void getCumulativePriceFR_should_return_the_FR_price_when_currentCart_contains_tickets() {
		when(currentCart.containTickets()).thenReturn(true);
		when(currentCart.getCumulativePrice()).thenReturn(PRICE);
		when(calculator.toPriceFR(PRICE)).thenReturn(PRICE_FR);
		
		String actual = paymentService.getCumulativePriceFR();
		
		assertEquals(PRICE_FR, actual);
	}
	
	@Test
	public void getCumulativePriceFR_should_return_a_error_message_when_currentCart_contains_no_tickets() {
		when(currentCart.containTickets()).thenReturn(false);
		when(currentCart.getCumulativePrice()).thenReturn(PRICE);
		when(calculator.toPriceFR(PRICE)).thenReturn(PRICE_FR);
		
		String actual = paymentService.getCumulativePriceFR();
		
		assertEquals(ERROR_MESSAGE, actual);
	}
	
	@Ignore
	@Test
	public void given_a_paymentViewModel_payAmount_should_call_pay_of_a_credit_card() throws InvalidCardException {
		PaymentViewModel paymentVM = new PaymentViewModel();
		MisterCard creditCard = mock(MisterCard.class);
		
		when(creditCardFactory.createCreditCard(paymentVM)).thenReturn(creditCard);
		when(currentCart.containTickets()).thenReturn(true);
		when(currentCart.getCumulativePrice()).thenReturn(CUMULATIVE_PRICE);
		when(creditCard.isValid()).thenReturn(true);
		
		paymentService.payAmount(paymentVM);
		
		verify(creditCard).pay(CUMULATIVE_PRICE);
	}
	
	@Ignore
	@Test(expected=InvalidCardException.class)
	public void given_a_paymentViewModel_payAmount_should_raise_InvalidCardException_when_card_is_invalid() throws InvalidCardException {
		PaymentViewModel paymentVM = new PaymentViewModel();
		MisterCard creditCard = mock(MisterCard.class);
		
		when(creditCardFactory.createCreditCard(paymentVM)).thenReturn(creditCard);
		when(currentCart.containTickets()).thenReturn(true);
		when(currentCart.getCumulativePrice()).thenReturn(CUMULATIVE_PRICE);
		doThrow(new InvalidCardException()).when(creditCard).pay(CUMULATIVE_PRICE);
		
		paymentService.payAmount(paymentVM);
	}
	
	@Test
	public void emptyCart_should_empty_currentCart() {
		paymentService.emptyCart();
		
		verify(currentCart).empty();
	}
}
