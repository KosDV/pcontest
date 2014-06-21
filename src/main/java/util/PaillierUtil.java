package util;

import java.math.BigInteger;
import java.util.Random;

import servers.conf.ServerConfigurator;

/**
 * @References: http://www.csee.umbc.edu/~kunliu1/research/Paillier.html
 *              http://liris.cnrs.fr/~ohasan/pprs/paillierdemo/Paillier.java
 */
public class PaillierUtil {

    private BigInteger p = new BigInteger(ServerConfigurator.getP());
    private BigInteger q = new BigInteger(ServerConfigurator.getQ());
    private BigInteger lambda = new BigInteger(ServerConfigurator.getLambda());
    public BigInteger n = new BigInteger(ServerConfigurator.getN());
    public BigInteger nsquare = new BigInteger(ServerConfigurator.getNsquare());
    public BigInteger g = new BigInteger(ServerConfigurator.getG());
    private int bitLength = new Integer(ServerConfigurator.getBitlength());

    public PaillierUtil() {
    }

    public BigInteger Encryption(BigInteger m) {
	BigInteger r = new BigInteger(bitLength, new Random());
	return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
    }

    public BigInteger Decryption(BigInteger c) {
	BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE)
		.divide(n).modInverse(n);
	return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n)
		.multiply(u).mod(n);
    }
}
