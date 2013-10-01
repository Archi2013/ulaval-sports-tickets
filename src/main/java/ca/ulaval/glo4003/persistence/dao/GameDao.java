package ca.ulaval.glo4003.persistence.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.dto.GameDto;

@Repository
public interface GameDao {

	List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException;
	GameDto get(long id) throws GameDoesntExistException;

}