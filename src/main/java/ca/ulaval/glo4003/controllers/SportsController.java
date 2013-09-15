package ca.ulaval.glo4003.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.ulaval.glo4003.domain.Sport;

@Controller
@RequestMapping(value = "/sports", method = RequestMethod.GET)
public class SportsController {

	private static final Logger logger = LoggerFactory.getLogger(SportsController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	List<Sport> getSports() {
		logger.info("Getting all sports");

		List<Sport> sports = new ArrayList<Sport>();
		Sport hockey = new Sport("Hockey");
		Sport baseball = new Sport("Baseball");
		sports.add(hockey);
		sports.add(baseball);
		return sports;
	}
}
