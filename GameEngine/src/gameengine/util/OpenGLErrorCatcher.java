/**
 * @project RunEngine_C1
 * @package runEngine.util
 * @filename OpenGlErrorCatcher.java
 * 
 * @author Florian Albrecht
 * @date 18.06.2016
 * @time 14:11:09
 *
 * @version 0.0
 */

package gameengine.util;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;

public final class OpenGLErrorCatcher {
	/*
	 * GL_NO_ERROR = 0, GL_INVALID_ENUM = 0x500, GL_INVALID_VALUE = 0x501,
	 * GL_INVALID_OPERATION = 0x502, GL_STACK_OVERFLOW = 0x503,
	 * GL_STACK_UNDERFLOW = 0x504, GL_OUT_OF_MEMORY = 0x505;
	 */

	private OpenGLErrorCatcher() {
	}

	public static boolean checkOpenGLError(String source) {
		int err = glGetError();

		if (err == GL_NO_ERROR)
			return true;

		System.err.println("OpenGlError at " + source + " : " + getErrorString(err));
		while ((err = glGetError()) != GL_NO_ERROR)
			System.err.println("OpenGlError at " + source + " : " + getErrorString(err));

		return false;
	}

	private static String getErrorString(int err) {
		switch (err) {
		case 0x0:
			return "GL_NO_ERROR";
		case 0x500:
			return "GL_INVALID_ENUM";
		case 0x501:
			return "GL_INVALID_VALUE";
		case 0x502:
			return "GL_INVALID_OPERATION";
		case 0x503:
			return "GL_STACK_OVERFLOW";
		case 0x504:
			return "GL_STACK_UNDERFLOW";
		case 0x505:
			return "GL_OUT_OF_MEMORY";
		default:
			return "unknwon Error";
		}

	}
}
