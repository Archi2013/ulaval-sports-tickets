package ca.ulaval.glo4003.presentation.controllers;

import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;

public aspect ConnectedVerificator {

	pointcut allControllersWithAUserInParam() : execution(public ModelAndView *..controllers..*.*(User,..));

	// Verify is logged
	ModelAndView around() : allControllersWithAUserInParam() {
		Object args[] = thisJoinPoint.getArgs();
		
 		for(int i = 0; i < args.length; i++){
	        Object arg = args[i];
	        if ( arg instanceof User ) {
	            User user = (User)arg;
	            
	    		if (user.isLogged()) {
	    			return proceed();
	    		}
	        }
		}
 		
		return new ModelAndView("redirect:/usager/connexion");
	}
}