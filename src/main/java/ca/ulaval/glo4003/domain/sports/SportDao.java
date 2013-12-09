package ca.ulaval.glo4003.domain.sports;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.exceptions.SportAlreadyExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.sports.dto.SportDto;

@Repository
public interface SportDao {

	public List<SportDto> getAll();

	public List<String> getAllSportNames();

	public SportDto get(String sportName) throws SportDoesntExistException;

	public void add(SportDto sport) throws SportAlreadyExistException;

	public void commit();
}
