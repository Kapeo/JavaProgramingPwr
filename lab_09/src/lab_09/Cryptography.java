package lab_09;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Cryptography {
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private Cipher cipher;
	static PublicKey publicKey;
	static String algorithm;
	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	static PrivateKey privateKey;
	static Key key;
	
	public Cryptography(String algorithm)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		this.algorithm = algorithm;
		this.keyGen = KeyPairGenerator.getInstance(algorithm);
		this.keyGen.initialize(1024);
		this.pair = this.keyGen.generateKeyPair();
		this.publicKey = pair.getPublic();
		this.privateKey = pair.getPrivate();
		this.cipher = Cipher.getInstance(algorithm);
	}
	
	public static Key getKey(int index){
		if (index == 0) key=privateKey;
		if (index == 1) key=publicKey;
		return key;
	}
	
	public static String encrypt(String plainText, int index) throws Exception {
	    Cipher encryptCipher = Cipher.getInstance(algorithm);
	    encryptCipher.init(Cipher.ENCRYPT_MODE, getKey(index));

	    byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

	    return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String decrypt(String cipherText, int index) throws Exception {
	    byte[] bytes = Base64.getDecoder().decode(cipherText);

	    Cipher decriptCipher = Cipher.getInstance(algorithm);
	    decriptCipher.init(Cipher.DECRYPT_MODE, getKey(index));

	    return new String(decriptCipher.doFinal(bytes), UTF_8);
	}


}
