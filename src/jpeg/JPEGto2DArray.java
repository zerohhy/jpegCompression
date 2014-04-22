package jpeg;

import compression.DCT;


import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class JPEGto2DArray {
	public static final boolean DEBUG = true;
	
	public static final int[][] create2DArray (BufferedImage image){ 
		int width = image.getWidth(); 
		int height = image.getHeight(); 
		int [][] product = new int[height][width];
		
		for (int row = 0; row < height; row++)  { 
			for (int col = 0; col < width; col++)  { 
				product [row][col] = image.getRGB(col, row); 
			}
		}
		
		return product; 
	}

	public static void writeToTextFile(int[][] image2DArray, String filename){
		try{  
	           	System.out.println("Saving file " + (System.getProperty("user.dir") + "/data/" + filename + ".csv"));  
				FileWriter fr = new FileWriter((System.getProperty("user.dir") + "/data/") + filename + ".csv");  
				BufferedWriter br = new BufferedWriter(fr);  
				PrintWriter out = new PrintWriter(br);  
				for(int i=0; i < image2DArray.length; i++){  
					for(int j=0; j < image2DArray[1].length;j++){
						out.write(image2DArray[i][j] + ",");  
					}
					out.write("\n");
				}
				out.close();  
	             
	             
	       	}  
	         
	       	catch(IOException e){  
	       		System.out.println(e);     
	       	}  
	}
	
	public static int[][] getRed(BufferedImage in){
		int width = in.getWidth();
		int height = in.getHeight();
		int[][] red = new int[height][width];
		try {
			

			

			int[] data = new int[width * height];
			
			
			in.getRGB(0, 0, width, height, data, 0, width);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					red[i][j] = ((data[i*height + j] >> 16) & 0xff);
				}
			}

			writeToTextFile(red,"red");

		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
		
		return red;
	
	}

	public static int[][] getGreen(BufferedImage in){
		int width = in.getWidth();
		int height = in.getHeight();
		int[][] green = new int[height][width];
		try {
			

			

			int[] data = new int[width * height];
			
			
			in.getRGB(0, 0, width, height, data, 0, width);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					green[i][j] = ((data[i*height + j] >> 16) & 0xff);
				}
			}

			writeToTextFile(green,"green");

		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
		
		return green;
	
	}
	
	public static int[][] getBlue(BufferedImage in){
		int width = in.getWidth();
		int height = in.getHeight();
		int[][] blue = new int[height][width];
		try {
			

			

			int[] data = new int[width * height];
			
			
			in.getRGB(0, 0, width, height, data, 0, width);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					blue[i][j] = ((data[i*height + j] >> 16) & 0xff);
				}
			}

			writeToTextFile(blue,"blue");

		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
		
		return blue;
	
	}
	
	public static int[][][] writeColorImageValueToFile(BufferedImage in) {
		int width = in.getWidth();
		int height = in.getHeight();

		System.out.println("width=" + width + " height=" + height);
		int[][][] rgb = new int[3][width][height];
		try {
			int[][] b = new int[height][width];
			int[][] c = new int[height][width];
			int[][] d = new int[height][width];
			

			int[] data = new int[width * height];
			
			
			in.getRGB(0, 0, width, height, data, 0, width);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					b[i][j] = ((data[i*height + j] >> 16) & 0xff);
					c[i][j] = ((data[i*height + j] >> 8) & 0xff);
					d[i][j] = (data[i*height + j] & 0xff);
				}
			}

			writeToTextFile(b,"red");
			writeToTextFile(c,"green");
			writeToTextFile(d,"blue");
			int[][][] rgbTemp = {b,c,d};
			rgb = rgbTemp;
		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
		
		return rgb;
	}
	 
	public static int[][][] getRGBL(BufferedImage in){
		int width = in.getWidth();
		int height = in.getHeight();
		
		int[][][] rgbl = new int[4][height][width];
		
		try {

			
			int[] data = new int[width * height];
			
			
			in.getRGB(0, 0, width, height, data, 0, width);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					rgbl[0][i][j] = ((data[i*height + j] >> 16) & 0xff);
					rgbl[1][i][j] = ((data[i*height + j] >> 8) & 0xff);
					rgbl[2][i][j] = (data[i*height + j] & 0xff);
					rgbl[3][i][j] = (data[i*height + j] >> 24) & 0xFF;
				}
			}


		} catch (Exception e) {
			System.err.println("Error: " + e);
		}
		
		return rgbl;
		
	}
	
	public static void main(String args[]) throws IOException{ 
		

		
	}


}
	
/*
		
		int width=437,height=370;
		
		int hBlocks = width/8;
		int vBlocks = height/8;

		System.out.println("Height: " + height + " Width: " + width);

		
		
		String fileName = System.getProperty("user.dir") + "/src/jpeg/redball.jpeg";

		File file = new File(fileName); 
		BufferedImage image = ImageIO.read(file); 
		
		int i1,j1;
		int [][] temp = new int[8][8];
		int[][] red = getRed(image);
		System.out.println("Red height: " + red.length);
		System.out.println("Red width: " + red[0].length);
		for(int i = 0; i < vBlocks; i++){
			for(int j = 0; j < hBlocks;j++){
				i1 = i * 8;
				j1 = j * 8;
				//System.out.println("(i1,j1): " + i1 + " " + j1);
				
				temp = DCT.quantize(DCT.DCT2(red,i1,j1));
				
				for(int row = 0; row < 8;row++){
					for(int col = 0; col < 8; col++){
						//System.out.print("[" + i + "," + j + "]" + temp[row][col]+" ");
						System.out.print(temp[row][col]+" ");
					}
					System.out.println();
				}
				System.out.println("--------------------");
			}
		}
		
	}
	
}

for(int i = 0; i < vBlocks; i++){
	for(int j = 0; j < hBlocks;j++){
		for(int i1 = i*8; i1 < (i+1)*8; i1++){
			for(int j1= j*8; j1 < (j+1)*8; j1++){
				System.out.print(red[i1][j1] + " ");
			}
			System.out.println();
		}
		System.out.println("----");
	}
}
*/