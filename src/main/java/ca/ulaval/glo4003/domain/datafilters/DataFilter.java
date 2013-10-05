package ca.ulaval.glo4003.domain.datafilters;

import java.util.List;

public interface DataFilter<T> {

	void applyFilterOnList(List<T> originalList);
}
