package ca.ulaval.glo4003.web.viewmodel.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.web.converter.SportSimpleConverter;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportsViewModel;

@Component
public class SportsViewModelFactory {

	@Inject
	private SportSimpleConverter converter;

	public SportsViewModel createViewModel(List<SportDto> sports) {
		SportsViewModel viewModel = new SportsViewModel();
		List<SportSimpleViewModel> sportViewModels = converter.convert(sports);
		viewModel.getSports().addAll(sportViewModels);
		return viewModel;
	}

}
