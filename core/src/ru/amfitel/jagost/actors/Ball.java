package ru.amfitel.jagost.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by estarcev on 07.02.2017.
 */
public class Ball extends Actor {

	private Body body;
	private float radius;

	public Ball(World world, float diam) {
		radius = diam/2;
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(def);
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		body.createFixture(shape, 1);
		shape.dispose();
	}

	public Ball(World world) {
		this(world, 0.057f);
	}

	public float getRadius(){
		return radius;
	}

	@Override
	public boolean remove() {
		body.getWorld().destroyBody(body);
		return super.remove();
	}

	@Override
	public void setPosition(float x, float y) {
		body.setTransform(x,y,0);

//		super.setPosition(x, y); //TODO
	}
}
