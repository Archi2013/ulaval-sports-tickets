package ca.ulaval.glo4003.web.viewmodel;

public class SectionViewModel {
	
	public String admissionType;
	public String sectionName;
	public int numberOfTickets;

	public SectionViewModel(String admissionType, String sectionName, int numberOfTickets) {
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
