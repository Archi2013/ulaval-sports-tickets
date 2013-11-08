package ca.ulaval.glo4003.domain.utilities.user;

import org.springframework.stereotype.Component;

@Component
public interface Encryption {
	public String encrypt(String inputPassword);
	public Boolean isSamePassword(String inputPassword, String encryptedPassword);
}
