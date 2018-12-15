package com.example.jianfeng.cmsbusiness_android.base.srp;

import java.math.BigInteger;

/**
 * Created by wxj on 2015/2/28.
 */
public class SRPClientSession {

    public SRPClientSession(SRPConstants constants, byte[] password, byte[] username) {
        fConstants = constants;
        p = password;
        I = username;
    }

    public void setSalt_s(BigInteger salt) throws SRPAuthenticationFailedException {
        //x = H(s, H (I + ':' + p))
        if (salt == null) {
            throw new SRPAuthenticationFailedException("Salt cannot be null!");
        }
        s = salt;
        //System.out.println("s:" + s.toString(16));
        a = SRPUtils.random(fConstants);
        //System.out.println("a:" + a.toString(16));
        A = fConstants.g.modPow(a, fConstants.N);
        //System.out.println("A:" + A.toString(16));
        //System.out.println(new String(p));
        //System.out.println("LL:" + (SRPUtils.hash(new String(I) + ":" + new String(p)).toString(16)));
        x = SRPUtils.hash(s.toString(10) + ":" + (SRPUtils.hash(new String(I) + ":" + new String(p))).toString(10));

        //System.out.println("x:" + x.toString(16));
    }


    public void setServerPublicKey_B(BigInteger publicKey_B) throws SRPAuthenticationFailedException {
        if (A == null) {
            throw new SRPAuthenticationFailedException("Salt value should be set.");
        }

        if (publicKey_B.mod(fConstants.N).equals(BigInteger.ZERO)) {
            throw new SRPAuthenticationFailedException("Wrong B value");
        }
        B = publicKey_B;
        u = SRPUtils.hash(A.toString(10) + ":" + B.toString(10));
        //System.out.println("u:" + u.toString(16));
        //System.out.println("B:" + B.toString(16));
        //pow(B - k * pow(g, x, N), a + u * x, N)
        BigInteger p1 = B.subtract(fConstants.k.multiply(fConstants.g.modPow(x, fConstants.N)));
        BigInteger p2 = a.add(u.multiply(x));

        S_c = p1.modPow(p2, fConstants.N);
        //System.out.println("S_c:" + S_c.toString(16));
        K_c = SRPUtils.hash(S_c.toString(10));
        //System.out.println("K_c:" + K_c.toString(16));
        //M_c = H(H(N) ^ H(g), H(I), s, A, B, K_c)
        p1 = SRPUtils.hash(fConstants.N.toString(10)).xor(SRPUtils.hash(fConstants.g.toString(10)));


        String r = p1.toString(10) + ":" + SRPUtils.hash(new String(I)).toString(10) + ":" + s.toString(10) +
                ":" + A.toString(10) + ":" + B.toString(10) + ":" + K_c.toString(10);
        M_c = SRPUtils.hash(r);
    }


    public BigInteger getEvidenceValue_M1() {
        return M_c;
    }

    public boolean isValidateServerEvidenceValue_M2(BigInteger evidenceValueFromServer_M2) throws SRPAuthenticationFailedException {
        //M_s = H(A, M_c, K_s)
        BigInteger M_s = SRPUtils.hash(A.toString(10) + ":" + M_c.toString(10) + ":" + K_c.toString(10));
        //System.out.println("M_s:" + M_s.toString(16));
        if (!M_s.equals(evidenceValueFromServer_M2)) {
            throw new SRPAuthenticationFailedException("Wrong M2");
        } else {
            return true;
        }
    }

    public BigInteger getA() {
        return A;
    }

    SRPConstants getConstants() {
        return fConstants;
    }

    private byte[] I; //username
    private byte[] p;
    private BigInteger s;
    private BigInteger A;
    private BigInteger B;
    private BigInteger S_c;
    private BigInteger K_c;
    private SRPConstants fConstants;
    private BigInteger u;
    private BigInteger x;
    private BigInteger a;
    private BigInteger M_c;
}
