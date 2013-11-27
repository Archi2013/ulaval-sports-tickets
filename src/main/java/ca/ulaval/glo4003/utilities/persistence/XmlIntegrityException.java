package ca.ulaval.glo4003.utilities.persistence;


public class XmlIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public XmlIntegrityException() {
		super("Integrity of the XML has been lost, please contact an administrator.");
	}
	
	public XmlIntegrityException(Throwable cause) {
		super("Integrity of the XML has been lost, please contact an administrator.", cause);
	}

	public XmlIntegrityException(String message, Throwable cause) {
		super(message, cause);
	}
}
