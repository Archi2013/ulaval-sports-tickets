package ca.ulaval.glo4003.datafilter;

import java.util.List;

public interface DataFilter<T> {

	void applyFilterOnList(List<T> originalList);
}
