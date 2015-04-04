package ua.rsa.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import ua.rsa.impl.PreRSAImpl;

/**
 * This class will decrypt a message
 * 
 * @author amit_thatavarti
 * 
 */
public class RSADecryptionAlgorithm {

	public static void main(String[] args) {

		// call decryption method

		RSADecryptionAlgorithm obj = new RSADecryptionAlgorithm();

		obj.decryptionMethod();

	}

	/**
	 * This method will take encrypted message from user and gives decrypted as
	 * output
	 */
	public void decryptionMethod() {

		// Ask user to enter the private key
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Please enter the Private key");

		String privateKey = null;
		try {
			privateKey = br.readLine();
		} catch (IOException e) {

			e.printStackTrace();
		}

		// to check whether user has entered a valid value
		while (!(privateKey != null && !privateKey.isEmpty())) {
			System.out.println("please enter a valid value");
			try {
				privateKey = br.readLine();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		if (privateKey != null && !privateKey.isEmpty()) {

			System.out.println("please enter the value of n");
			String nString = null;
			try {
				nString = br.readLine();
			} catch (IOException e) {

				e.printStackTrace();
			}

			// to check whether user has entered a valid value
			while (!(nString != null && !nString.isEmpty())) {
				System.out.println("please enter a valid value");
				try {
					nString = br.readLine();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			System.out.println("Please enter block size");

			String size = null;

			try {
				size = br.readLine();
			} catch (IOException e) {

				e.printStackTrace();
			}

			// to check whether user has entered a valid value
			while (!(size != null && !size.isEmpty())) {
				System.out.println("please enter a valid value");
				try {
					size = br.readLine();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			System.out.println("Please enter encrypted Message");

			String encryptedMessage = null;

			try {
				encryptedMessage = br.readLine();
			} catch (IOException e) {

				e.printStackTrace();
			}

			// to check whether user has entered a valid value
			while (!(encryptedMessage != null && !encryptedMessage.isEmpty())) {
				System.out.println("please enter a valid value");
				try {
					encryptedMessage = br.readLine();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			PreRSAImpl preRsaObj = new PreRSAImpl();

			BigInteger d = new BigInteger(privateKey);
			BigInteger n = new BigInteger(nString);
			int blockSize = Integer.valueOf(size).intValue();

			String decryptedOriginalMessage = preRsaObj.decryptMessage(encryptedMessage, d, n, blockSize);

			System.out.println("Decrypted message :- " + decryptedOriginalMessage);

		}

	}

}
