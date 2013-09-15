package ca.ulaval.glo4003.controllers;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.ulaval.glo4003.data_access.SportDao;
import ca.ulaval.glo4003.domain.Sport;

@Controller
@RequestMapping(value = "/sports", method = RequestMethod.GET)
public class SportController {

	private static final Logger logger = LoggerFactory.getLogger(SportController.class);

	@Inject
	private SportDao dao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	List<Sport> getSports() {
		logger.info("Getting all sports");

		List<Sport> sports = dao.getAll();
		return sports;
	}
}
