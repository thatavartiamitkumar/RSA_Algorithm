package ua.rsa.impl;

import java.math.BigInteger;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger p = new BigInteger("995244143");
		BigInteger q = new BigInteger("980418421");
		BigInteger pq = p.multiply(q);
		System.out.println("multpied valu" + pq);
		
	}

}
