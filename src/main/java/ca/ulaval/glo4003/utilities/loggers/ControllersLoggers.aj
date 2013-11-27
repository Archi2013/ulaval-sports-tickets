package ca.ulaval.glo4003.utilities.loggers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.presentation.controllers.AddGameController;
import ca.ulaval.glo4003.presentation.controllers.GameController;
import ca.ulaval.glo4003.presentation.controllers.PaymentController;
import ca.ulaval.glo4003.presentation.controllers.SearchController;
import ca.ulaval.glo4003.presentation.controllers.SectionController;
import ca.ulaval.glo4003.presentation.controllers.SessionController;
import ca.ulaval.glo4003.presentation.controllers.SportController;
import ca.ulaval.glo4003.presentation.viewmodels.GameToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.GeneralTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SeatedTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SelectSportViewModel;
import ca.ulaval.glo4003.services.NoTicketsInCartException;

import org.springframework.web.servlet.ModelAndView;

public aspect ControllersLoggers {
	
	private static final Logger SportControllerLogger = LoggerFactory.getLogger(SportController.class);
	private static final Logger GameControllerLogger = LoggerFactory.getLogger(GameController.class);
	private static final Logger SectionControllerLogger = LoggerFactory.getLogger(SectionController.class);
	private static final Logger SessionControllerLogger = LoggerFactory.getLogger(SessionController.class);
	private static final Logger AdministrationControllerLogger = LoggerFactory.getLogger(AddGameController.class);
	private static final Logger SearchControllerLogger = LoggerFactory.getLogger(SearchController.class);
	private static final Logger PaymentControllerLogger = LoggerFactory.getLogger(PaymentController.class);
	
	pointcut SportController_getSportGames() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SportController.getSportGames(..));
	
	after() throwing(Exception exception) : SportController_getSportGames(){
		SportControllerLogger.info("==> Impossible to get games for sport: " + thisJoinPoint.getArgs()[0]);
		GameControllerLogger.info("==> Exception : " + exception.getMessage());
		exception.printStackTrace();
	}
	
	pointcut GameController_getTicketsForGame() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.GameController.getTicketsForGame(..));
	
	before() : GameController_getTicketsForGame() {
		GameControllerLogger.info("Getting all tickets for game : " + thisJoinPoint.getArgs()[0]);
	}
	
	after() throwing(Exception exception) : GameController_getTicketsForGame(){
		GameControllerLogger.info("==> Impossible to get all tickets for game : " + thisJoinPoint.getArgs()[0]);
		GameControllerLogger.info("==> Exception : " + exception.getMessage());
		exception.printStackTrace();
	}
	
	pointcut SectionController_getSectionForGame() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SectionController.getSectionForGame(..));
	
	before() : SectionController_getSectionForGame() {
		SectionControllerLogger.info("Getting ticket section : " + thisJoinPoint.getArgs()[1]);
	}
	
	after() throwing(Exception exception) : SectionController_getSectionForGame(){
		SportControllerLogger.info("Exception : " + exception.getClass().getSimpleName() + " : la section n'existe pas");
		exception.printStackTrace();
	}
	
	pointcut SessionController_submitSignIn() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SessionController.submitSignIn(..));

	after() throwing(Exception exception) : SessionController_submitSignIn(){
		SessionControllerLogger.info("==> Impossible to Sign In : " + thisJoinPoint.getArgs()[0]);
		SessionControllerLogger.info("==> Exception : " + exception.getMessage());
		exception.printStackTrace();
	}
	
	pointcut SessionController_registerUser() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SessionController.registerUser(..));
	
	
	after() throwing(Exception exception) : SessionController_registerUser(){
		SessionControllerLogger.info("==> Impossible to registerUser : " + thisJoinPoint.getArgs()[0]);
		SessionControllerLogger.info("==> Exception : " + exception.getMessage());
		exception.printStackTrace();
	}
	
	
	pointcut AdmnistrationController_addConnectedUserToModelAndView() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.AddGameController.addGame(..));
	
	before() : AdmnistrationController_addConnectedUserToModelAndView() {
		GameToAddViewModel gameToAddViewModel = (GameToAddViewModel) thisJoinPoint.getArgs()[0];
		AdministrationControllerLogger.info("Adminisatration : Add a new game for a sport : " + gameToAddViewModel.getSport());
	}
	
	after() throwing(Exception exception) : AdmnistrationController_addConnectedUserToModelAndView(){
		GameToAddViewModel gameToAddViewModel = (GameToAddViewModel) thisJoinPoint.getArgs()[0];
		AdministrationControllerLogger.info("==> Impossible d'ajouter la game : " + gameToAddViewModel.getSport());
		AdministrationControllerLogger.info("==> Exception : " + exception.getMessage());
		exception.printStackTrace();
	}
	
	pointcut AdmnistrationController_addTickets_selectSport() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.AddTicketsController.addTickets_selectSport(..));
	
	before() : AdmnistrationController_addTickets_selectSport() {
		SelectSportViewModel selectSportViewModel = (SelectSportViewModel) thisJoinPoint.getArgs()[0];
		AdministrationControllerLogger.info("Administration : Add a new game for a sport : " + selectSportViewModel.getSport());
		AdministrationControllerLogger.info("Ticket is of type : " + selectSportViewModel.getTypeBillet());
	}
	
	pointcut AdmnistrationController_addTickets_general() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.AddTicketsController.addTickets_general(..));
	
	before() : AdmnistrationController_addTickets_general() {
		GeneralTicketsToAddViewModel generalTicketsToAddViewModel = (GeneralTicketsToAddViewModel) thisJoinPoint.getArgs()[0];
		AdministrationControllerLogger.info("Adminisatration :Adding " + generalTicketsToAddViewModel.getNumberOfTickets() + "new general tickets to game"
				+ generalTicketsToAddViewModel.getGameDate());
	}
	
	after() throwing(Exception exception) : AdmnistrationController_addTickets_general(){
		GeneralTicketsToAddViewModel generalTicketsToAddViewModel = (GeneralTicketsToAddViewModel) thisJoinPoint.getArgs()[0];
		AdministrationControllerLogger.info("Administration : Impossible d'ajouter la game : " + generalTicketsToAddViewModel.getGameDate());
		AdministrationControllerLogger.info("==> Exception : " + exception.getMessage());
		exception.printStackTrace();
	}
	
	pointcut AdmnistrationController_addTickets_seated() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.AddTicketsController.addTickets_seated(..));
	
	before() : AdmnistrationController_addTickets_seated() {
		SeatedTicketsToAddViewModel seatedTicketsToAddViewModel = (SeatedTicketsToAddViewModel) thisJoinPoint.getArgs()[0];
		AdministrationControllerLogger.info("Administration: adding a seated ticket to game on " + seatedTicketsToAddViewModel.getGameDate() + " in seat "
				+ seatedTicketsToAddViewModel.getSeat() + " of section " + seatedTicketsToAddViewModel.getSection());
	}
	
	after() throwing(Exception exception) : AdmnistrationController_addTickets_seated(){
		SeatedTicketsToAddViewModel seatedTicketsToAddViewModel = (SeatedTicketsToAddViewModel) thisJoinPoint.getArgs()[0];
		AdministrationControllerLogger.info("Administration : Impossible d'ajouter la game : " + seatedTicketsToAddViewModel.getGameDate());
		AdministrationControllerLogger.info("==> Exception : " + exception.getMessage());
		exception.printStackTrace();
	}
	
	pointcut SearchController_home() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SearchController.home(..));
	
	after() throwing(Exception exception) : SearchController_home(){
		SearchControllerLogger.info("no preferences saved");
		exception.printStackTrace();
	}
	
	pointcut PaymentController_home() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.PaymentController.home(..));
	
	after() throwing(Exception exception) : PaymentController_home(){
		PaymentControllerLogger.info("Exception : " + exception.getClass().getSimpleName() + " : ticket introuvable");
		exception.printStackTrace();
	}
	
	pointcut PaymentController_modeOfPayment() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.PaymentController.modeOfPayment(..));
	
	after() throwing(Exception exception) : PaymentController_modeOfPayment(){
		PaymentControllerLogger.info("Exception : " + exception.getClass().getSimpleName() + " : pas de tickets dans le panier d'achat");
		exception.printStackTrace();
	}
	
	pointcut PaymentController_validate() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.PaymentController.validate(..));
	
	after() throwing(Exception exception) : PaymentController_validate(){
		if(exception instanceof NoTicketsInCartException){
			PaymentControllerLogger.info("Exception : " + exception.getClass().getSimpleName() + " pas de tickets dans le panier d'achat");
			exception.printStackTrace();
		}
		if(exception instanceof InvalidCreditCardException){
			PaymentControllerLogger.info("Exception : " + exception.getClass().getSimpleName() + " : carte de crédit invalide");
			exception.printStackTrace();
		}
	}
}
