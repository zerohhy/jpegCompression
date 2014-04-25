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

public class lzw_int {

	

	public static int dictSize_global = 256;
	public static Map<byte[],Integer> dictionary_global = new HashMap<byte[],Integer>();
	/** Compress a string to a list of output symbols. */
	
	
	static byte[] concatenateByteArrays(byte[] a, byte[] b) {
		System.out.print("a.legnth="+a.length+"   b.length= "+b.length+'\n');
	    byte[] r = new byte[a.length + b.length]; 
	    System.arraycopy(a, 0, r, 0, a.length); 
	    System.arraycopy(b, 0, r, a.length, b.length); 
	    System.out.print("result.legnth= "+r.length+'\n');
	    System.out.println(r.toString()+'\n');
	    return r;
	} 
	
	
	
	public static List<Integer> compress(byte[] uncompressed) {
		byte[] a = new byte[1];
		for (int i = 0; i < 256; i++){
			a[0]=(byte)i;
			dictionary_global.put(a , i);
		}
//		String w = "";
		byte[] w =new byte[1];
		byte[] cc = new byte[1];
		byte[] wc = new byte[4096];
		List<Integer> result = new ArrayList<Integer>();
		for (byte c : uncompressed) {
			cc[0]=c;
//			String wc = w + c;
			wc = concatenateByteArrays(w,cc);
			if (dictionary_global.containsKey(wc))
				w = wc;
			else {
				System.out.print(dictionary_global.get(w));
				result.add(dictionary_global.get(w));
				// Add wc to the dictionary.
				dictionary_global.put(wc, dictSize_global++);
				w =  cc;
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
		Map<Integer,byte[]> dictionary = new HashMap<Integer,byte[]>();
		byte[] a = new byte[1];
		for (int i = 0; i < 256; i++){
			a[0]=(byte)i;
			dictionary.put(i, a);
		}
		byte[] w = new byte[1];
		w[0] = compressed.remove(0).byteValue();
		
		
		
		byte[] result =new byte[1024*10];
		result[0]=(byte) w[0];
		byte[] kk = new byte[1];
		byte[] temp = new byte[1];
//		String w = "" + (char)(int)compressed.remove(0);
//		StringBuffer result = new StringBuffer(w);
		for (int k : compressed) {
			byte[] entry;
//			String entry;
			if (dictionary.containsKey(k))
				entry = dictionary.get(k);
			else if (k == dictSize){
				
				temp[0] = w[0];
				entry = concatenateByteArrays(w,temp);
			}
//				entry = w + w.charAt(0);
			else{
				throw new IllegalArgumentException("Bad compressed k: " + k);
			}
			result = concatenateByteArrays(result,entry);
			// Add w+entry[0] to the dictionary.
			temp[0] =entry[0];
			dictionary.put(dictSize++,concatenateByteArrays(w,temp));
			w = entry;
		}
		return result.toString();
	}

	public static String decompress_all(List<Integer> compressed) {
//		StringBuffer result = new StringBuffer();
		byte[] result =new byte[1024*10];
		for (int k : compressed) {
			for (java.util.Map.Entry<byte[], Integer> entry : dictionary_global.entrySet()) {
				if (entry.getValue().equals(k)) {
					//                System.out.println(entry.getKey());
					result=concatenateByteArrays(result, entry.getKey());
				}
			}
		}
		return result.toString();
	}

	
	//delete
//	public static List<Integer> compress(int uncompressed) {
//		for (int i = 0; i < 256; i++)
//			dictionary_global.put("" + (char)i, i);
//
//		String w = "";
//		List<Integer> result = new ArrayList<Integer>();
//		for (char c : uncompressed.toCharArray()) {
//			String wc = w + c;
//			if (dictionary_global.containsKey(wc))
//				w = wc;
//			else {
//				result.add(dictionary_global.get(w));
//				// Add wc to the dictionary.
//				dictionary_global.put(wc, dictSize_global++);
//				w = "" + c;
//			}
//		}
//
//		// Output the code for w.
//		if (!w.equals(""))
//			result.add(dictionary_global.get(w));
//		return result;
//	}

	
	public static void printHexFile(String filename) throws IOException{
	    FileInputStream in = new FileInputStream(filename);
	    int read;
	    int[] read_int;
	    while((read = in.read()) != -1){
//	        System.out.print(Integer.toHexString(read) + "");
//	        System.out.print(read + "\t");
	    	System.out.print((char)read);
//	        read_int;
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
		
		byte[] input = new byte[100];
		for(int i =0; i<80; i++){
			input[i]= (byte) 9;
		}
		List<Integer> compressed = compress(input);
//		List<Integer> compressed = compress("999999999999999999999999999999999999999999999999999999999999");
		System.out.println(compressed.toString());
//		String decompressed = decompress_all(compressed);
//		System.out.println(decompressed);
//		System.out.println(dictionary_global);
		
//		byte[] input2 = new byte[100];
//		for(int i =0; i<80; i++){
//			input[i]= (byte) 10;
//		}
//		
//		List<Integer> compressedw = compress(input2);
////		compressed = compress("888888888888888888888888888888888888888888888888888888888888888888");
//		System.out.println(compressed);
//		decompressed = decompress_all(compressed);
//		System.out.println(decompressed);
//		System.out.println(dictionary_global);

		
//		printHexFile(System.getProperty("user.dir") + "/data/large/puredark.jpg");
		

		
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