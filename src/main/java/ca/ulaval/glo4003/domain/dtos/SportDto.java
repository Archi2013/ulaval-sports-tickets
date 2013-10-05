package ca.ulaval.glo4003.domain.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SportDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final int A_PRIME_NUMBER = 7;
	private static final int AN_OTHER_PRIME_NUMBER = 71;

	private String name;
	private List<GameDto> games;

	public SportDto() {
		this.name = "";
		this.games = new ArrayList<GameDto>();
	}

	public SportDto(String name) {
		this.name = name;
		this.games = new ArrayList<GameDto>();
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
        
		SportDto other = (SportDto) obj;
 
        return new EqualsBuilder().append(this.name, other.name).build();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(A_PRIME_NUMBER, AN_OTHER_PRIME_NUMBER).append(this.name).toHashCode();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GameDto> getGames() {
		return games;
	}
}
