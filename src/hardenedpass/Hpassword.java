import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.security.MessageDigest;
import java.security.SecureRandom; 
import java.security.SignatureException;

import sun.misc.BASE64Decoder;

public class Hpassword {


	public static void main(String args[])
	{
		Random randQ = new SecureRandom(); 
		BigInteger q = BigInteger.probablePrime(160, randQ);
		System.out.println("Value for q is :" + q);
		BigInteger hpwd = getHPassword(q);
		System.out.println("Value for hpwd :" + hpwd);

		// Testing
		String password ="gayathripmpmpm";

		int m = 5; // No of features
		BigInteger[] coeffArr = generateRandCoeffs(m);
		coeffArr[0] = hpwd;

		HashMap<Integer , ArrayList<Point>>  XYValuesMap = generateXYValues(coeffArr , m);

		Iterator iter = XYValuesMap.keySet().iterator();
		try {
		BufferedWriter bw = new BufferedWriter(new FileWriter("instructionTable.txt"));
		while(iter.hasNext()) {

			Integer i = (Integer)iter.next();

			ArrayList<Point> pointList = (ArrayList<Point>)XYValuesMap.get(i);

			    byte[] alphaData = BigInteger.valueOf(2 * i).toByteArray();
				BigInteger keyedHashValAlpha = (KeyedHash.calculateKeyedHash( alphaData, password)).mod(q);
				BigInteger alphaValue = (pointList.get(0).getY().multiply(keyedHashValAlpha)).mod(q);

				byte[] betaData = BigInteger.valueOf(2 * i + 1).toByteArray();
				BigInteger keyedHashValBeta = (KeyedHash.calculateKeyedHash( betaData, password)).mod(q);
				BigInteger betaValue = (pointList.get(1).getY().multiply(keyedHashValBeta)).mod(q);

				
				bw.write(i + "," + alphaValue + "," + betaValue );
				bw.newLine();
			

		}
		
		bw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Creation of history file
		int h = 20 ; // Num of max past logins
		int maxHistorySize = 3200; // 2000 Bytes
		String initialFeatureVals = "0,0,0,0,0";
		String fileContent = "";
		for(int i = 1; i < 20 ; i++ )
			fileContent += initialFeatureVals + "\n";

		try {
			BigInteger contentHashVal = KeyedHash.calculateKeyedHash(initialFeatureVals.getBytes(), password);
			String contentHashString = new String(contentHashVal.toByteArray());
			
			int mesgLength = fileContent.getBytes().length + contentHashString.getBytes().length;
			
			
			System.out.println("Content size is:" + mesgLength);
			
			int cipherLen = (mesgLength /16 + 1) * 16;
			
			int padLength = ((maxHistorySize) * (mesgLength)) / cipherLen - cipherLen;
			System.out.println("Padlength :" + padLength );
			for(int i = 0 ; i < padLength - 1 ; i++)
				fileContent += '#';
			
			fileContent += '\n'; 
			
			fileContent += contentHashString;
			System.out.println("File content length :" + fileContent.getBytes().length);
			System.out.println(fileContent);
			
			System.out.println("Padlength :" + padLength + "\n");
			System.out.println("Cipher Length :" + cipherLen );
			// Generate the AES 256 bit key
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(hpwd.toByteArray());
			byte[] aesKey = md.digest();
			System.out.println("AES Key Size:" + aesKey.length + aesKey.toString());
			
			String encryptedVal = AES.encrypt(fileContent, aesKey);
			System.out.println("Encrypted file content :" + encryptedVal + "\n Enc File Size :" + new BASE64Decoder().decodeBuffer(encryptedVal).length);
			
			// Output to history file
			PrintWriter out = new PrintWriter(new FileWriter("historyfile.txt"));
			out.print(encryptedVal);
			
			out.close();
			String decryptedVal = AES.decrypt(encryptedVal, aesKey);
			System.out.println("Decrypted String : " + decryptedVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}


	private static HashMap<Integer , ArrayList<Point>> generateXYValues(BigInteger[] coeffArr , int m)
	{
		HashMap<Integer , ArrayList<Point>> XYValuesMap = new HashMap<Integer , ArrayList<Point>>();

		for(int i = 1 ; i <= m ; i++)
		{
			Point firstVal = new Point( BigInteger.valueOf( 2 * i) , generateYVal(coeffArr , BigInteger.valueOf(2 * i)) );	
			Point secondVal = new Point( BigInteger.valueOf( 2 * i + 1) , generateYVal(coeffArr , BigInteger.valueOf(2 * i + 1)) );	

			ArrayList<Point> pointsList = new ArrayList<Point>();
			pointsList.add(firstVal);
			pointsList.add(secondVal);
			XYValuesMap.put( i, pointsList);
		}

		return XYValuesMap;
	}

	private static BigInteger generateYVal(BigInteger[] coeffArr , BigInteger xVal)
	{
		BigInteger yVal = BigInteger.ZERO;
		
		for(int j = 0 ; j < coeffArr.length ; j ++)
			yVal = yVal.add(coeffArr[j].multiply(xVal.pow(j)));

		return yVal;
	}
	private static BigInteger[] generateRandCoeffs(int m) {
		// TODO Auto-generated method stub
		BigInteger[] returnArr = new BigInteger[m];
		Random randP = new SecureRandom();
		for(int i =1 ; i < m ; i ++ )
		{
			returnArr[i] = BigInteger.valueOf(randP.nextInt(100)); // Max value set to 100
			
		}
		return returnArr;
	}


	public static BigInteger getHPassword(BigInteger qVal)
	{
		Random rnd = new Random();
		do {
			BigInteger i = new BigInteger(qVal.bitLength(), rnd);
			if (i.compareTo(qVal) <= 0)
				return i;
		} while (true);

	}
}
