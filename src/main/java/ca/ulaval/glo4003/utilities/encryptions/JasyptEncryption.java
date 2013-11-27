package ca.ulaval.glo4003.utilities.encryptions;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Component;

@Component
public class JasyptEncryption implements Encryption {

	StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
	
	@Override
	public String encrypt(String inputPassword) {
		return passwordEncryptor.encryptPassword(inputPassword);
	}

	@Override
	public Boolean isSamePassword(String inputPassword, String encryptedPassword) {
		return passwordEncryptor.checkPassword(inputPassword, encryptedPassword);
	}
}
