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
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.PayableItemsViewModelFactory;
import ca.ulaval.glo4003.services.CartService;
import ca.ulaval.glo4003.services.InvalidTicketsException;
import ca.ulaval.glo4003.services.TicketsNotFoundException;
import ca.ulaval.glo4003.utilities.Constants;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/panier", method = RequestMethod.GET)
public class CartController {

	private static final String ADD_TICKETS_PAGE = "cart/add-tickets";
	
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

	@RequestMapping(value = "ajout-billets", method = RequestMethod.POST)
	public ModelAndView addToCart(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("chooseTicketsForm") @Valid ChooseTicketsViewModel chooseTicketsVM,
			BindingResult result) {
		ModelAndView mav = new ModelAndView(ADD_TICKETS_PAGE);

		if (!currentUser.isLogged()) {
			cartErrorManager.prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		if (result.hasErrors()) {
			cartErrorManager.prepareErrorPageToShowTraffickedPageMessage(mav);
			return mav;
		}

		try {
			cartService.saveToCart(chooseTicketsVM);
			mav.addObject("payableItems", getPayableItemsViewModel(chooseTicketsVM));
			mav.addObject("currency", Constants.CURRENCY);
		} catch (GameDoesntExistException | SectionDoesntExistException | TicketsNotFoundException | InvalidTicketsException e) {
			cartErrorManager.prepareErrorPage(mav, e);
		}

		return mav;
	}

	public PayableItemsViewModel getPayableItemsViewModel(ChooseTicketsViewModel chooseTicketsVM)
			throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate());
		SectionDto sectionDto = sectionDao.getAvailable(chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate(),
				chooseTicketsVM.getSectionName());

		return payableItemsViewModelFactory.createViewModel(chooseTicketsVM, gameDto, sectionDto);
	}
}
