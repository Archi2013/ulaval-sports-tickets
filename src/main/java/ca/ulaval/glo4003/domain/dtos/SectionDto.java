package ca.ulaval.glo4003.domain.dtos;

public class SectionDto {

	private String admissionType;
	private String sectionName;
	private int numberOfTickets;
	private double price;

	public SectionDto(String admissionType, String sectionName, int numberOfTickets, double price) {
		this.admissionType = admissionType;
		this.sectionName = sectionName;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
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

	public double getPrice() {
		return price;
	}

	public boolean isGeneralAdmission() {
		// TODO Auto-generated method stub
		return true;
	}

}
