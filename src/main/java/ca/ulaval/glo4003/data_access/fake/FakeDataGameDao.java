package ca.ulaval.glo4003.data_access.fake;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.data_access.GameDao;
import ca.ulaval.glo4003.dtos.GameDto;

@Repository
public class FakeDataGameDao implements GameDao {

	@Override
	public List<GameDto> getGamesForSport(String sportName) {
		GameDto firstGame = new GameDto(1);
		GameDto secondGame = new GameDto(2);
		
		List<GameDto> games = new ArrayList<GameDto>();
		games.add(firstGame);
		games.add(secondGame);
		
		return games;
	}

}
