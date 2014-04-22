package LZW;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

public class LZW_testing {

	public static int dictSize = 0;
	public static Map<String,Integer> dictionary_global = new HashMap<String,Integer>();

	/** Compress a string to a list of output symbols. */
	public static List<Integer> compress(String uncompressed) {
		String w = "";
		List<Integer> result = new ArrayList<Integer>();
		for (char c : uncompressed.toCharArray()) {
//			System.out.println(uncompressed.toCharArray());
			String wc = w + c;
			System.out.print("w: "+ w + " c: "+ c + "\n");
//			System.out.println(wc);
			
			if (dictionary_global.containsKey(wc)){
				w = wc;
				System.out.println("w=wc");
			}
			else {
				System.out.println("add wc");
//				if(dictionary_global.get(w) != null){
					result.add(dictionary_global.get(w));
//				}
					System.out.println(result.toString());
				// Add wc to the dictionary.
				dictionary_global.put(wc, dictSize++);
				w = "" + c;
			}
		}
		// Output the code for w.
//		if (!w.equals(""))
//			result.add(dictionary_global.get(w));

//		System.out.println("start print dict\n");
//		System.out.println("\n");
//		System.out.println(dictionary.toString()+"\n");
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
			else if	(k>dictSize)
				entry = dictionary.get(w);
			else
				throw new IllegalArgumentException("Bad compressed k: " + k);

			result.append(entry);

			// Add w+entry[0] to the dictionary.
			dictionary.put(dictSize++, w + entry.charAt(0));

			w = entry;
		}
		return result.toString();
	}

	public static void main(String[] args) throws IOException {
//		List<Integer> compressed = compress("afsdfasdjg;asdfasdfafgasdg;lkjg;lakdfjgl;dfkjg;ld");

//		List<Integer> compressed = compress("1 2 3 4 5 6 7 8 9 10 11 12 13 14 ");
		List<Integer> compressed = compress("1234567890 ");

		System.out.println(compressed);
		
		System.out.println("start print dict\n");
//		System.out.println("\n");
		System.out.println(dictionary_global.toString());
		//compressed = compress("bbbbbbbbbbbbbbbbbaaaaaaaa");
		//System.out.println(compressed.toString());
		
//		System.out.println("start print dict\n");
//		System.out.println("\n");
//		System.out.println(dictionary_global.toString()+"\n");
//		
		
		
		
		
		
		
		
		
		//        compressed = compress("1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1");
		//        System.out.println(compressed);
		//        compressed = compress("1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1");
		//        System.out.println(compressed);
		//        compressed = compress("1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1");
		//        System.out.println(compressed);
		//        compressed = compress("1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1 1	1	-1");
		//        System.out.println(compressed);

		String decompressed = decompress(compressed);
		System.out.println(decompressed);

//		System.out.print("decompressed: " + decompressed.length());
//		System.out.print(" compressed: "+compressed.size());

		//        
		//        
		//        String fileName = System.getProperty("user.dir") + "/data/redCompressed.csv";
		//		System.out.println("Getting raw RGBA components for " + fileName);
		//		
		//        InputStream    fis;
		//        BufferedReader br;
		//        String         line;
		//
		//        fis = new FileInputStream(fileName);
		//        br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		//        while ((line = br.readLine()) != null) {
		//        	System.out.println(line.toString());
		//        	
		//        }
		//
		//        
		//        
		//        // Done with the file
		//        br.close();
		//        br = null;
		//        fis = null;

		
		
		
//		
//		int i = 234;
//		byte b = (byte) i;
//		System.out.println(b); // -22
//		int i2 = b & 0xFF;
//		System.out.println(i2); // 234
//		
		
		
		
	}
}
