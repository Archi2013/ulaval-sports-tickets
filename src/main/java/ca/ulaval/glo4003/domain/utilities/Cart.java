package ca.ulaval.glo4003.domain.utilities;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;


@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cart {
	private GameDto gameDto;
	private SectionDto sectionDto;
	private Double cumulativePrice;

	public Boolean containTickets() {
		if (gameDto != null && sectionDto != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public GameDto getGameDto() {
		return gameDto;
	}
	
	public void setGameDto(GameDto gameDto) {
		this.gameDto = gameDto;
	}
	
	public SectionDto getSectionDto() {
		return sectionDto;
	}
	
	public void setSectionDto(SectionDto sectionDto) {
		this.sectionDto = sectionDto;
	}

	public Double getCumulativePrice() {
		return cumulativePrice;
	}

	public void setCumulativePrice(Double cumulativePrice) {
		this.cumulativePrice = cumulativePrice;
	}
}


