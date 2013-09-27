package ca.ulaval.glo4003.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SportDto implements Serializable {
	private static final long serialVersionUID = 1L;

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
