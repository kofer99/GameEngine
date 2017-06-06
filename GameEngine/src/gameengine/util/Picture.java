package gameengine.util;

import java.nio.IntBuffer;

public class Picture {

	private int width;
	private int height;
	private IntBuffer data;
	
	public Picture(int width, int height, int[] data){
		this.width = width;
		this.height = height;
		this.data = BufferUtils.createIntBuffer(data);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public IntBuffer getData() {
		return data;
	}
	
}
