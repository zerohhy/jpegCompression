package LZW;
//referance: http://rosettacode.org/wiki/LZW_compression
//referance: http://opencsv.sourceforge.net/
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.KeyStore.Entry;
import java.util.*;

import movie.Movie;
import au.com.bytecode.opencsv.CSVReader;

public class lzw_byte_int_char {


	public static int dictSize_global = 256;
	public static Map<String,Integer> dictionary_global = new HashMap<String,Integer>();
//	public static List<List<String>> compressed = new ArrayList<List<String>>();
	public static ArrayList<ArrayList<Integer>> compressed = new ArrayList<ArrayList<Integer>>();

	public static int frames =0;	

	/** Compress a string to a list of output symbols. */
	public static ArrayList<Integer> compress(String uncompressed) {
		for (int i = 0; i < 256; i++){
			dictionary_global.put("" + (char)i, i);
		}
		String w = "";
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (char c : uncompressed.toCharArray()) {
			String wc = w + c;
			if (dictionary_global.containsKey(wc)){
				w = wc;
			}
			else {
				result.add(dictionary_global.get(w));
				// Add wc to the dictionary.
				dictionary_global.put(wc, dictSize_global++);
				w = "" + c;
			}
		}
		// Output the code for w.
		if (!w.equals("")){
			result.add(dictionary_global.get(w));
		}
		return result;
	}




	/** Decompress a list of output ks to a string. not using this */
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


	public static String printFile_to_single_string(String filename) throws IOException{
		FileInputStream in = new FileInputStream(filename);
		int read;
		List<Integer> readfiletoint = new ArrayList<Integer>();
		String inttochars=null;

		while((read = in.read()) != -1){
			readfiletoint.add(read);  // what ever file to int array 
			//	        System.out.print(Integer.toHexString(read) + "|");
			//	        System.out.print(read + " ");
			inttochars=inttochars+(char)read;
			//	        System.out.println((char)read);
		}
		System.out.println();
		System.out.println("readfiletoint: "+readfiletoint.size()+"number of intergers = bytes\n" );
		System.out.println("inttochars: " + inttochars.length()+"number of char = bytes\n" );
		sumrawsize=sumrawsize+inttochars.length();
		// System.out.println(inttochars);
			    in.close();
		// System.out.println();
		//        System.out.print(readfiletoint.toString());
		return inttochars;




//
//		List<Integer> compressed = compress(inttochars);
//		compressed.addAll(compress(inttochars));
//
//		//		System.out.println(compressed);
//		//		String decompressed = decompress_all(compressed);
//		//		System.out.println(decompressed);
//
//		System.out.println("\ncompresion dictionary Size: "+ dictionary_global.toString().length() + "bytes (useing worst case saving method)");
//		System.out.println("totalcompressedSize: "+ compressed.size()+ "bytes");
//		//		System.out.println("compresion dictionary Size: "+ dictionary_global.toString().length() + "bytes (useing worst case saving method)");
//		System.out.println("lzw size total = totalcompressedSize + compresion dictionary Size = "+(compressed.size()+dictionary_global.toString().length()));
//		//		System.out.print ("estimate compression ratio (lzw size total / totalrawsize ):" + ((totalcompressedSize+dictionary_global.toString().length())*100 / totalrawsize) + "%");
//		//		
//		//        readfiletoint.
//		//	    FileInputStream in2 = new FileInputStream(filename);
//		//	    int read2;
//		//	    System.out.println();
//		//	    while((read2 = in2.read()) != -1){
//		//	    System.out.print(read2 + " ");
//		//	    }
	}




	public static void serializing_dictionary_global(Map<String,Integer> input){
		try
		{
			FileOutputStream fileOut =
			new FileOutputStream(System.getProperty("user.dir") + "/data/dictionary_global.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(input);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in " +  System.getProperty("user.dir") + "/data/dictionary_global.ser");
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}
	
	public static Map<String,Integer>unserializing_dictionary_global(String fileName) throws IOException, ClassNotFoundException{
		Map<String,Integer> temp = (Map<String, Integer>) new ArrayList<ArrayList<Integer>>();
	         FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         temp = (Map<String,Integer>) in.readObject();
	         in.close();
	         fileIn.close();
	         return temp;
	}
	

	
	public static void serializing_keys(ArrayList<ArrayList<Integer>> input){
		try
		{
			FileOutputStream fileOut =
			new FileOutputStream(System.getProperty("user.dir") + "/data/serializing_keys.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(input);
			out.close();
			fileOut.close();
			System.out.println();
			System.out.printf("Serialized data is saved in " +  System.getProperty("user.dir") + "/data/serializing_keys.ser");
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	public static ArrayList<ArrayList<Integer>> unserializing_key(String fileName) throws IOException, ClassNotFoundException{
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
	         FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         temp = (ArrayList<ArrayList<Integer>>) in.readObject();
	         in.close();
	         fileIn.close();
	         return temp;
	      
	}



	public static double sumrawsize=0;
	public static double sumcompressedsize=0;
	
	
	
	public static void main(String[] args) throws IOException {
		// List<Integer> compressed = compress("999999999999999999999999999999999999999999999999999999999999");
		// System.out.println(compressed);
		// String decompressed = decompress_all(compressed);
		// System.out.println(decompressed);
		// System.out.println(dictionary_global);

		// compressed = compress("888888888888888888888888888888888888888888888888888888888888888888");
		// System.out.println(compressed);
		// decompressed = decompress_all(compressed);
		//		System.out.println(decompressed);
		//		System.out.println("compresion dictionary Size: "+ dictionary_global.toString().length() + "bytes (useing worst case saving method)");
		//		System.out.println(dictionary_global);
		////		System.out.println();
		//		System.out.println("compresion dictionary Size: "+ dictionary_global.toString().length() + "bytes (useing worst case saving method)");


		//		-rw-r--r--@   1 rh  staff  13267 Apr 25 01:24 puredark.jpg
		// printHexFile(System.getProperty("user.dir") + "/data/large/puredark.jpg");

		
//		-rw-r--r--@ 1 rh  staff  12431 Apr 25 01:19 YOURIMAGE1.jpg
		
		int num_of_frames = 100;
		String files[] = new String[num_of_frames];
		for(int i = 0; i < num_of_frames; i++){
			compressed.add(compress(printFile_to_single_string(System.getProperty("user.dir") + "/data/large/YOURIMAGE" + (i+1) +".jpg")));  		// compressed.addAll(compress(inttochars));
			System.out.println("after compression size:" + compressed.get(i).size()+"\n" );//+compressed.toString());
			sumcompressedsize=sumcompressedsize+compressed.get(i).size();
			System.out.println("i: "+i);
			System.out.println("sumraw: "+sumrawsize+"\n"+"sumcompressed: "+sumcompressedsize); //+ "\nsumcompressedsize/sumrawsize: " + sumcompressedsize/sumrawsize*100 +"%\n" );
			System.out.print("dictionary_global: " + dictionary_global.size() + "\n");
			double temp = sumcompressedsize/sumrawsize*100;
			System.out.print("\nsumcompressedsize/sumrawsize: " + temp +"%\n");
			System.out.print("\ndictionary_global.size() : " + dictionary_global.size() +"\n");
		}
		
		sumcompressedsize=sumcompressedsize+dictionary_global.size();
		System.out.print("dictionary_global: " + dictionary_global.size() + "\n");
		System.out.print("sumraw: "+sumrawsize+"\n"+"sumcompressed: "+sumcompressedsize);
		double temp = sumcompressedsize/sumrawsize*100;
		System.out.print("\nsumcompressedsize/sumrawsize: " + temp +"%\n");
		System.out.print("\ndictionary_global.size() : " + dictionary_global.size() +"\n");
		serializing_dictionary_global(dictionary_global);
		serializing_keys(compressed);




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