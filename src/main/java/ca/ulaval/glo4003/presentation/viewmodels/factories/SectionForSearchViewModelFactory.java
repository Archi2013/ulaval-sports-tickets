package ca.ulaval.glo4003.presentation.viewmodels.factories;

import static com.google.common.collect.Lists.*;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.search.SectionForSearchDto;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

import com.google.common.base.Function;

@Component
public class SectionForSearchViewModelFactory {
	
	@Inject
	private Calculator calculator;
	
	@Inject
	private Constants constants;

	public List<SectionForSearchViewModel> createViewModels(List<SectionForSearchDto> ticketForSearchDtos) {
		List<SectionForSearchViewModel> list = transform(ticketForSearchDtos, new Function<SectionForSearchDto, SectionForSearchViewModel>() {
			public SectionForSearchViewModel apply(SectionForSearchDto section) {
				return new SectionForSearchViewModel(section.sport, section.opponents, section.location, constants.toLongDateTimeFormatFR(section.date),
						section.section, section.numberOfTicket, calculator.toPriceFR(section.price), section.url);
			}
		});
		return list;
	}

}
