package ca.ulaval.glo4003.domain.utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.controllers.HomeController;
import ca.ulaval.glo4003.presentation.controllers.SportController;
import org.springframework.web.servlet.ModelAndView;

public aspect LoggerAspect {
	
	private static final Logger HomeControllerLogger = LoggerFactory.getLogger(HomeController.class);
	private static final Logger SportControllerLogger = LoggerFactory.getLogger(SportController.class);
	
	pointcut HomeController_home() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.HomeController.home(..));
	
	after() : HomeController_home() {
		HomeControllerLogger.info("Welcome home! The client locale is {}.", thisJoinPoint.getArgs()[0]);
		System.out.println("Welcome home! The client locale is "+ thisJoinPoint.getArgs()[0]);
	}
	
	pointcut HomeController_addConnectedUserToModelAndView() :
		execution (private void ca.ulaval.glo4003.presentation.controllers.HomeController.addConnectedUserToModelAndView(..));
	
	after() : HomeController_addConnectedUserToModelAndView() {
		Object[] parameterList = thisJoinPoint.getArgs();
		if((boolean) parameterList[1]){
			HomeControllerLogger.info("usagé connecté");
			System.out.println("usagé connecté");
		}
		else{
			HomeControllerLogger.info("usagé non connecté");
			System.out.println("usagé non connecté");
		}
	}
	
	pointcut SportController_getSports() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SportController.getSports());
	
	before() : SportController_getSports() {
		SportControllerLogger.info("Getting all sports");
	}
	//LOG MANQUANT...
	
	pointcut SportController_getSportGames() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SportController.getSportGames(..));
	
	before() : SportController_getSportGames() {
		SportControllerLogger.info("Getting games for sport: " + thisJoinPoint.getArgs()[0]);
	}
	//LOG MANQUANT...
	after() throwing(Exception exception) : SportController_getSportGames(){
		SportControllerLogger.info("==> Impossible to get games for sport: " + thisJoinPoint.getArgs()[0]);
		exception.printStackTrace();
	}
	
}
