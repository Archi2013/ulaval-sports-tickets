package ca.ulaval.glo4003.web.viewmodel;

public class TicketViewModel {
	public Long id;
	public String price;
	public String admissionType;
	public String section;
	
	public TicketViewModel(Long id, String price,
			String admissionType, String section) {
		this.id = id;
		this.price = price;
		this.admissionType = admissionType;
		this.section = section;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAdmissionType() {
		return admissionType;
	}

	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
}
