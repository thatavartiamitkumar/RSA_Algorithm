/**
 * 
 */
package ua.rsa.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Map;

import ua.rsa.impl.PreRSAImpl;

/**
 * This class will encrypt a given message
 * 
 * @author Amit
 * 
 */
public class RSAEncryptionAlgorithmBean {

	public static final String GCD = "d";
	public static final String X = "x";
	public static final String Y = "y";

	// declare variables
	int numberOfDigitsP;
	int numberOfDigitsQ;
	BigInteger p;
	BigInteger q;
	BigInteger e;
	private BigInteger d;

	public void encryptionMethod() throws IOException {

		BigInteger n = null;
		BigInteger phiN = null;

		PreRSAImpl preRSAObject = new PreRSAImpl();

		// generate p
		p = preRSAObject.primeNumberGenerator(numberOfDigitsP, null);

		System.out.println("prime number P: " + p);

		// generate q
		q = preRSAObject.primeNumberGenerator(numberOfDigitsQ, p);

		System.out.println("prime number q: " + q);

		// find out n and Phi of N
		if (p != null && q != null) {
			n = p.multiply(q);
			phiN = (p.subtract(new BigInteger("1"))).multiply(q
					.subtract(new BigInteger("1")));

			System.out.println("value of n is : " + n);
			System.out.println("value of phiN is: " + phiN);

		} else {
			System.out.println("There are no prime numbers");
		}

		// find out length of phiN
		String phiNString = phiN.toString();
		int phiNLength = phiNString.length();

		// Ask user to enter the digits of e
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the length of e between 0 and " + phiNLength
				+ "digits");

		String userEnteredLength = br.readLine();

		// to check whether user has entered a valid value
		while (!(userEnteredLength != null && !userEnteredLength.isEmpty())) {
			System.out.println("please enter a valid value");
			userEnteredLength = br.readLine();
		}

		if (userEnteredLength != null && !userEnteredLength.isEmpty()) {
			Integer intLength = new Integer(userEnteredLength);
			int eLength = intLength.intValue();

			e = preRSAObject.generatePublicKey(eLength, phiN);

			System.out.println("value of generated e is " + e);

			// fine the value of multiplicative inverse of e

			Map<String, BigInteger> extendedEucledianMap = preRSAObject
					.extendedEuclideanAlgorithm(phiN, e);

			if (extendedEucledianMap != null && !extendedEucledianMap.isEmpty()) {

				d = extendedEucledianMap.get(Y);
				// if d is negative add phiN to the value
				if (d.compareTo(new BigInteger("0")) < 0) {
					d = phiN.add(d);
				}
				System.out.println("private Key value : " + d);
				System.out.println("Coefficent of PhiN is: "
						+ extendedEucledianMap.get(X));
				System.out.println("GCD of e and PhiN is :"
						+ extendedEucledianMap.get(GCD));

			}

		}

		// Encrypt the message
		String encryptedMessage = preRSAObject.encryptMessage(n, e);
		System.out.println("encrypted message : " + encryptedMessage);

	}

	public static void main(String args[]) throws IOException {
		RSAEncryptionAlgorithmBean bean = new RSAEncryptionAlgorithmBean();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter the length of prime number p");

		String pLength = br.readLine();

		while (!(pLength != null && !pLength.isEmpty())) {
			System.out.println("please enter a valid value");
			pLength = br.readLine();
		}

		if (pLength != null && !pLength.isEmpty()) {
			Integer intLength = new Integer(pLength);

			bean.numberOfDigitsP = intLength.intValue();

		}

		System.out.println("enter the length of prime number q");
		String qLength = br.readLine();

		while (!(qLength != null && !qLength.isEmpty())) {
			System.out.println("please enter a valid value");
			qLength = br.readLine();
		}

		if (qLength != null && !qLength.isEmpty()) {
			Integer intLength = new Integer(qLength);

			bean.numberOfDigitsQ = intLength.intValue();

		}

		bean.encryptionMethod();

	}

}
