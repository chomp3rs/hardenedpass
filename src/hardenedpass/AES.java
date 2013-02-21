package hardenedpass;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES {

	public static String encrypt(String data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		    String strCipherText = new String();

			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher aesCipher = Cipher.getInstance("AES");

			aesCipher.init(Cipher.ENCRYPT_MODE,secretKey);


			byte[] byteDataToEncrypt = data.getBytes();
			byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt); 
			strCipherText = new BASE64Encoder().encode(byteCipherText);
		
		    return strCipherText;
	}

	public static String decrypt(String cipherText , byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		    String strDecryptedText= new String();
        	SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.DECRYPT_MODE,secretKey);
			byte[] byteDecryptedText = aesCipher.doFinal(new BASE64Decoder().decodeBuffer(cipherText));
			strDecryptedText = new String(byteDecryptedText);
        	return strDecryptedText;
	}




}