package ca.ulaval.glo4003.web.viewmodels.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.web.converters.SportConverter;
import ca.ulaval.glo4003.web.viewmodels.SportViewModel;
import ca.ulaval.glo4003.web.viewmodels.SportsViewModel;

@Component
public class SportsViewModelFactory {

	@Inject
	private SportConverter converter;

	public SportsViewModel createViewModel(List<SportDto> sports) {
		SportsViewModel viewModel = new SportsViewModel();
		List<SportViewModel> sportViewModels = converter.convert(sports);
		viewModel.getSports().addAll(sportViewModels);
		return viewModel;
	}

}
