package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.CartErrorHandler;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;
import ca.ulaval.glo4003.services.CartViewService;
import ca.ulaval.glo4003.services.CommandCartService;
import ca.ulaval.glo4003.services.QueryCartService;
import ca.ulaval.glo4003.services.exceptions.InvalidTicketsException;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;
import ca.ulaval.glo4003.services.exceptions.TicketsNotFoundException;
import ca.ulaval.glo4003.utilities.Constants;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/panier", method = RequestMethod.GET)
public class CartController {

	private static final String CART_DETAIL_PAGE = "cart/details";
	
	@Inject
	CommandCartService commmandCartService;
	
	@Inject
	QueryCartService queryCartService;
	
	@Inject
	CartViewService cartViewService;
	
	@Inject
	private CartErrorHandler cartErrorHandler;
	
	@Autowired
	private User currentUser;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView showDetails() {
		ModelAndView mav = new ModelAndView(CART_DETAIL_PAGE);
		
		if (!currentUser.isLogged()) {
			cartErrorHandler.prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		try {
			mav.addObject("payableItems", cartViewService.getPayableItemsViewModel());
			mav.addObject("currency", Constants.CURRENCY);
		} catch (NoTicketsInCartException e) {
			cartErrorHandler.prepareErrorPage(mav, e);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "ajout-billets-generaux", method = RequestMethod.POST)
	public ModelAndView addGeneralTicketsToCart(@ModelAttribute("chosenGeneralTicketsForm") @Valid ChosenGeneralTicketsViewModel chosenGeneralTicketsVM,
			BindingResult result) {
		ModelAndView mav = new ModelAndView(CART_DETAIL_PAGE);

		if (!currentUser.isLogged()) {
			cartErrorHandler.prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		if (result.hasErrors()) {
			cartErrorHandler.prepareErrorPageToShowTraffickedPageMessage(mav);
			return mav;
		}

		try {
			addGeneralTicketsToCart(chosenGeneralTicketsVM);
			mav.addObject("payableItems", cartViewService.getPayableItemsViewModel());
			mav.addObject("currency", Constants.CURRENCY);
		} catch (TicketsNotFoundException | InvalidTicketsException | NoTicketsInCartException e) {
			cartErrorHandler.prepareErrorPage(mav, e);
		}

		return mav;
	}

	@RequestMapping(value = "ajout-billets-avec-siege", method = RequestMethod.POST)
	public ModelAndView addWithSeatTicketsToCart(@ModelAttribute("chosenWithSeatTicketsForm") @Valid ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM,
			BindingResult result) {
		ModelAndView mav = new ModelAndView(CART_DETAIL_PAGE);

		if (!currentUser.isLogged()) {
			cartErrorHandler.prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		if (result.hasErrors()) {
			cartErrorHandler.prepareErrorPageToShowTraffickedPageMessage(mav);
			return mav;
		}

		try {
			addWithSeatTicketsToCart(chosenWithSeatTicketsVM);
			mav.addObject("payableItems", cartViewService.getPayableItemsViewModel());
			mav.addObject("currency", Constants.CURRENCY);
		} catch (TicketsNotFoundException | InvalidTicketsException | NoTicketsInCartException e) {
			cartErrorHandler.prepareErrorPage(mav, e);
		}

		return mav;
	}
	
	private void addWithSeatTicketsToCart(ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM)
			throws InvalidTicketsException, TicketsNotFoundException {
		commmandCartService.addWithSeatTicketsToCart(chosenWithSeatTicketsVM.getSportName(),
				chosenWithSeatTicketsVM.getGameDate(), chosenWithSeatTicketsVM.getSectionName(),
				chosenWithSeatTicketsVM.getSelectedSeats());
	}
	
	private void addGeneralTicketsToCart(ChosenGeneralTicketsViewModel chosenGeneralTicketsVM)
			throws InvalidTicketsException, TicketsNotFoundException {
		commmandCartService.addGeneralTicketsToCart(chosenGeneralTicketsVM.getSportName(),
				chosenGeneralTicketsVM.getGameDate(), chosenGeneralTicketsVM.getSectionName(),
				chosenGeneralTicketsVM.getNumberOfTicketsToBuy());
	}
}
