package ca.ulaval.glo4003.persistence.daos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}

		TicketType otherTicketType = (TicketType) obj;
		return new EqualsBuilder().append(this.admissionType, otherTicketType.admissionType)
				.append(this.sectionName, otherTicketType.sectionName).build();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(admissionType).append(sectionName).toHashCode();
	}
}
