package ca.ulaval.glo4003.web.viewmodels;

public class SportViewModel {
	public String name;
	public String url;

	public SportViewModel(String name, String url) {
		this.name = name;
		this.url = url;
	}

	// Used for jsps.
	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

}
