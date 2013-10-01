package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import com.google.common.base.Function;

public abstract class AbstractConverter<Dto, ViewModel> {

	public List<ViewModel> convert(List<Dto> gameDtos) {
		return transform(gameDtos, new Function<Dto, ViewModel>() {
			@Override
			public ViewModel apply(Dto gameDto) {
				return convert(gameDto);
			}
		});
	}

	public abstract ViewModel convert(Dto dto);
}
