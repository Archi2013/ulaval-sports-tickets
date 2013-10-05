package ca.ulaval.glo4003.domain.dtos;

public class SectionDto {

	private String admissionType;
	private String sectionName;

	private int numberOfTickets;

	public SectionDto(String admissionType, String sectionName, int numberOfTickets) {
		this.admissionType = admissionType;
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
	}

	public String getAdmissionType() {
		return admissionType;
	}

	public String getSectionName() {
		return sectionName;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

}
