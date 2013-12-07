package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.services.SectionService;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.persistence.XmlIntegrityException;
import ca.ulaval.glo4003.utilities.time.UrlDateTimeFactory;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/sport/{sportNameUrl}/match/{dateString}", method = RequestMethod.GET)
public class SectionController {

	private static final String SECTION_LIST = "section/list";

	private static final String SECTION_NO_TICKET = "section/no-ticket";

	private static final String ERROR_404_PAGE = "error/404";

	private static final String SECTION_DETAILS_PAGE = "section/details";

	@Inject
	private SectionService sectionService;
	
	@Inject
	private SportUrlMapper sportUrlMapper;
	
	@Inject
	private UrlDateTimeFactory urlDateTimeFactory;

	@RequestMapping(value = "/billets/{ticketType}", method = RequestMethod.GET)
	public ModelAndView getSectionDetails(@PathVariable String dateString, @PathVariable String sportNameUrl,
			@PathVariable String ticketType) {
		try {
			String sportName = sportUrlMapper.getSportName(sportNameUrl);
			DateTime gameDate = urlDateTimeFactory.create(dateString).getDateTime();
			ModelAndView mav = new ModelAndView(SECTION_DETAILS_PAGE);
			mav.addObject("currency", Constants.CURRENCY);

			SectionViewModel section = sectionService.getAvailableSection(sportName, gameDate, ticketType);

			mav.addObject("section", section);

			if (section.isGeneralAdmission()) {
				ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = sectionService.getChosenGeneralTicketsViewModel(
						sportName, gameDate, ticketType);
				mav.addObject("chosenGeneralTicketsForm", chosenGeneralTicketsVM);
			} else {
				ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = sectionService
						.getChosenWithSeatTicketsViewModel(sportName, gameDate, ticketType);
				mav.addObject("chosenWithSeatTicketsForm", chosenWithSeatTicketsVM);
			}

			return mav;
		} catch (SectionDoesntExistException | NoSportForUrlException e) {
			return new ModelAndView(ERROR_404_PAGE);
		}
	}

	@RequestMapping(value = "/billets", method = RequestMethod.GET)
	public ModelAndView getSectionsForGame(@PathVariable String dateString, @PathVariable String sportNameUrl) {
		try {
			String sportName = sportUrlMapper.getSportName(sportNameUrl);
			DateTime gameDate = urlDateTimeFactory.create(dateString).getDateTime();
			ModelAndView mav = new ModelAndView(SECTION_LIST);

			mav.addObject("currency", Constants.CURRENCY);

			SectionsViewModel sectionsViewModel = sectionService.getAvailableSectionsForGame(sportName, gameDate);
			mav.addObject("gameSections", sectionsViewModel);
			return mav;

		} catch (GameDoesntExistException | NoSportForUrlException e) {
			return new ModelAndView(ERROR_404_PAGE);
		} catch (XmlIntegrityException e) {
			return new ModelAndView(SECTION_NO_TICKET);
		}
	}
}
