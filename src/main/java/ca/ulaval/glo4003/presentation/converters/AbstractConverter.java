package ca.ulaval.glo4003.presentation.converters;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import com.google.common.base.Function;

public abstract class AbstractConverter<Dto, ViewModel> {

	public List<ViewModel> convert(List<Dto> dtos) {
		return transform(dtos, new Function<Dto, ViewModel>() {
			@Override
			public ViewModel apply(Dto dto) {
				return convert(dto);
			}
		});
	}

	public abstract ViewModel convert(Dto dto);
}
