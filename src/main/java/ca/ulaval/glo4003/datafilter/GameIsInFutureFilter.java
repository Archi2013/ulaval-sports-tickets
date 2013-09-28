package ca.ulaval.glo4003.datafilter;

import static com.google.common.collect.Lists.newArrayList; 

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import ca.ulaval.glo4003.dto.GameDto;

@Component
public class GameIsInFutureFilter implements DataFilter<GameDto> {

	private DateTime currentDate;

	public GameIsInFutureFilter() {
		this.currentDate = new DateTime(); // Initialise par defaut au temps
											// courant
	}

	public GameIsInFutureFilter(DateTime currentDate) {
		this.currentDate = currentDate;
	}

	@Override
	public void applyFilterOnList(List<GameDto> originalList) {
		/*List<GameDto> result = newArrayList(Iterables.filter(originalList,
				new Predicate<GameDto>() {
			public boolean apply(GameDto game) {
				return game.getGameDate().isAfter(currentDate);
			}
		}));
		return result;*/
		
		List<GameDto> toRemove = new ArrayList<>();
		for (int i = 0; i < originalList.size(); i++) {
			if (originalList.get(i).getGameDate().isBefore(currentDate)) {
				toRemove.add(originalList.get(i));
			}
		}
		originalList.removeAll(toRemove);
	}

}
