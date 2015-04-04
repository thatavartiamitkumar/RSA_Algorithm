package ua.rsa.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the core class where all the logic required to encrypt and decrypt a
 * message is written
 * 
 * @author amit_thatavarti
 * 
 */
public class PreRSAImpl {

	public static final String GCD = "d";
	public static final String X = "x";
	public static final String Y = "y";

	/**
	 * API to check for primality of a number
	 * 
	 * @param p
	 * @return
	 */
	public boolean primalityTest(BigInteger p) {

		// we need to find a^(p-1) = 1 mod p
		// for safer side we need to test the number with two different bases.

		// Variable to store modulus
		BigInteger pMod;

		// power which is (p-1)
		BigInteger power = p.subtract(new BigInteger("1"));

		// call the modulus method with base 11
		pMod = modulo(new BigInteger("11"), power, p);

		if (pMod.compareTo(new BigInteger("1")) == 0) {

			// call the modulus method with base 13
			pMod = modulo(new BigInteger("13"), power, p);
			if (pMod.compareTo(new BigInteger("1")) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method calculated a^(b-1) mod b value using Modular Exponentiation
	 * Algorithm
	 * 
	 * @param base
	 * @param p
	 * @return
	 */
	public BigInteger modulo(BigInteger base, BigInteger power,
			BigInteger divisor) {

		// variable to store final value
		BigInteger x = new BigInteger("1");

		if (power != null && base != null && divisor != null) {

			BigInteger b = power;

			BigInteger a = base;

			// Using Modular Exponentiation Algorithm
			while (b.compareTo(new BigInteger("0")) > 0) {

				if (!isEven(b)) {

					x = (x.multiply(a)).mod(divisor);

				}

				a = (a.multiply(a)).mod(divisor);

				// dividing the b by 2 and taking the integer value
				b = b.divide(new BigInteger("2"));

			}

		} else {
			return null;
		}
		return x;
	}

	/**
	 * This API will check whether the given number is even or not
	 * 
	 * @param b
	 * @return
	 */
	public boolean isEven(BigInteger b) {

		if ((b.mod(new BigInteger("2"))).compareTo(new BigInteger("0")) == 0) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * API to generate a prime number
	 * 
	 * @param digits
	 * @return
	 */
	public BigInteger primeNumberGenerator(int digits, BigInteger p) {

		boolean isPrime = false; // boolean value which determines the primality
									// of random number

		// stores random number
		BigInteger randNumber;
		BigInteger maxNumber = null;

		// generate n digit number
		randNumber = generateNDigitNumber(digits, p);

		// generate greatest number N digits, we need to find a n digit number
		// less than this number
		maxNumber = generateMaxNDigitNumber(digits);

		// while loop which will stop once we find a random number which is
		// prime

		while (!isPrime) {

			// check for primality
			isPrime = primalityTest(randNumber);

			if (!isPrime) {
				// incremet the random number by 2.
				randNumber = incrementRandomNumber(randNumber, maxNumber, 2);
			}

		}

		return randNumber;

	}

	/**
	 * This method will generate the max value of the N digit number, which is
	 * max value of the random number generated
	 * 
	 * @param digits
	 * @return
	 */
	public BigInteger generateMaxNDigitNumber(int digits) {

		BigInteger maxVal = new BigInteger("1");

		// creating a 10^digits number
		for (int i = 0; i < digits; i++) {

			maxVal = maxVal.multiply(new BigInteger("10"));

		}

		// subtract 1 to the max value
		maxVal = maxVal.subtract(new BigInteger("1"));

		return maxVal;
	}

	/**
	 * This method will generate a random N digit number
	 * 
	 * @param digits
	 * @return
	 */
	public BigInteger generateNDigitNumber(int digits, BigInteger p) {

		BigInteger returnVal = new BigInteger("1");

		// creating a 10^digits number
		for (int i = 0; i < (digits - 1); i++) {

			returnVal = returnVal.multiply(new BigInteger("10"));

		}

		if (p != null) {

			// checking if p and q are of same length or not
			if (p.toString().length() == digits) {

				// find value of q more than p
				returnVal = p.add(new BigInteger("2"));
			} else {
				// adding 1 to the return value
				returnVal = returnVal.add(new BigInteger("1"));
			}

		} else {

			// adding 1 to the return value
			returnVal = returnVal.add(new BigInteger("1"));

		}

		return returnVal;

	}

	/**
	 * This method will increment the random number by 2
	 * 
	 * @param randNumber
	 * @param maxNum
	 * @return
	 */
	public BigInteger incrementRandomNumber(BigInteger randNumber,
			BigInteger maxNum, int incrementNumberBy) {

		StringBuilder sb = new StringBuilder();

		// adding the value to add
		sb.append(incrementNumberBy);

		if (randNumber != null && maxNum != null
				&& (randNumber.compareTo(maxNum) < 0)) {
			randNumber = randNumber.add(new BigInteger(sb.toString()));
		} else {
			System.out
					.println("There is no prime number for the N digits which you have entered");
			return null;

		}

		return randNumber;

	}

	/**
	 * This API will calculate a number which has a gcd 1 with phiN
	 * 
	 * @param eLength
	 * @param phiN
	 * @return
	 */
	public BigInteger generatePublicKey(int eLength, BigInteger phiN) {

		// generate a random number with elength
		// check the GCD of the random number generated with phiN
		// if it is one that is the value of e
		// else take one more random number and continue.

		BigInteger e = generateNDigitNumber(eLength, null);

		BigInteger gcd = calculateGCD(phiN, e);

		BigInteger eMax = generateMaxNDigitNumber(eLength);

		if (gcd != null) {
			while (gcd.compareTo(new BigInteger("1")) != 0) {

				// increment the value of e by 1
				e = incrementRandomNumber(e, eMax, 1);

				// calculate the GCD with new e
				gcd = calculateGCD(phiN, e);
			}
		}

		return e;

	}

	/**
	 * Euclidean Algorithm This API uses euclidean algorithm to find the GCD of
	 * two given numbers
	 * 
	 * @param phiN
	 * @param e
	 * @return
	 */
	public BigInteger calculateGCD(BigInteger phiN, BigInteger e) {

		if (e != null && (e.compareTo(new BigInteger("0")) == 0)) {

			return phiN;

		}
		BigInteger mod = modulo(phiN, new BigInteger("1"), e);
		phiN = calculateGCD(e, mod);

		return phiN;
	}

	/**
	 * This API will find modular inverse value using EXTENDED EUCLIDEAN
	 * ALGORITHM
	 * 
	 * @param a
	 * @param b
	 * @param firstRun
	 * @return
	 */
	public Map<String, BigInteger> extendedEuclideanAlgorithm(BigInteger a,
			BigInteger b) {

		// St :- d= gcd(a,b)= ax + by
		// X is PhiN coefficient
		// Y is multiplicative inverse of e

		// Map to store the final values of gcd, d and y
		Map<String, BigInteger> returnMap = new HashMap<String, BigInteger>();

		// Map which is used to calculate gcd,d and y
		Map<String, BigInteger> tempMap = new HashMap<String, BigInteger>();

		if (a != null && b != null && (a.compareTo(new BigInteger("0")) != 0)) {

			if (b.compareTo(new BigInteger("0")) == 0) {
				returnMap.put(GCD, a);
				returnMap.put(X, new BigInteger("1"));
				returnMap.put(Y, new BigInteger("0"));

				return returnMap;
			}

			// this method is called recursively
			tempMap = extendedEuclideanAlgorithm(b,
					modulo(a, new BigInteger("1"), b));

			if (tempMap != null && !tempMap.isEmpty()) {

				returnMap.put(GCD, tempMap.get(GCD));
				returnMap.put(X, tempMap.get(Y));
				BigInteger y2 = (a.divide(b)).multiply(tempMap.get(Y));
				BigInteger y1 = tempMap.get(X).subtract(y2);
				returnMap.put(Y, y1);

			}
		}
		return returnMap;

	}

	/**
	 * This API encrypt a given message with the given block size
	 * 
	 * @param n
	 * @param e
	 * @return
	 * @throws IOException
	 */
	public String encryptMessage(BigInteger n, BigInteger e) throws IOException {

		// final encrypted message is stored in this which is further
		// modified to BigInteger
		StringBuffer encryptedMessage = new StringBuffer();

		// Ask user to enter the Message
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter Message to encrypt");

		// read line
		String userEnteredMessage = br.readLine();

		// to check whether user has entered a valid value
		while (!(userEnteredMessage != null && !userEnteredMessage.isEmpty())) {
			System.out.println("please enter a valid value");
			userEnteredMessage = br.readLine();
		}

		if (userEnteredMessage != null && !userEnteredMessage.isEmpty()) {

			// Prompt user to enter the block size
			System.out.println("Enter the block size");
			String userEnteredBlockSize = br.readLine();

			while (!(userEnteredBlockSize != null && !userEnteredBlockSize
					.isEmpty())) {
				System.out.println("please enter a valid value");
				userEnteredBlockSize = br.readLine();
			}

			Integer blockSizeInt = new Integer(userEnteredBlockSize);
			int blockSize = blockSizeInt.intValue();

			int startSubString = 0;
			int endSubString = 0;

			// for each substring we will find the integer value m1,m2,m3
			for (int i = 0; i < userEnteredMessage.length(); i += blockSize) {

				endSubString += blockSize;
				String subString;

				if (endSubString <= userEnteredMessage.length()) {

					// taking the sub string from the user entered message
					subString = userEnteredMessage.substring(startSubString,
							endSubString);
				} else {
					// taking the sub string from the user entered message
					subString = userEnteredMessage.substring(startSubString);

				}

				// adding the blocksize to start

				startSubString += blockSize;
				// counter for while loop
				int y = 0;

				BigInteger intSubMessage;

				// each char is converted to its ascii value and stored in this
				// buffer
				StringBuffer asciiBinaryBuffer = new StringBuffer();

				// This while loop will find ascii value of each character and
				// appened to ascii buffer
				while (y < subString.length()) {

					// taking the ascii value
					int ascii = subString.charAt(y);

					// convert the ascii into binary
					String asciiBinary = decimalToBinary(
							new BigInteger(Integer.toString(ascii)), 1);

					// appened the ascii to the buffer
					asciiBinaryBuffer.append(asciiBinary);
					// increment the counter
					y++;
				}

				// ascii buffer contains concatenated binarynumber of the block
				// convert this concatenated binary number to Integer

				BigInteger decimalValue = binaryToDecimal(asciiBinaryBuffer);

				// find the modulo and assign the value
				intSubMessage = modulo(decimalValue, e, n);

				// Append this sub message to the message
				encryptedMessage.append(intSubMessage).append(" ");
			}

		}

		return encryptedMessage.toString();

	}

	/**
	 * This API will decrypt the message and return the original message
	 * 
	 * @param decryptedNumber
	 * @param d
	 * @param n
	 * @param blockSize
	 * @return
	 */
	public String decryptMessage(String decryptedNumber, BigInteger d,
			BigInteger n, int blockSize) {

		StringBuffer originalMessage = new StringBuffer();

		if (decryptedNumber != null) {
			String[] message = decryptedNumber.split(" ");

			if (message != null && message.length != 0) {

				for (String iterator : message) {

					// find C^d mod n

					BigInteger c = new BigInteger(iterator);

					BigInteger m = modulo(c, d, n);

					// convert the decimal to binary

					String decodedMessageNumber = decimalToBinary(m, blockSize);

					// Spilt the string into block size

					int startString = 0;
					int stopString = 0;

					for (int i = 0; i < blockSize; i++) {

						stopString += 8;

						String binarySubString = decodedMessageNumber
								.substring(startString, stopString);

						// convert the binary string to decimal which will be
						// the ascii value

						BigInteger asciiValue = binaryToDecimal(new StringBuffer(
								binarySubString));

						if (asciiValue.compareTo(new BigInteger("0")) == 0) {
							startString += 8;
							continue;

						}

						char originalChar = (char) asciiValue.intValue();

						// appened the originalChar to message

						originalMessage.append(originalChar);

						startString += 8;

					}

				}

			}

		}
		return originalMessage.toString();
	}

	/**
	 * This API will convert decimal number to binary number This API is used
	 * while encryption and decryption
	 * 
	 * @param decimalNumber
	 * @param blockSize
	 * @return
	 */
	public String decimalToBinary(BigInteger decimalNumber, int blockSize) {

		StringBuffer binaryValue = new StringBuffer();
		String binaryString;

		while (decimalNumber.compareTo(new BigInteger("0")) != 0) {
			if (decimalNumber.mod(new BigInteger("2")).compareTo(
					new BigInteger("1")) == 0) {
				binaryValue.append(1);
			} else {
				binaryValue.append(0);
			}

			decimalNumber = decimalNumber.divide(new BigInteger("2"));
		}

		int lengthOfBinaryNum = binaryValue.toString().length();

		int maxLength = blockSize * 8;

		if (lengthOfBinaryNum < maxLength) {

			int numberOfZerosToAppened = maxLength - lengthOfBinaryNum;

			for (int i = 0; i < numberOfZerosToAppened; i++) {

				binaryValue.append("0");
			}

		}

		binaryString = binaryValue.reverse().toString();

		return binaryString;
	}

	/**
	 * This API is used to convert binary value to decimal.
	 * 
	 * @param binaryValue
	 * @return
	 */
	public BigInteger binaryToDecimal(StringBuffer binaryValue) {

		int lengthOfBinaryNumber = binaryValue.length();

		// initialzing it to 0
		BigInteger binaryIntVal = new BigInteger("0");

		// base integer
		BigInteger base = new BigInteger("2");

		for (int i = 0; i <= (lengthOfBinaryNumber - 1); i++) {

			if (binaryValue.toString().charAt(i) == '1') {

				int power = lengthOfBinaryNumber - 1 - i;

				binaryIntVal = binaryIntVal.add(base.pow(power));
			}

		}

		return binaryIntVal;
	}
}
