package ca.ulaval.glo4003.web.viewmodel;

public class TicketViewModel {
	public Long id;
	public String price;
	public String admissionType;
	public String section;
	public String opponents;
	public String date;

	public TicketViewModel(Long id, String price, String admissionType,
			String section, String opponents, String date) {
		super();
		this.id = id;
		this.price = price;
		this.admissionType = admissionType;
		this.section = section;
		this.opponents = opponents;
		this.date = date;
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

	public String getOpponents() {
		return opponents;
	}

	public void setOpponents(String opponents) {
		this.opponents = opponents;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
