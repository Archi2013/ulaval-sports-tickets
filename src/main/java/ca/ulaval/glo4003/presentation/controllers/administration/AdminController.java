package ca.ulaval.glo4003.presentation.controllers.administration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;

@Controller
@SessionAttributes({ "currentUser" })
public class AdminController {
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("currentUser") User currentUser) {
		ModelAndView mav = new ModelAndView("admin/home");
		
		return mav;
	}
}
