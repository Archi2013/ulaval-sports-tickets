package ca.ulaval.glo4003.utilities.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.controllers.CartController;
import ca.ulaval.glo4003.presentation.controllers.GameController;
import ca.ulaval.glo4003.presentation.controllers.PaymentController;
import ca.ulaval.glo4003.presentation.controllers.SearchController;
import ca.ulaval.glo4003.presentation.controllers.SectionController;
import ca.ulaval.glo4003.presentation.controllers.SessionController;
import ca.ulaval.glo4003.presentation.controllers.SportController;
import ca.ulaval.glo4003.presentation.controllers.administration.AddGameController;
import ca.ulaval.glo4003.presentation.viewmodels.GameToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.GeneralTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SeatedTicketsToAddViewModel;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

public aspect ControllersLoggers {
	
	private static final Logger SportControllerLogger = LoggerFactory.getLogger(SportController.class);
	private static final Logger SectionControllerLogger = LoggerFactory.getLogger(SectionController.class);
	private static final Logger SessionControllerLogger = LoggerFactory.getLogger(SessionController.class);
	private static final Logger AdministrationControllerLogger = LoggerFactory.getLogger(AddGameController.class);
	private static final Logger SearchControllerLogger = LoggerFactory.getLogger(SearchController.class);
	private static final Logger PaymentControllerLogger = LoggerFactory.getLogger(PaymentController.class);
	private static final Logger CartControllerLogger = LoggerFactory.getLogger(CartController.class);
	
	pointcut SectionController_getSectionsForGame() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SectionController.getSectionsForGame(..));
	
	before() : SectionController_getSectionsForGame() {
		SectionControllerLogger.info("Getting ticket section : " + thisJoinPoint.getArgs()[1]);
	}
	
	after() throwing(Exception exception) : SectionController_getSectionsForGame(){
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
	
	before() : SessionController_submitSignIn(){
		SessionControllerLogger.info("Soumission des informations de connexion");
	}
	
	pointcut SessionController_logoutUser() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SessionController.logoutUser(..));
	
	
	after() : SessionController_logoutUser(){
		SessionControllerLogger.info("Usager déconnecté");
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
	
	after() throwing(UserDoesntHaveSavedPreferences exception) : SearchController_home(){
		SearchControllerLogger.info("l'usager ne possède pas de préférences");
		exception.printStackTrace();
	}
	
	pointcut SearchController_savePreferences() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SearchController.savePreferences(..));
	
	after() : SearchController_savePreferences(){
		SearchControllerLogger.info("préférences de l'usager sauvegardées");
	}
	
	pointcut SearchController_getList() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.SearchController.getList(..));
	
	after() : SearchController_getList(){
		SearchControllerLogger.info("mise à jour de la liste des éléments recherchés terminée");
	}
	
	pointcut CartController_showDetails() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.CartController.showDetails(..));
	
	before() : CartController_showDetails(){
		CartControllerLogger.info("affichage du panier d'achat");
	}
	
	after() throwing(Exception exception) : CartController_showDetails(){
		CartControllerLogger.info("Exception : " + exception.getClass().getSimpleName());
	}
	
	pointcut CartController_addGeneralTicketsToCart() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.CartController.addGeneralTicketsToCart(..));
	
	before() : CartController_addGeneralTicketsToCart(){
		CartControllerLogger.info("ajout de billets généraux au panier d'achat");
	}
	
	after() throwing(Exception exception) : CartController_addGeneralTicketsToCart(){
		CartControllerLogger.info("Exception : " + exception.getClass().getSimpleName());
	}
	
	pointcut CartController_addWithSeatTicketsToCart() :
		execution (public ModelAndView ca.ulaval.glo4003.presentation.controllers.CartController.addWithSeatTicketsToCart(..));
	
	before() : CartController_addWithSeatTicketsToCart(){
		CartControllerLogger.info("ajout de billets avec siège au panier d'achat");
	}
	
	after() throwing(Exception exception) : CartController_addWithSeatTicketsToCart(){
		CartControllerLogger.info("Exception : " + exception.getClass().getSimpleName());
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
