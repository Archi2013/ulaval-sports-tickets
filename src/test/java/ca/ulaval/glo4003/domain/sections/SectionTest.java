package ca.ulaval.glo4003.domain.sections;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SectionTest {
	private static final String GENERAL_SECTION = "Générale";
	private static final String SEATED_SECTION = "AnythingElse";
	private static final String A_SEAT = "Seat";
	private static final String ANOTHER_SEAT = "Seat2";

	private static final int A_NUMBER = 20;
	private static final int A_SMALLER_NUMBER = 15;
	private static final int A_LARGER_NUMBER = 35;

	private Section sectionGenerale;
	private Section sectionWithSeat;
	private Set<String> A_LIST_OF_SEATS;
	private Set<String> A_LONGER_LIST_OF_SEATS;
	private Set<String> WRONG_LIST_OF_SEATS;

	@Before
	public void setUp() {
		A_LIST_OF_SEATS = new HashSet<String>();
		A_LIST_OF_SEATS.add(A_SEAT);
		A_LONGER_LIST_OF_SEATS = new HashSet<String>();
		A_LONGER_LIST_OF_SEATS.add(A_SEAT);
		A_LONGER_LIST_OF_SEATS.add(ANOTHER_SEAT);
		WRONG_LIST_OF_SEATS = new HashSet<String>();
		WRONG_LIST_OF_SEATS.add(ANOTHER_SEAT);

		sectionGenerale = new Section(new SectionDto(GENERAL_SECTION, A_NUMBER, 0));
		sectionWithSeat = new Section(new SectionDto(SEATED_SECTION, A_LIST_OF_SEATS.size(), 0, A_LIST_OF_SEATS));
	}

	@Test
	public void if_sectionName_is_not_Générale_isGeneralAdmission_return_true() {
		assertFalse(sectionWithSeat.isGeneralAdmission());
	}

	@Test
	public void isValidNumberOfTicketsForGeneralTickets_return_true_if_asked_a_smaller_number() {
		sectionGenerale = new Section(new SectionDto(GENERAL_SECTION, A_NUMBER, 0));

		assertTrue(sectionGenerale.isValidNumberOfTicketsForGeneralTickets(A_SMALLER_NUMBER));
	}

	@Test
	public void isValidNumberOfTicketsForGeneralTickets_return_false_if_asked_a_number_smaller_than_one() {
		sectionGenerale = new Section(new SectionDto(GENERAL_SECTION, A_NUMBER, 0));

		assertFalse(sectionGenerale.isValidNumberOfTicketsForGeneralTickets(0));
	}

	@Test
	public void isValidNumberOfTicketsForGeneralTickets_return_false_if_asked_a_larger_number() {
		sectionGenerale = new Section(new SectionDto(GENERAL_SECTION, A_NUMBER, 0));

		assertFalse(sectionGenerale.isValidNumberOfTicketsForGeneralTickets(A_LARGER_NUMBER));
	}

	@Test
	public void isValidSelectedSeatForWithSeatsTickets_return_false_if_not_ticket_is_selected() {
		sectionWithSeat = new Section(new SectionDto(SEATED_SECTION, 0, 0, new HashSet<String>()));

		assertFalse(sectionWithSeat.isValidSelectedSeatsForWithSeatTickets(A_LIST_OF_SEATS));
	}

	@Test
	public void isValidSelectedSeatForWithSeatsTickets_return_false_if_a_longer_ticket_lists_is_demanded() {
		assertFalse(sectionWithSeat.isValidSelectedSeatsForWithSeatTickets(A_LONGER_LIST_OF_SEATS));
	}

	@Test
	public void isValidSelectedSeatForWithSeatsTicket_return_false_if_absent_ticket_is_demanded() {
		assertFalse(sectionWithSeat.isValidSelectedSeatsForWithSeatTickets(WRONG_LIST_OF_SEATS));
	}

	@Test
	public void isValidSelectedSeatForWithSeatsTicket_return_true_if_correct_ticket_is_demanded() {
		assertTrue(sectionWithSeat.isValidSelectedSeatsForWithSeatTickets(A_LIST_OF_SEATS));
	}
}
