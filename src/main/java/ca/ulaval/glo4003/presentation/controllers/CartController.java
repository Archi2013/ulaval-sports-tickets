package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.controllers.errormanagers.CartErrorManager;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.PayableItemsViewModelFactory;
import ca.ulaval.glo4003.services.CartService;
import ca.ulaval.glo4003.services.exceptions.InvalidTicketsException;
import ca.ulaval.glo4003.services.exceptions.TicketsNotFoundException;
import ca.ulaval.glo4003.utilities.Constants;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/panier", method = RequestMethod.GET)
public class CartController {

	private static final String CART_DETAIL_PAGE = "cart/details";
	
	@Inject
	private GameDao gameDao;

	@Inject
	private SectionDao sectionDao;
	
	@Inject
	CartService cartService;
	
	@Inject
	private PayableItemsViewModelFactory payableItemsViewModelFactory;
	
	@Inject
	private CartErrorManager cartErrorManager;

	@RequestMapping(value = "ajout-billets-generaux", method = RequestMethod.POST)
	public ModelAndView addGeneralTicketsToCart(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("chosenGeneralTicketsForm") @Valid ChosenGeneralTicketsViewModel chosenGeneralTicketsVM,
			BindingResult result) {
		ModelAndView mav = new ModelAndView(CART_DETAIL_PAGE);

		if (!currentUser.isLogged()) {
			cartErrorManager.prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		if (result.hasErrors()) {
			cartErrorManager.prepareErrorPageToShowTraffickedPageMessage(mav);
			return mav;
		}

		try {
			addGeneralTicketsToCart(chosenGeneralTicketsVM);
			mav.addObject("payableItems", getPayableItemsViewModel(chosenGeneralTicketsVM));
			mav.addObject("currency", Constants.CURRENCY);
		} catch (GameDoesntExistException | SectionDoesntExistException | TicketsNotFoundException | InvalidTicketsException e) {
			cartErrorManager.prepareErrorPage(mav, e);
		}

		return mav;
	}

	@RequestMapping(value = "ajout-billets-avec-siege", method = RequestMethod.POST)
	public ModelAndView addWithSeatTicketsToCart(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("chosenWithSeatTicketsForm") @Valid ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM,
			BindingResult result) {
		ModelAndView mav = new ModelAndView(CART_DETAIL_PAGE);

		if (!currentUser.isLogged()) {
			cartErrorManager.prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		if (result.hasErrors()) {
			cartErrorManager.prepareErrorPageToShowTraffickedPageMessage(mav);
			return mav;
		}

		try {
			addWithSeatTicketsToCart(chosenWithSeatTicketsVM);
			mav.addObject("payableItems", getPayableItemsViewModel(chosenWithSeatTicketsVM));
			mav.addObject("currency", Constants.CURRENCY);
		} catch (GameDoesntExistException | SectionDoesntExistException | TicketsNotFoundException | InvalidTicketsException e) {
			cartErrorManager.prepareErrorPage(mav, e);
		}

		return mav;
	}

	private void addWithSeatTicketsToCart(ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM)
			throws InvalidTicketsException, TicketsNotFoundException {
		cartService.addWithSeatTicketsToCart(chosenWithSeatTicketsVM.getSportName(),
				chosenWithSeatTicketsVM.getGameDate(), chosenWithSeatTicketsVM.getSectionName(),
				chosenWithSeatTicketsVM.getSelectedSeats());
	}
	
	private void addGeneralTicketsToCart(ChosenGeneralTicketsViewModel chosenGeneralTicketsVM)
			throws InvalidTicketsException, TicketsNotFoundException {
		cartService.addGeneralTicketsToCart(chosenGeneralTicketsVM.getSportName(),
				chosenGeneralTicketsVM.getGameDate(), chosenGeneralTicketsVM.getSectionName(),
				chosenGeneralTicketsVM.getNumberOfTicketsToBuy());
	}
	
	public PayableItemsViewModel getPayableItemsViewModel(ChosenTicketsViewModel choosenTicketsVM)
			throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(choosenTicketsVM.getSportName(), choosenTicketsVM.getGameDate());
		SectionDto sectionDto = sectionDao.getAvailable(choosenTicketsVM.getSportName(), choosenTicketsVM.getGameDate(),
				choosenTicketsVM.getSectionName());

		return payableItemsViewModelFactory.createViewModel(choosenTicketsVM, gameDto, sectionDto);
	}
}
