package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.SportDto;

@Repository
public interface SportDao {

	public List<SportDto> getAll();
	public SportDto get(String sportName) throws SportDoesntExistException;
	public void add(SportDto sport) throws SportAlreadyExistException;
	public void saveChanges(SportDto sport) throws SportDoesntExistException;
}
