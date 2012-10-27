/** This class defines all the general miscellanious math functions
 *  Should never be instanciated
 */

/**
 * @author orpheon
 *
 */
public class Functions
{
	public static double get_period(int frequency)
	{
		// 6.28318530718 = 2*pi
		return 6.28318530718/frequency;
	}
	
	public static byte[] convert_to_bytearray(double x)
	{
		if (Math.abs(x) > 1)
		{
			System.out.println("ERROR: Too large x value given for byte converting!");
			System.out.println(x);
			// FIXME: There has to be a better way to do this:
			byte[] error = {1};
			return error;
		}
		// First cast it to short and multiply it with the size of short to use all of those 16 bits
		short a = (short) (x * 32.767);
		// Then convert that short into a byte array
		// Code origin: http://stackoverflow.com/questions/2188660/convert-short-to-byte-in-java
		byte[] ret = new byte[2];
		ret[0] = (byte)(a & 0xff);
		ret[1] = (byte)((a >> 8) & 0xff);

		return ret;
	}
}
