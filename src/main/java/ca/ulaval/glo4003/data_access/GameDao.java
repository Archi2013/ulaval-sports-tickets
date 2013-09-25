package ca.ulaval.glo4003.data_access;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.dtos.GameDto;

@Repository
public interface GameDao {

	List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException;

}
