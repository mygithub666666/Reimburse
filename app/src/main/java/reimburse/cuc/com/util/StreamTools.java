package reimburse.cuc.com.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {
	
	/**
	 * @param is
	 * @return
	 * @throws IOException 
	 */
	public static String readStream(InputStream is) throws IOException{
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		while(( len = is.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		is.close();
		String result = baos.toString("utf-8");//utf-8
		System.out.println(baos.toString("utf-8"));
		baos.close();
		return result;
	}
}
