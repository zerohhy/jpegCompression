package jpeg;

import java.applet.Applet;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ShowImage extends Applet{

	private BufferedImage[] images;
	private String directory;
	
	
	public static void main(String[] args) throws InterruptedException{
		
		//the BufferedImage class manages the images in memory and provides methods for storing interpreting, and obtaining pixel data.
		//It's basically an image object, but with an accessible data buffer.
		BufferedImage[] img = new BufferedImage[2];
		try{
			img[0] = ImageIO.read(new File("/Users/rh/workspaces/java/jpegCompression/data/img2.jpg"));
		}catch (IOException e) {
			
		}

		try{
			img[1] = ImageIO.read(new File("/Users/rh/workspaces/java/jpegCompression/data/img2.jpg"));
		}catch (IOException e) {
			
		}
		ImageIcon icon = new ImageIcon(img[1]);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(600,600);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int i = 1;
		int j = 0;
		while(i < 100){
			if(i % 2 == 0)
				j = 0;
			else
				j = 1;
			
			icon = new ImageIcon(img[j]);
			Thread.sleep(100);
			lbl.setIcon(icon);
			frame.add(lbl);
			i++;
		}
		System.out.println("Done!");
		
		
		
	}
	
}