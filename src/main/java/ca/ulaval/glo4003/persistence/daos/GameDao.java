package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.GameDto;

@Repository
public interface GameDao {

	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException;

	public GameDto get(Long id) throws GameDoesntExistException;

	public GameDto get(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public void add(GameDto game) throws GameAlreadyExistException;

	public void commit();

	public void update(GameDto dto) throws GameDoesntExistException;
}
