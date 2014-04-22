package jpeg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// methods for adding frames to a movie and saving 
public class Movie {
	// each byte array in this list represents a frame of the movie 
	List<ByteArrayOutputStream> baosList = new ArrayList<ByteArrayOutputStream>(); 
	String dirName = System.getProperty("user.dir");
	String fileName = dirName + "/data/";
	
	// movie constructor takes in a String for the fileName
	public Movie(String name) { 
		fileName += name; 
	}

	// save all the frames of the movie in one file 
	public void save() { 
		try {
			FileOutputStream fos = new FileOutputStream(new File(fileName));
		    // iterate through each baos in list and write to file
			for (ByteArrayOutputStream i: baosList) { 
				i.writeTo(fos);
				fos.write(System.getProperty("line.separator").getBytes()); 
			}
			fos.close();
		} catch(IOException ioe) {
		    // Handle exception here
		    ioe.printStackTrace();
		} 
		
	}
	
	// add a new image/frame to the movie
	public void addFrame(ByteArrayOutputStream newFrame) { 
		baosList.add(newFrame); 
	}
	
	// gets size of movie file in KB
	public double getSize() { 
		File file = new File(fileName); 
		long fileLen = file.length();
		double size = ((double) fileLen)/1000; 
		return size; 
	}
}
