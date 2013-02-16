import java.io.BufferedWriter;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.security.SecureRandom; 
import java.security.SignatureException;

public class Hpassword {


	public static void main(String args[])
	{
		Random randQ = new SecureRandom(); 
		BigInteger q = BigInteger.probablePrime(160, randQ);
		System.out.println("Value for q is :" + q);
		BigInteger hpwd = getHPassword(q);
		System.out.println("Value for hpwd :" + hpwd);

		// Testing
		String password ="gayathri";

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

			
				BigInteger keyedHashValAlpha = (KeyedHash.calculateKeyedHash( 2 * i, password)).mod(q);
				BigInteger alphaValue = (pointList.get(0).getY().multiply(keyedHashValAlpha)).mod(q);

				BigInteger keyedHashValBeta = (KeyedHash.calculateKeyedHash( 2 * i + 1, password)).mod(q);
				BigInteger betaValue = (pointList.get(1).getY().multiply(keyedHashValBeta)).mod(q);

				
				bw.write(i + "," + alphaValue + "," + betaValue );
				bw.newLine();
			

		}
		
		bw.close();
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
		System.out.println("Coeff Array Length :" + coeffArr.length);
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
			System.out.println("Coeff " + i + "::" + returnArr[i]);
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
