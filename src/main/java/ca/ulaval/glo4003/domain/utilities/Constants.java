package ca.ulaval.glo4003.domain.utilities;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.persistence.daos.SportDao;

@Component
public class Constants {
	
	public static final String LONG_DATE_TIME_FORMAT = "d MMMM yyyy à HH'h'mm z";
	public static final String CURRENCY = "CDN$";
	
	@Inject
	private SportDao sportDao;
	
	public static enum TicketKind {
		GENERAL_ADMISSION, WITH_SEAT;
		
	    public String toString() {
	        String name = "";
	        switch (ordinal()) {
	        case 0:
	            name = "admission générale";
	            break;
	        case 1:
	            name = "avec siège";
	            break;
	        default:
	            name = "Erreur";
	            break;
	        }
	        return name;
	    }
	}
	
	public static enum DisplayedPeriod {
		ONE_DAY, ONE_WEEK, ONE_MONTH, THREE_MONTH, SIX_MONTH, ALL;
		
	    public String toString() {
	        String name = "";
	        switch (ordinal()) {
	        case 0:
	            name = "un jour";
	            break;
	        case 1:
	            name = "une semaine";
	            break;
	        case 2:
	            name = "un mois";
	            break;
	        case 3:
	            name = "trois mois";
	            break;
	        case 4:
	            name = "six mois";
	            break;
	        case 5:
	            name = "tout";
	            break;
	        default:
	            name = "Erreur";
	            break;
	        }
	        return name;
	    }
	}
	
	public static enum CreditCardType {
		MISTERCARD, VASI, AMERICANEXPRESSO;
		
	    public String toString() {
	        String name = "";
	        switch (ordinal()) {
	        case 0:
	            name = "Mistercard";
	            break;
	        case 1:
	            name = "Vasi";
	            break;
	        case 2:
	        	name = "AmericanExpresso";
	        default:
	            name = "Erreur";
	            break;
	        }
	        return name;
	    }
	}
	
	public List<String> getSportList() {
		return sportDao.getAllSportNames();
	}
	
	public List<DisplayedPeriod> getDisplayedPeriods() {
		List<DisplayedPeriod> list = new ArrayList<>();
		for (DisplayedPeriod period : DisplayedPeriod.values()) {
			list.add(period);
		}
		return list;
	}
	
	public List<TicketKind> getTicketKinds() {
		List<TicketKind> ticketTypes = new ArrayList<>();
		ticketTypes.add(TicketKind.GENERAL_ADMISSION);
		ticketTypes.add(TicketKind.WITH_SEAT);
		return ticketTypes;
	}
}
