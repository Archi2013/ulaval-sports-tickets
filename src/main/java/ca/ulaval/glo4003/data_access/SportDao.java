package ca.ulaval.glo4003.data_access;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.dtos.SportDto;

@Repository
public interface SportDao {
	public List<SportDto> getAll();
	public SportDto get(String sportName);
}

