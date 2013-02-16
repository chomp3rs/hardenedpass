import java.math.BigInteger;


public class Point {

	private BigInteger x;
	private BigInteger y;
	
	public Point(BigInteger xVal , BigInteger yVal)
	{
		x = xVal;
		y=  yVal;
	}
	public BigInteger getX() {
		return x;
	}
	public void setX(BigInteger x) {
		this.x = x;
	}
	public BigInteger getY() {
		return y;
	}
	public void setY(BigInteger y) {
		this.y = y;
	}
	
}
