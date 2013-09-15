package ca.ulaval.glo4003.domain;

import java.io.Serializable;

public class Sport implements Serializable {
	private String name;

	public Sport() {
		this.name = "";
	}

	public Sport(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
