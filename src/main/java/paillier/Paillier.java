package paillier;

/*  Copyright (c) 2009 Omar Hasan (omar dot hasan at insa-lyon dot fr)
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.math.BigInteger;
import java.util.Random;

public class Paillier {
    private final int CERTAINTY = 64; // certainty with which primes are
    // generated: 1-2^(-CERTAINTY)
    private int modLength; // length in bits of the modulus n
    private BigInteger p; // a random prime
    private BigInteger q; // a random prime (distinct from p)
    private BigInteger lambda; // lambda = lcm(p-1, q-1) = (p-1)*(q-1)/gcd(p-1,
    // q-1)
    private BigInteger n; // n = p*q
    private BigInteger nsquare; // nsquare = n*n
    private BigInteger g; // a random integer in Z*_{n^2}
    private BigInteger mu; // mu = (L(g^lambda mod n^2))^{-1} mod n, where L(u)

    // = (u-1)/n

    public Paillier(int modLengthIn) throws Exception {
	if (modLengthIn < 8)
	    throw new Exception(
		    "Paillier(int modLength): modLength must be >= 8");

	modLength = modLengthIn;

	generateKeys();
    }

    public void generateKeys() {
	p = new BigInteger(modLength / 2, CERTAINTY, new Random());
	do {
	    q = new BigInteger(modLength / 2, CERTAINTY, new Random());
	} while (q.compareTo(p) == 0);

	lambda = (p.subtract(BigInteger.ONE).multiply(q
		.subtract(BigInteger.ONE))).divide(p.subtract(BigInteger.ONE)
		.gcd(q.subtract(BigInteger.ONE)));

	n = p.multiply(q);
	nsquare = n.multiply(n);

	do {
	    g = randomZStarNSquare();
	} while (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n)
		.gcd(n).intValue() != 1);

	mu = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n)
		.modInverse(n);
    }

    public void printValues() {
	System.out.println("p:       " + p);
	System.out.println("q:       " + q);
	System.out.println("lambda:  " + lambda);
	System.out.println("n:       " + n);
	System.out.println("nsquare: " + nsquare);
	System.out.println("g:       " + g);
	System.out.println("mu:      " + mu);
    }

    public BigInteger randomZStarNSquare() {
	BigInteger r;

	do {
	    r = new BigInteger(modLength * 2, new Random());
	} while (r.compareTo(nsquare) >= 0 || r.gcd(nsquare).intValue() != 1);

	return r;
    }

}