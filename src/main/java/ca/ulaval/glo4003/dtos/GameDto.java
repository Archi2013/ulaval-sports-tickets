package ca.ulaval.glo4003.dtos;

public class GameDto {
	public long id;
	
	public GameDto(long id){
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
