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

	public static String encrypt(String data, byte[] key)
	{
		String strCipherText = new String();


		try{
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher aesCipher = Cipher.getInstance("AES");

			aesCipher.init(Cipher.ENCRYPT_MODE,secretKey);


			byte[] byteDataToEncrypt = data.getBytes();
			byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt); 
			strCipherText = new BASE64Encoder().encode(byteCipherText);
			
			
/*System.out.println("Helloooo");
			aesCipher.init(Cipher.DECRYPT_MODE,secretKey);
			byte[] byteDecryptedText = aesCipher.doFinal(byteCipherText);
			
			String strDecryptedText = new String(byteDecryptedText);
			System.out.println("Decrypted text :" + strDecryptedText);*/

		}

		catch (NoSuchAlgorithmException noSuchAlgo)
		{
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}

		catch (NoSuchPaddingException noSuchPad)
		{
			System.out.println(" No Such Padding exists " + noSuchPad);
		}

		catch (InvalidKeyException invalidKey)
		{
			System.out.println(" Invalid Key " + invalidKey);
		}

		catch (BadPaddingException badPadding)
		{
			System.out.println(" Bad Padding " + badPadding);
		}

		catch (IllegalBlockSizeException illegalBlockSize)
		{
			System.out.println(" Illegal Block Size " + illegalBlockSize);
		}



		return strCipherText;
	}

	public static String decrypt(String cipherText , byte[] key)
	{
		String strDecryptedText= new String();

		try{
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher aesCipher = Cipher.getInstance("AES");

			aesCipher.init(Cipher.DECRYPT_MODE,secretKey);
			byte[] byteDecryptedText = aesCipher.doFinal(new BASE64Decoder().decodeBuffer(cipherText));
			strDecryptedText = new String(byteDecryptedText);

		}

		catch (NoSuchAlgorithmException noSuchAlgo)
		{
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}

		catch (NoSuchPaddingException noSuchPad)
		{
			System.out.println(" No Such Padding exists " + noSuchPad);
		}

		catch (InvalidKeyException invalidKey)
		{
			System.out.println(" Invalid Key " + invalidKey);
		}

		catch (BadPaddingException badPadding)
		{
			System.out.println(" Bad Padding " + badPadding);
		}

		catch (IllegalBlockSizeException illegalBlockSize)
		{
			System.out.println(" Illegal Block Size " + illegalBlockSize);
		}

        catch( IOException io)
        {
        	System.out.println("IO Exception :" + io.getMessage());
        }

		return strDecryptedText;
	}




}