package ca.ulaval.glo4003.web.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.datafilter.DataFilter;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.persistence.dao.SportDao;
import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapper;
import ca.ulaval.glo4003.web.converter.SportConverter;
import ca.ulaval.glo4003.web.converter.SportSimpleConverter;

@Controller
@RequestMapping(value = "/sport", method = RequestMethod.GET)
public class SportController {

	private static final Logger logger = LoggerFactory.getLogger(SportController.class);

	@Inject
	private SportDao sportDao;

	@Inject
	private DataFilter<GameDto> filter;
	
	@Inject
	private SportSimpleConverter sportSimpleConverter;
	
	@Inject
	private SportConverter sportConverter;
	
	@Inject
	private SportUrlMapper sportUrlMapper;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSports(Model model) {
		logger.info("Getting all sports");
		
		model.addAttribute("sports", sportSimpleConverter.convert(sportDao.getAll()));
		
		return "sport/list";
	}

	@RequestMapping(value = "/{sportUrl}/matchs", method = RequestMethod.GET)
	public String getSportGames(@PathVariable String sportUrl, Model model) {
		try {
			String sportName = sportUrlMapper.getSportName(sportUrl);
			logger.info("Getting games for sport: " + sportName);

			SportDto sportDto = sportDao.get(sportName);
			filter.applyFilterOnList(sportDto.getGames());
			
			if (sportDto.getGames().isEmpty()) {
				return "sport/no-games";
			} else {
				model.addAttribute("sport", sportConverter.convert(sportDto));
				return "sport/games";
			}
		} catch (RuntimeException | SportDoesntExistInPropertieFileException e) {
			logger.info("==> Impossible to get games for sport: " + sportUrl);
			e.printStackTrace();
			return "error/404";
		}
	}
}
