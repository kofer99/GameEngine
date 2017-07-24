
/**
 * 
 * @author Florian Albrecht
 * @date 26.06.2016
 * @time 17:25:57
 *
 */

package gameengine.systems.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import gameengine.util.BufferUtils;

public class Texture {

	private static int width;
	private static int height;
	public int textureID;
	private static HashMap<String, Integer> loadedtextures = new HashMap<>();

	public Texture(String name) {
		textureID = loadTexture(name);
	}

	public Texture(String name, boolean repeat) {
		if (repeat)
			textureID = loadTextureRepeat(name);
		else
			textureID = loadTexture(name);
	}

	public static int loadTexture(String path) {
		path = ("res/texture/" + path);

		if (loadedtextures.containsKey(path)) {
			return loadedtextures.get(path);
		} else {
			int[] pixels = null;
			try {
				BufferedImage image = ImageIO.read(new FileInputStream(path));
				width = image.getWidth();
				height = image.getHeight();
				pixels = new int[width * height];
				image.getRGB(0, 0, width, height, pixels, 0, width);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (pixels == null)
				return -1;

			int[] data = new int[pixels.length];
			for (int i = 0; i < pixels.length; i++) {
				int a = (pixels[i] & 0xff000000) >> 24;
				int r = (pixels[i] & 0x00ff0000) >> 16;
				int g = (pixels[i] & 0x0000ff00) >> 8;
				int b = (pixels[i] & 0x000000ff);// >> 0;

				data[i] = a << 24 | b << 16 | g << 8 | r;
			}

			int tex = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

			loadedtextures.put(path, tex);
			return loadedtextures.get(path);
		}
	}

	public static int loadTextureRepeat(String path) {
		path = ("res/texture/" + path);

		if (loadedtextures.containsKey(path + "repeat")) {
			return loadedtextures.get(path);
		} else {
			int[] pixels = null;
			try {
				BufferedImage image = ImageIO.read(new FileInputStream(path));
				width = image.getWidth();
				height = image.getHeight();
				pixels = new int[width * height];
				image.getRGB(0, 0, width, height, pixels, 0, width);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (pixels == null)
				return -1;

			int[] data = new int[pixels.length];
			for (int i = 0; i < pixels.length; i++) {
				int a = (pixels[i] & 0xff000000) >> 24;
				int r = (pixels[i] & 0x00ff0000) >> 16;
				int g = (pixels[i] & 0x0000ff00) >> 8;
				int b = (pixels[i] & 0x000000ff);// >> 0;

				data[i] = a << 24 | b << 16 | g << 8 | r;
			}

			int tex = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_REPEAT);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

			loadedtextures.put(path + "repeat", tex);
			return loadedtextures.get(path + "repeat");
		}
	}

	public void bind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void destroy() {
		GL11.glDeleteTextures(textureID);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the textureID
	 */
	public int getTextureID() {
		return textureID;
	}
}
