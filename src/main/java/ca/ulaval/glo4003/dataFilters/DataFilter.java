package ca.ulaval.glo4003.dataFilters;

import java.util.List;

public interface DataFilter<T> {

	void applyFilterOnList(List<T> originalList);
}
