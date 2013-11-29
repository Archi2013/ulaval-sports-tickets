package ca.ulaval.glo4003.domain.cart;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.utilities.Calculator;

@Component
public class SectionForCart {
	private String sportName;
	private DateTime gameDate;
	private String sectionName;
	private String opponents;
	private String location;
	private Boolean generalAdmission;
	private Integer numberOfTicketsToBuy;
	private Set<String> selectedSeats;
	private Double price;
	
	@Inject
	private Calculator calculator;
	
	public SectionForCart(String sportName, DateTime gameDate, String sectionName,
			String opponents, String location,
			Integer numberOfTicketsToBuy, Double price) {
		super();
		this.numberOfTicketsToBuy = numberOfTicketsToBuy;
		this.selectedSeats = new HashSet<String>();
		this.generalAdmission = true;
		this.sectionName = sectionName;
		this.gameDate = gameDate;
		this.opponents = opponents;
		this.location = location;
		this.sportName = sportName;
		this.price = price;
	}
	
	public SectionForCart(String sportName, DateTime gameDate, String sectionName,
			String opponents, String location,
			Set<String> selectedSeats, Double price) {
		super();
		this.numberOfTicketsToBuy = selectedSeats.size();
		this.selectedSeats = selectedSeats;
		if (this.selectedSeats == null) {
			this.selectedSeats = new HashSet<String>();
		}
		this.generalAdmission = false;
		this.sectionName = sectionName;
		this.gameDate = gameDate;
		this.opponents = opponents;
		this.location = location;
		this.sportName = sportName;
		this.price = price;
	}
	
	public void addElements(SectionForCart sectionForCart) {
		if (getGeneralAdmission()) {
			this.numberOfTicketsToBuy += sectionForCart.getNumberOfTicketsToBuy();
		} else {
			this.selectedSeats.addAll(sectionForCart.getSelectedSeats());
		}
	}
	
	@Override
    public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if ( !(obj instanceof SectionForCart) ) {
			return false;
		}
        
		SectionForCart other = (SectionForCart) obj;
 
        return new EqualsBuilder().append(this.sportName, other.sportName)
        		.append(this.gameDate, other.gameDate)
        		.append(this.sectionName, other.sectionName).build();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.sportName)
				.append(this.gameDate).append(this.sectionName).toHashCode();
	}
	
	public Boolean getGeneralAdmission() {
		return generalAdmission;
	}

	public String getSportName() {
		return sportName;
	}

	public DateTime getGameDate() {
		return gameDate;
	}

	public String getSectionName() {
		return sectionName;
	}

	public String getOpponents() {
		return opponents;
	}

	public String getLocation() {
		return location;
	}

	public Integer getNumberOfTicketsToBuy() {
		return numberOfTicketsToBuy;
	}

	public Set<String> getSelectedSeats() {
		return selectedSeats;
	}

	public Double getPrice() {
		return price;
	}

	public Double getSubtotal() {
		if (getGeneralAdmission()) {
			return calculator
					.calculateCumulativePriceForGeneralAdmission(
							this.numberOfTicketsToBuy,
							this.price);
		} else {
			return calculator
					.calculateCumulativePriceForWithSeatAdmission(
							this.selectedSeats,
							this.price);
		}
	}
}
