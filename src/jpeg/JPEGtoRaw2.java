//http://stackoverflow.com/questions/10923842/can-i-change-the-compression-algorithm-used-by-javas-imagewriter-when-creating
/*taken from: http://www.programcreek.com/2009/02/java-convert-image-to-byte-array-convert-byte-array-to-image/ for learning purposes*/
//http://examples.javacodegeeks.com/desktop-java/imageio/compress-a-jpeg-file/
package jpeg;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
 
 
public class JPEGtoRaw2 {
	 
	// list of JPEGS that will serve as the frames
	public static List<String> frames = new ArrayList<String>(); 
	public static Movie m = new Movie("movieTest");
	public static String dirName = System.getProperty("user.dir");
	public static double size = 0; 
	
	
	public static void main(String[] args) throws IOException{
		
//		// String dirName = System.getProperty("user.dir");
//		String fileName = dirName + "/data/test.jpeg";
//		System.out.println(fileName);
//		
//		File origFile = new File(fileName); 
//		long origLength = origFile.length(); 
//		System.out.printf("Original file test.jpeg was %d KB \n", origLength/1000);
//
//		// testCompression(fileName);
		
		String fileName = dirName + "/data/"; 
		frames.add(fileName + "one.jpg"); 
		frames.add(fileName + "two.jpg"); 
		frames.add(fileName + "three.jpg"); 
		frames.add(fileName + "four.jpg"); 
		frames.add(fileName + "five.jpg"); 
		frames.add(fileName + "six.jpg"); 
		
		for (String s: frames){ 
			File file = new File(s); 
			double fileLen = ((double) file.length())/1000; 
			size += fileLen; 
			generalCompression(s);
		}
		 
		m.save(); 
		System.out.printf("Size of all original images is %f KB \n", size); 
		System.out.printf("Size of the movie file is %f KB \n", m.getSize()); 
		
	}
	
	public static void testCompression(String fileName) throws IOException{
		File imageFile = new File(fileName);
		File compressedImageFile = new File(fileName + "_out");

		InputStream is = new FileInputStream(imageFile);
		OutputStream os = new FileOutputStream(compressedImageFile);

		// A quality of .86 gets you the same size back
		// A quality of 1.0 cause the file to grow in size 
		float quality = .859f;
		
		// create a BufferedImage as the result of decoding the supplied InputStream
		BufferedImage image = ImageIO.read(is);

		// get all image writers for JPG format
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

		if (!writers.hasNext())
			throw new IllegalStateException("No writers found");

		ImageWriter writer = (ImageWriter) writers.next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);
		

		ImageWriteParam param = writer.getDefaultWriteParam();

		// compress to a given quality
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);

		// appends a complete image stream containing a single image and
		//associated stream and image metadata and thumbnails to the output
		writer.write(null, new IIOImage(image, null, null), param);

		// close all streams
		is.close();
		os.close();
		ios.close();
		writer.dispose();
		
		long newLength = compressedImageFile.length(); 
		System.out.printf("Original file test_out.jpeg was %d KB \n", newLength/1000);


}
		
	
	
	public static void generalCompression(String fileName) throws IOException{
		
		// String dirName = System.getProperty("user.dir");
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
		
		BufferedImage img=ImageIO.read(new File(fileName));		
		
		
		System.out.println("IMG: " + img.getHeight() + "x" + img.getWidth());
		
		ImageIO.write(img, "jpg", baos);
		 
		m.addFrame(baos);
		
		
//		System.out.println("Number of Bytes In image: " + baos.size());
		baos.flush();
// 
//		String base64String=Base64.encode(baos.toByteArray());
//		System.out.println("String: " + base64String.length());
//		baos.close();
// 
//		byte[] bytearray = Base64.decode(base64String);
//		System.out.println("BYTEARRAY: " + bytearray.length);
//		BufferedImage imag=ImageIO.read(new ByteArrayInputStream(bytearray));
//		System.out.println("IMAG: " + imag.getHeight() + "x" + imag.getWidth());
//		ImageIO.write(imag, "jpg", new File(dirName+"/data/","test2.jpeg"));
	}
}
