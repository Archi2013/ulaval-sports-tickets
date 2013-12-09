package ca.ulaval.glo4003.presentation.controllers.administration;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;

public aspect AdministratorVerificator {

	pointcut allMethodsInAdminPackage() : execution(public ModelAndView *..administration.*.*(..));

	ModelAndView around() : allMethodsInAdminPackage() {
		
		Object args[] = thisJoinPoint.getArgs();
		
 		for(int i = 0; i < args.length; i++){
	        Object arg = args[i];
	        if ( arg instanceof HttpSession ) {  	
	            HttpSession session = (HttpSession)arg;
	            
	            User user = (User) session.getAttribute("currentUser");
	            
	    		if (user != null && user.isAdmin() && user.isLogged()) {
	    			return proceed();
	    		}
	        }
		}
 		
		return new ModelAndView("redirect:/admin/sans-autorisation");
	}
}