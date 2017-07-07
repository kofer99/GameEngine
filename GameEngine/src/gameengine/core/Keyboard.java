package gameengine.core;

import org.lwjgl.glfw.GLFW;

public class Keyboard {

	private boolean[] keysDown = new boolean[GLFW.GLFW_KEY_LAST];
	private boolean[] keysUp = new boolean[GLFW.GLFW_KEY_LAST];

	private boolean textInput = false;
	private String inputString;

	public Keyboard() {
		clear();
	}

	public void setKeyDown(int key) {
		keysDown[key] = true;
		keysUp[key]= false;
		if (textInput == true) {
			if (key != 0 && key < 256) {
				inputString += (char) key;
			}
		}
	}

	public void setKeyUp(int key) {
		keysUp[key] = true;
		keysDown[key] = false;
	}

	public boolean isDown(int key) {
		return keysDown[key];
	}

	public boolean isUp(int key) {
		return keysUp[key];
	}

	public void switchToTextInput() {
		textInput = true;
	}

	public void switchToKeyInput() {
		textInput = false;
	}

	public String getInputString() {
		return inputString;
	}

	public void tick() {
		clear();
	}

	private void clear() {
		for (int i = 0; i < keysDown.length; i++) {
			keysDown[i] = false;
			keysUp[i] = false;
		}
		inputString = "";
	}

}
