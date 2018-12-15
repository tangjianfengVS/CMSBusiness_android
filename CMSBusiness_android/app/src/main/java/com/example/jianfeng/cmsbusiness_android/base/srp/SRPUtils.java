package com.example.jianfeng.cmsbusiness_android.base.srp;


import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by wxj on 2015/2/28.
 */
public class SRPUtils {
    static void 		validateConstants(BigInteger N, BigInteger g) {
        // Developed from "SRP JavaScript Demo" from http://srp.stanford.edu<p>

        if ( !N.isProbablePrime(10) ) {
            throw new IllegalArgumentException("isProbablePrime(10) failed for " + N.toString(16));
        }

        BigInteger 		n_minus_one_div_2 = N.subtract(BigInteger.ONE).divide(TWO);

        if ( !n_minus_one_div_2.isProbablePrime(10) ) {
            throw new IllegalArgumentException("(N-1)/2 is not prime for " + N.toString(16));
        }

        if( g.modPow(n_minus_one_div_2, N).add(BigInteger.ONE).compareTo(N) != 0) {
            throw new IllegalArgumentException("Not a primitive root: " + g.toString(16));
        }
    }


    static BigInteger		hash(String s)
    {
        return new BigInteger(hashToBytes(s), 16);
    }

    static String		hashToBytes(String s)
    {
        try
        {

            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] 				b = s.getBytes("utf-8");
            sha.update(b, 0, b.length);
            char[] output = Hex.encodeHex(sha.digest());
            //String output = new String(Hex.encodeHex(sha.digest()));
            //return sha.digest();
            return String.valueOf(output);
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        }

        return null;

    }

    static BigInteger		hash(BigInteger i)
    {
        return new BigInteger(hashToBytes(i));
    }

    static byte[]		hashToBytes(BigInteger i)
    {
        try
        {
            MessageDigest		sha = MessageDigest.getInstance("SHA-256");
            byte[] 				b = i.toByteArray();
            sha.update(b, 0, b.length);
            return sha.digest();
        }
        catch ( NoSuchAlgorithmException e )
        {
            throw new UnsupportedOperationException(e);
        }
    }

    static BigInteger		random(SRPConstants fConstants)
    {
        int 		numberOfBytes = (fConstants.N.bitLength() + (fConstants.N.bitLength() - 1)) / 8;
        byte[]		b = new byte[numberOfBytes];
        fRandom.nextBytes(b);
        BigInteger	i = new BigInteger(b);

        // random numbers must be: 1 < r < n
        BigInteger	max = fConstants.N.subtract(TWO);
        return i.mod(max).add(TWO);
    }

    static BigInteger makePrivateKey(byte[] username, byte[] password, byte[] salt)
    {
        // H(s, H (I + ':' + p))
        String H1 = hashToBytes(hashToBytes(new String(username) + ":" + new String(password)) + ":" + new String(salt));
        return new BigInteger(H1, 16);

    }

    static BigInteger 		calc_u(BigInteger A, BigInteger B)
    {
        return hash(new String(A.toByteArray()) + ":" + new String(B.toByteArray()));
    }




    private static final BigInteger 		TWO = BigInteger.valueOf(2);

    private static final SecureRandom fRandom = new SecureRandom();




    public static BigInteger calcM1(SRPConstants fConstants, byte[] fUsername,
                                    BigInteger fPublicKey_A, BigInteger publicKey_B,
                                    BigInteger fCommonValue_K, BigInteger fsalt) {
        // TODO Auto-generated method stub
        //H(H(N) ^ H(g), H(I), s, A, B, K_c)
        String HNG = new String((hash(fConstants.N.toString(16)).xor(hash(fConstants.g))).toByteArray());
        String HU = hashToBytes(new String(fUsername));
        String Hs = new String(fsalt.toByteArray());
        String HA = new String(fPublicKey_A.toByteArray());
        String HB = new String(publicKey_B.toByteArray());
        String HK = new String(fCommonValue_K.toByteArray());
        return hash(HNG + ":" + HU + ":" + Hs + ":" + HA + ":" + HB + ":" + HK);
    }

}
