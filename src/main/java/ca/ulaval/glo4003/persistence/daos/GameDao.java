package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.GameDto;

@Repository
public interface GameDao {

	List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException;
	GameDto get(long id) throws GameDoesntExistException;
	void add(GameDto game);

}
