package ca.ulaval.glo4003.dtos;

import java.io.Serializable;

public class SportDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String name;

	public SportDto() {
		this.name = "";
	}

	public SportDto(String name) {
		this.name = name;
	}
}
