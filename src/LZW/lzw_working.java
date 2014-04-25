package LZW;
//referance: http://rosettacode.org/wiki/LZW_compression
//referance: http://opencsv.sourceforge.net/
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.KeyStore.Entry;
import java.util.*;

import au.com.bytecode.opencsv.CSVReader;

public class lzw_working {


	public static int dictSize_global = 256;
	public static Map<String,Integer> dictionary_global = new HashMap<String,Integer>();
	/** Compress a string to a list of output symbols. */
	public static List<Integer> compress(String uncompressed) {
		for (int i = 0; i < 256; i++)
			dictionary_global.put("" + (char)i, i);

		String w = "";
		List<Integer> result = new ArrayList<Integer>();
		for (char c : uncompressed.toCharArray()) {
			String wc = w + c;
			if (dictionary_global.containsKey(wc))
				w = wc;
			else {
				result.add(dictionary_global.get(w));
				// Add wc to the dictionary.
				dictionary_global.put(wc, dictSize_global++);
				w = "" + c;
			}
		}

		// Output the code for w.
		if (!w.equals(""))
			result.add(dictionary_global.get(w));
		return result;
	}

	/** Decompress a list of output ks to a string. */
	public static String decompress(List<Integer> compressed) {
		// Build the dictionary.
		int dictSize = 256;
		Map<Integer,String> dictionary = new HashMap<Integer,String>();
		for (int i = 0; i < 256; i++)
			dictionary.put(i, "" + (char)i);

		String w = "" + (char)(int)compressed.remove(0);
		StringBuffer result = new StringBuffer(w);
		for (int k : compressed) {
			String entry;
			if (dictionary.containsKey(k))
				entry = dictionary.get(k);
			else if (k == dictSize)
				entry = w + w.charAt(0);
			else
				throw new IllegalArgumentException("Bad compressed k: " + k);

			result.append(entry);
			// Add w+entry[0] to the dictionary.
			dictionary.put(dictSize++, w + entry.charAt(0));
			w = entry;
		}
		return result.toString();
	}

	public static String decompress_all(List<Integer> compressed) {
		StringBuffer result = new StringBuffer();
		for (int k : compressed) {
			for (java.util.Map.Entry<String, Integer> entry : dictionary_global.entrySet()) {
				if (entry.getValue().equals(k)) {
					//                System.out.println(entry.getKey());
					result.append(entry.getKey());
				}
			}
		}
		return result.toString();
	}

	public static void printHexFile(String filename) throws IOException{
	    FileInputStream in = new FileInputStream(filename);
	    int read;
	    while((read = in.read()) != -1){
	        System.out.print(Integer.toHexString(read) + "|");
	        System.out.print(read + "\t");
	    }
	    
//	    FileInputStream in2 = new FileInputStream(filename);
//	    int read2;
//	    System.out.println();
//	    while((read2 = in2.read()) != -1){
//	    System.out.print(read2 + " ");
//	    }
	}
	public void printBinaryFile(String filename) throws IOException{
	    FileInputStream in = new FileInputStream(filename);
	    int read;
	    while((read = in.read()) != -1){
	        System.out.print(Integer.toBinaryString(read) + "\t");
	    }
	}


	public static void main(String[] args) throws IOException {
		List<Integer> compressed = compress("999999999999999999999999999999999999999999999999999999999999");
		System.out.println(compressed);
		String decompressed = decompress_all(compressed);
		System.out.println(decompressed);
		System.out.println(dictionary_global);

		compressed = compress("888888888888888888888888888888888888888888888888888888888888888888");
		System.out.println(compressed);
		decompressed = decompress_all(compressed);
		System.out.println(decompressed);
		System.out.println(dictionary_global);

		
		printHexFile(System.getProperty("user.dir") + "/data/large/puredark.jpg");
		

		
//		String fileName = System.getProperty("user.dir") + "/data/redCompressed.csv";
//		String fileName = System.getProperty("user.dir") + "/data/movies/movie5.ser";
//		System.out.println("Getting raw RGBA components for " + fileName);
//		InputStream    fis;
//		BufferedReader br;
//		String         line;

		
		
//		CSV file reading and  counting
//		CSVReader reader = new CSVReader(new FileReader(fileName));
//	    String [] nextLine;
//	    while ((nextLine = reader.readNext()) != null) {
//	        // nextLine[] is an array of values from the line
//	    	if(nextLine.length > 5){
//	    		for(int j = 0; j<nextLine.length;j++) {
//	    			System.out.print(nextLine[j] + " ");
//	    	}
//	    		System.out.println();
//	    	
//	    	}
//	    }
	    
	    
	    
//	line by line compression
//	    
//		fis = new FileInputStream(fileName);
//		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
//		int i=0;
//		
//		int totalrawsize = 0;
//		int totalcompressedSize=0;
//		while ((line = br.readLine()) != null  && (i<100000000)) {
//			System.out.println("raw: "+line.toString());
//			i=i+1;
//			
//
//			
//			compressed = compress(line);
//			totalrawsize = totalrawsize + line.length()+1;
//			totalcompressedSize =totalcompressedSize + compressed.size() + 1;
//			
//			decompressed = decompress_all(compressed);
//			
//			
//			if(line.length()>5){
//				System.out.println("raw: "+line.toString());
//				System.out.println("totalrawsize: "+ totalrawsize);
//				System.out.println("totalcompressedSize: "+ totalcompressedSize);
//				System.out.println("compressed: "+compressed);
//				System.out.println("decompressed: "+decompressed);
////				System.out.println(dictionary_global);
//			}
//			
//
//		}
//		System.out.println(dictionary_global);
//		System.out.println("\n");
//		System.out.println("totalrawsize: "+ totalrawsize + "bytes (this is equal to the actual file size)");
//		System.out.println("totalcompressedSize: "+ totalcompressedSize+ "bytes");
//		System.out.println("compresion dictionary Size: "+ dictionary_global.toString().length() + "bytes (useing worst case saving method)");
//		System.out.println("lzw size total = totalcompressedSize + compresion dictionary Size = "+(totalcompressedSize+dictionary_global.toString().length()));
//		System.out.print ("estimate compression ratio (lzw size total / totalrawsize ):" + ((totalcompressedSize+dictionary_global.toString().length())*100 / totalrawsize) + "%");
//		
//		// Done with the file
//		br.close();
//		br = null;
//		fis = null;
//

		





	}
}