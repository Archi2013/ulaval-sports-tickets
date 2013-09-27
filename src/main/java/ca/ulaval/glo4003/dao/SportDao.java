package ca.ulaval.glo4003.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.dto.SportDto;

@Repository
public interface SportDao {
	public List<SportDto> getAll();
	public SportDto get(String sportName);
}

