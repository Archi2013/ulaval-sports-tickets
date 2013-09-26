package ca.ulaval.glo4003.dataFilters;

import java.util.List;

public interface DataFilter<T> {

	List<T> applyFilter(List<T> originalList);
}
