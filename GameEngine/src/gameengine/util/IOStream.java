/**
 * @project RunEngine_C1
 * @package runEngine.util
 * @filename IOStream.java
 * 
 * @author Florian Albrecht
 * @date 17.06.2016
 * @time 12:52:44
 *
 * @version 0.0
 */

package gameengine.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class is used to read a file in asci code.
 * 
 * @author Florian Albrecht
 */
public final class IOStream {

	/**
	 * Lets no one instantiate this class.
	 */
	private IOStream() {
	}

	/**
	 * Reads the data from a file to a {@code String}.
	 * 
	 * @param path
	 *            the path of the file
	 * @return the content of the file as {@code Sting}
	 */
	public static String read(String path) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader input = new BufferedReader(new FileReader(path));
			String line;

			while ((line = input.readLine()) != null) {
				result.append(line + "\n");
			}

			input.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		
		return result.toString();
	}

	public static Picture loadImageInt(String path){
		try {
			BufferedImage image = ImageIO.read(new FileInputStream("res/textures/" + path));
			int width = image.getWidth();
			int height = image.getHeight();
			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			
			int[] data = new int[pixels.length];
			for (int i = 0; i < pixels.length; i++) {
				int a = (pixels[i] & 0xff000000) >> 24;
				int r = (pixels[i] & 0x00ff0000) >> 16;
				int g = (pixels[i] & 0x0000ff00) >> 8;
				int b = (pixels[i] & 0x000000ff);// >> 0;

				data[i] = a << 24 | b << 16 | g << 8 | r;
			}
			
			return new Picture (width, height,data);
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
