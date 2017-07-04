/**
 * 
 */
package game;

import static org.lwjgl.glfw.GLFW.*;

import far.math.vec.Vec2f;
import far.math.vec.Vec3f;
import gameengine.components.ActionComponent;
import gameengine.components.PhysicComponent;
import gameengine.components.Renderable;
import gameengine.components.Transform;
import gameengine.objects.Entity;
import gameengine.objects.Game;

/**
 * @author Florian Albrecht
 *
 */
public class TestGame implements Game {
	
	Transform Player;
	Player p1;
	PhysicComponent PlPh;
	
	Transform Player2;
	PhysicComponent PlPh2;
	public TestGame() {

	}

	@Override
	public void init() {
		Player = new Transform(new Vec3f(13f,-5f,0f), new Vec2f(2f,4f));
		PlPh = new PhysicComponent(Player);
		p1 = new Player(PlPh);
		
		Player2 = new Transform(new Vec3f(-5f,5f,0f), new Vec2f(2f,2f));
		PlPh2 = new PhysicComponent(Player2);
	//	Entity e = new Entity();
		
		Entity e3 = new Entity();
		e3.add(new Renderable(null,Player));
		e3.add(PlPh);
		e3.add(p1);
		
		
		Entity e = new Entity();
		e.add(new Renderable(null,Player2));
		e.add(PlPh2);
		PlPh2.setVelocity(new Vec3f(0.5f,0.5f,0.0f));
		
	
	//	e3.add(new Transform(new Vec3f(0f, 0f, 0f)));
	}
	
	@Override
	public void update(){
		
	}

}
