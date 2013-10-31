package ca.ulaval.glo4003.domain.utilities;

public class Constants {
	
	public static enum AdmissionType {
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
		TODAY, ONE_WEEK, ONE_MONTH, THREE_MONTH, SIX_MONTH, ALL;
		
	    public String toString() {
	        String name = "";
	        switch (ordinal()) {
	        case 0:
	            name = "aujourd'hui";
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
}
