package Engine;

import java.io.*;
/** This class defines all the general miscellaneous math functions
 *  Should never be instanciated
 */

/**
 * @author orpheon
 *
 */
public class Functions
{
	// Converts a signed double into a 16bit bytearray (for outputting as sound)
	public static byte[] convert_to_16bit_bytearray(double x)
	{
		// LITTLE ENDIAN!!!
		if (Math.abs(x) > 1)
		{
			System.out.println("ERROR: Too large x value given for byte converting!");
			System.out.println(x);
			byte[] error = {1};
			// FIXME: Use some python "raise" equivalent for stopping java
			// This will call out a runtime error, only way I can think of to stop Java
			System.out.println(error[4]);
			return error;
		}
		// First cast it to short and multiply it with the size of short to use all of those 16 bits
		short a = (short) (x * 32767);
//		System.out.println("\nUnscaled: "+x+"\nScaled: "+a);
		// Then convert that short into a byte array
		// Code origin: http://stackoverflow.com/questions/2188660/convert-short-to-byte-in-java
		byte[] ret = new byte[2];
		ret[0] = (byte)(a & 0xff);
		ret[1] = (byte)((a >> 8) & 0xff);

		return ret;
	}
}
