package ca.ulaval.glo4003.domain.utilities;

public class TicketType {

	public final String admissionType;
	public final String sectionName;

	public TicketType(String admissionType, String sectionName) {
		this.admissionType = admissionType;
		this.sectionName = sectionName;
	}

	public static TicketType fromString(String value) {
		String[] s = value.split(":");
		return new TicketType(s[0], s[1]);
	}
}
