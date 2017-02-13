package ru.amfitel.jagost.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.amfitel.jagost.screens.PoolScreen;

/**
 * Created by estarcev on 07.02.2017.
 */
public class Ball extends Actor {

	private Body body;
	private float radius;
	private float scaledRadius;
	private InputListener inputListener;
	private ShapeRenderer shapeRenderer;
	private boolean touchDown = false;

	public Ball(World world, float diam) {
		radius = diam/2;
		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.DynamicBody;
		def.linearDamping = 0.4f;
//		def.bullet = true;
		body = world.createBody(def);
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 4f;
		fdef.restitution = 0.3f;
		fdef.friction = 0.3f;

		body.createFixture(fdef);
		shape.dispose();

		shapeRenderer = new ShapeRenderer();
//		setDebug(true);
	}

	public Ball(World world) {
		this(world, 0.57f);
	}

	public float getRadius(){
		return radius;
	}

	@Override
	public boolean remove() {
		body.getWorld().destroyBody(body);
		shapeRenderer.dispose();
		return super.remove();
	}

	public void setTransform(float x, float y, float angle) {
		body.setTransform(x, y, angle);
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
		float x = body.getPosition().x * PoolScreen.scale - scaledRadius;
		float y = body.getPosition().y * PoolScreen.scale - scaledRadius;
		setPosition(x, y);
		setSize(scaledRadius + scaledRadius, scaledRadius + scaledRadius);
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(inputListener != null) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.circle(getX() + scaledRadius, getY() + scaledRadius, scaledRadius);
			shapeRenderer.end();
		}
	}

	private InputListener getInputListener() {
		return new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("touchDown "+x + " " + y);
				touchDown = true;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				touchDown = false;
				body.applyForceToCenter(-x*PoolScreen.scale,-y*PoolScreen.scale,true);
				System.out.println("touchUp " + x + " " + y);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
//					System.out.println("mouseMoved "+x+" "+ y+" vs "+ getX()+ " "+ getY());
				return true;
			}
		};
	}

	public void resize(int width, int height) {
		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
		scaledRadius = radius * PoolScreen.scale;
	}

}
