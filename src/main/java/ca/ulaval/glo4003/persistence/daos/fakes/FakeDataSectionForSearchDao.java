package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.SectionForSearchDto;
import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.persistence.daos.SectionForSearchDao;


@Repository
public class FakeDataSectionForSearchDao implements SectionForSearchDao {
	
	@Override
	public List<SectionForSearchDto> getSections(TicketSearchPreferenceDto ticketSearchPreferenceDto) {
		// Faire qqc avec ticketSearchPreferenceDto
		
		SectionForSearchDto t1 = new SectionForSearchDto("Baseball Masculin", "Radiants", DateTime.now(),
				"Générale", "Générale", new Integer(3), new Double(16.9), "/sport/volleyball-feminin/match/40/billets/generale-generale");
		SectionForSearchDto t2 = new SectionForSearchDto("Soccer Masculin", "Endormis", DateTime.now(),
				"Générale", "Générale", new Integer(22), new Double(23.0), "/sport/volleyball-feminin/match/40/billets/generale-generale");
		SectionForSearchDto t3 = new SectionForSearchDto("Volleyball Féminin", "Kira", DateTime.now(),
				"VIP", "Orange", new Integer(7), new Double(16.9505), "/sport/volleyball-feminin/match/1/billets/vip-front-row");
		SectionForSearchDto t4 = new SectionForSearchDto("Volleyball Féminin", "Kira", DateTime.now(),
				"VIP", "Rouge", new Integer(4), new Double(17.95), "/sport/volleyball-feminin/match/1/billets/vip-front-row");
		List<SectionForSearchDto> sections = new ArrayList<>();
		sections.add(t1);
		sections.add(t2);
		sections.add(t3);
		sections.add(t4);
		return sections;
	}

}
