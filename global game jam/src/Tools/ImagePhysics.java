package Tools;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class ImagePhysics {
	
	public static final byte RED_COLOR = 0, GREEN_COLOR = 1, BLUE_COLOR = 2;
	
	private BufferedImage image;
	private int height,width;
	
	public ImagePhysics(String file){
		try{
			this.image = ImageIO.read(new File(file));
			this.width = image.getWidth();
			this.height = image.getHeight();

		}catch(Exception e){}
	}
	
	public int[] getDataByCoord(int x,int y){
		Color color = new Color(image.getRGB(x, y));
		return new int[]{color.getRed(),color.getGreen(),color.getBlue()};
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	
	
}
