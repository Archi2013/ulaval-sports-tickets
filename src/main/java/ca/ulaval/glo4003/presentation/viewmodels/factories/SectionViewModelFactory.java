package ca.ulaval.glo4003.presentation.viewmodels.factories;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.tickets.TicketTypeUrlMapper;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

@Component
public class SectionViewModelFactory {

	@Inject
	private TicketTypeUrlMapper ticketTypeUrlMapper;
	
	@Inject
	private Calculator calculator;
	
	@Inject
	private Constants constants;

	public SectionViewModel createViewModel(SectionDto section, GameDto game) {
		String dateFR = constants.toLongDateTimeFormatFR(game.getGameDate());
		String priceFR = calculator.toPriceFR(section.getPrice());
		
		return new SectionViewModel(section.getSectionName(), section.getNumberOfTickets(),
				priceFR, dateFR, game.getOpponents(), game.getLocation(),
				createUrl(section.getSectionName()), section.getSeats(), section.isGeneralAdmission());
	}

	public List<SectionViewModel> createViewModel(List<SectionDto> sections, GameDto gameDto) {
		List<SectionViewModel> sectionsVM = newArrayList();
		for (SectionDto sectionDto : sections) {
			sectionsVM.add(createViewModel(sectionDto, gameDto));
		}
		return sectionsVM;
	}

	private String createUrl(String sectionName) {
		return ticketTypeUrlMapper.getUrl(sectionName);
	}

}
