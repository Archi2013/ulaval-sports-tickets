package ca.ulaval.glo4003.dataFilters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.dtos.GameDto;

public class GameIsInFutureFilter implements DataFilter<GameDto> {

	private DateTime currentDate;

	public GameIsInFutureFilter(DateTime currentDate) {
		this.currentDate = currentDate;
	}

	@Override
	public List<GameDto> applyFilter(List<GameDto> originalList) {
		List<GameDto> toRemove = new ArrayList<GameDto>();

		for (int i = 0; i < originalList.size(); i++) {
			if (originalList.get(i).getGameDate().isBefore(currentDate)) {
				toRemove.add(originalList.get(i));
			}
		}
		for (int i = 0; i < toRemove.size(); i++) {
			originalList.removeAll(toRemove);
		}

		return originalList;
	}

}
