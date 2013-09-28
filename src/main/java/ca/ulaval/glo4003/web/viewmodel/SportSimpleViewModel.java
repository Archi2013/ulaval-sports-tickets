package ca.ulaval.glo4003.web.viewmodel;


public class SportSimpleViewModel {
	public String name;
	public String url;

	public SportSimpleViewModel(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
