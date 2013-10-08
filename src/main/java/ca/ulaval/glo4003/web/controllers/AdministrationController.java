package ca.ulaval.glo4003.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.web.viewmodels.GameToAddViewModel;

@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdministrationController {
	private static final Logger logger = LoggerFactory.getLogger(AdministrationController.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home() {
		logger.info("Adminisatration : Home");
		return "admin/home";
	}
	
	@RequestMapping(value = "/match", method = RequestMethod.GET)
	public ModelAndView game() {
		logger.info("Adminisatration : Page to add a new game for a sport");
		return new ModelAndView("admin/game", "command", new GameToAddViewModel());
	}
	
	@RequestMapping(value = "/ajout-match", method = RequestMethod.POST)
	public String addGame(@ModelAttribute("SpringWeb")GameToAddViewModel gameToAddVM, Model model) {
		logger.info("Adminisatration : Add a new game for a sport : " + gameToAddVM.getSport());
		
		model.addAttribute("game", gameToAddVM);
		
		// Ajout à la base de donnée
		
		return "admin/game-added";
	}
}
