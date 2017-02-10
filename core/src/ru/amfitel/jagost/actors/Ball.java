package ru.amfitel.jagost.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by estarcev on 07.02.2017.
 */
public class Ball extends Actor {

	private Body body;
	private float radius;
	private InputListener inputListener;

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

	public void enableControl(boolean b) {
		if(b){
			if(inputListener == null) {
				inputListener = getInputListener();
				addListener(inputListener);
			}
		} else {
			if(inputListener != null) {
				removeListener(inputListener);
				inputListener = null;
			}
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		float x = body.getPosition().x;
		float y = body.getPosition().y;


		setPosition(x, y);
	}

	private InputListener getInputListener() {
		return new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("touchDown");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("touchUp");
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				System.out.println("mouseMoved");
				return true;
			}
		};
	}


}
