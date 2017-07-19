/**
 * @project RunEngine_C1
 * @package runEngine.sound
 * @filename SoundMaster.java
 * 
 * @author Florian Albrecht
 * @date 23.07.2016
 * @time 16:53:04
 *
 * @version 0.0
 */

package gameengine.systems;

import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import gameengine.collections.ComponentList;
import gameengine.components.AudioComponent;
import gameengine.objects.ComponentType;
import gameengine.objects.EngineSystem;

public class AudioController extends EngineSystem {

	private long context;
	private long device;

	private ComponentList<AudioComponent> renderable;

	public AudioController() {
		renderable = new ComponentList<AudioComponent>(ComponentType.AUDIO);
		super.addList(renderable);

	}

	public void init() {
		try {
			device = ALC10.alcOpenDevice((CharSequence) null);
			if (device == NULL)
				throw new IllegalStateException("Failed to open default device");

			ALCCapabilities deviceCaps = ALC.createCapabilities(device);
			context = ALC10.alcCreateContext(device, (IntBuffer) null);
			ALC10.alcMakeContextCurrent(context);
			AL.createCapabilities(deviceCaps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Using OpenAL");
		for (AudioComponent a : renderable) {
			a.Source(a.loadSound());
		}
	}

	public void setListenerData() {
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}

	@Override
	public void update() throws ClassCastException {

	}

	public void cleanUp() {
		ALC10.alcDestroyContext(context);
		ALC10.alcCloseDevice(device);
	}

}
