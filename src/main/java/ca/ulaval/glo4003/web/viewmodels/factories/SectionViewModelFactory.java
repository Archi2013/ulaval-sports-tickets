package ca.ulaval.glo4003.web.viewmodels.factories;

import static com.google.common.collect.Lists.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.SectionDoesntExistInPropertiesFileException;
import ca.ulaval.glo4003.domain.utilities.SectionUrlMapper;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;

@Component
public class SectionViewModelFactory {

	@Inject
	private SectionUrlMapper sectionUrlMapper;

	public SectionViewModel createViewModel(SectionDto section, GameDto game) {
		String dateFR = game.getGameDate().toString("d MMMM yyyy à HH'h'mm z");
		String priceFR = (new Double(section.getPrice())).toString();
		priceFR = priceFR.replace(".", ",");
		
		List<String> l = new ArrayList<>();
		l.add("2A");
		l.add("375");
		l.add("X1");
		
		return new SectionViewModel(section.getAdmissionType(), section.getSectionName(), section.getNumberOfTickets(),
				priceFR, dateFR, game.getOpponents(), createUrl(section.getAdmissionType(),
						section.getSectionName()), l);
	}

	public List<SectionViewModel> createViewModel(List<SectionDto> sections, GameDto gameDto) {
		List<SectionViewModel> sectionsVM = newArrayList();
		for (SectionDto sectionDto : sections) {
			sectionsVM.add(createViewModel(sectionDto, gameDto));
		}
		return sectionsVM;
	}

	private String createUrl(String admissionType, String sectionName) {
		try {
			return sectionUrlMapper.getSectionUrl(admissionType, sectionName);
		} catch (RuntimeException | SectionDoesntExistInPropertiesFileException e) {
			return "erreur";
		}
	}

}
