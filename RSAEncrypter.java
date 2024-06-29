import java.math.BigInteger;
import java.util.Random;

public class RSAEncrypter {

    public static void main(String[] args){
        BigInteger p =  BigInteger.probablePrime(512, new Random());
        BigInteger q =  BigInteger.probablePrime(512, new Random());

        BigInteger n = p.multiply(q);

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = findE(phi);

        System.out.println("Chave pública (n,e) : (" + n + "," + e + ")");

        BigInteger d = findD(e, phi)[1];

        System.out.println("Chave privada (n,d) : (" + n + "," + d + ")");

        String teste = "teste";
        BigInteger stringConverted = convertString(teste);

        BigInteger encryptedMessage = encrypt(stringConverted, e, n);
        BigInteger decryptedMessage = decrypt(encryptedMessage, d, n);
        String message = convertBigInteger(decryptedMessage);

        System.out.println("Mensagem criptografada : " + encryptedMessage);
        System.out.println("Mensagem descriptografada :" + message);
    }

    public static BigInteger convertString(String string){
        byte[] inputStringBytes = string.getBytes();
        return new BigInteger(inputStringBytes);
    }

    public static String convertBigInteger(BigInteger bigInteger){
        return new String(bigInteger.toByteArray());
    }

    public static BigInteger findE(BigInteger phi){
        BigInteger e;
        do{
            e = new BigInteger(1024, new Random());
            while(e.min(phi).equals(phi)) e = new BigInteger(1024, new Random());

        } while (!gcd(e, phi).equals(BigInteger.ONE));

        return e;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b){
        if(b.equals(BigInteger.ZERO)) return a;
        else return gcd(b, a.mod(b));
    }

    public static BigInteger[] findD(BigInteger a, BigInteger  b){
        if(b.equals(BigInteger.ZERO)) return new BigInteger[] { a, BigInteger.ONE, BigInteger.ZERO };

        BigInteger[] vals = findD(b, a.mod(b));
        BigInteger d = vals[0];
        BigInteger p = vals[2];
        BigInteger q = vals[1].subtract(a.divide(b).multiply(vals[2]));

        return new BigInteger[] { d, p, q };
    }

    public static BigInteger encrypt(BigInteger string, BigInteger e, BigInteger n){
        return string.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger string, BigInteger d, BigInteger n){
        return string.modPow(d, n);
    }
}
