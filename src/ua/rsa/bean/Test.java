package ua.rsa.bean;

import java.io.IOException;
import java.math.BigInteger;

public class Test {

	public static void main(String[] args) throws IOException {
		Test test = new Test();
		BigInteger result = test.modulo(new BigInteger(String.valueOf(1024)),new BigInteger(String.valueOf(17)),new BigInteger(String.valueOf(3337)));
		System.out.println(result);

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

}
