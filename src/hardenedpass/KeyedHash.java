package hardenedpass;

import java.math.BigInteger;
import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class KeyedHash {

	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	public static BigInteger calculateKeyedHash(byte[] data, String key)
			throws java.security.SignatureException
			{
				BigInteger result;
				try {
					SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
					Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
					mac.init(signingKey);
					     
				     
					byte[] rawHmac = mac.doFinal(data);
					result = new BigInteger(1 , rawHmac);
		
		
				} catch (Exception e) {
					throw new SignatureException("Error in generating Keyed Hash" + e.getMessage());
				}
				return result;
			}
}
