package ca.ulaval.glo4003.presentation.viewmodels;

public class SportViewModel {
	public String name;
	public String url;

	public SportViewModel(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

}
