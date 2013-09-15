package ca.ulaval.glo4003.data_access;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.Sport;

@Repository
public interface SportDao {
	public List<Sport> getAll();
}
