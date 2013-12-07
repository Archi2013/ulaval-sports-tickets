package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.presentation.viewmodels.SportsViewModel;
import ca.ulaval.glo4003.services.SportViewService;

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
@SessionAttributes({ "currentUser" })
public class SportController {

	@Inject
	private SportViewService sportViewService;

	@RequestMapping(value = "sports", method = RequestMethod.GET)
	public ModelAndView getSports() {
		ModelAndView mav = new ModelAndView("sport/list");

		SportsViewModel sports = sportViewService.getSports();
		mav.addObject("sports", sports);
		return mav;
	}
}
