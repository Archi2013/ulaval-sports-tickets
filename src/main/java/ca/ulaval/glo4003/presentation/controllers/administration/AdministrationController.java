package ca.ulaval.glo4003.presentation.controllers.administration;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdministrationController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session) {
		ModelAndView mav = new ModelAndView("admin/home");
		
		return mav;
	}
	
	@RequestMapping(value = "/sans-autorisation", method = RequestMethod.GET)
	public String unauthorized() {
		return "admin/unauthorized";
	}
}
