package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.GameDto;

@Repository
public interface GameDao {

	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException;

	public GameDto get(int id) throws GameDoesntExistException;

	public void add(GameDto game) throws GameAlreadyExistException;

	public void saveChanges(GameDto game) throws GameDoesntExistException;
}
