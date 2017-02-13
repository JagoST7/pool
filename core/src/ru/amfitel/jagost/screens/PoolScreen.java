package ru.amfitel.jagost.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import ru.amfitel.jagost.actors.Ball;
import ru.amfitel.jagost.actors.Table;

/**
 * Created by estarcev on 06.02.2017.
 */
public class PoolScreen extends BaseScreen{

	Table table;
	World world;
	Box2DDebugRenderer b2dr;
	Camera debugCam;
	Array<Ball> balls;

	public static float scale = 1;

	public PoolScreen(Game game){
		super(game);

		Gdx.graphics.setContinuousRendering(true);

		world = new World(new Vector2(0, 0f), true);
		b2dr = new Box2DDebugRenderer();
		debugCam = new OrthographicCamera();

		table = new Table(world, true);
		stage.addActor(table);

		balls = new Array<Ball>();

		Ball ball = new Ball(world);
		ball.setTransform(table.getFullWidth()/2, table.getFullWidth()/2, 0);
		balls.add(ball);
		stage.addActor(ball);
		ball.enableControl(true);

		createBalls(table.getFullWidth()/2, table.getFullHeight()-table.getFullWidth()/2);

//		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	private void createBalls(float sx, float sy) {
		int l=1;
		Ball ball= balls.first();
		float rad = ball.getRadius();
		float dy = (float)Math.sqrt((rad+rad)*(rad+rad) - (rad*rad));
		while (l<6){
			for(int i=0;i<l;i++){
				ball = new Ball(world);
				balls.add(ball);
				stage.addActor(ball);
				ball.setTransform(sx+(i*rad*2), sy, 0);
			}
			sx -= rad;
			sy += dy;
			l++;
		}
	}


	@Override
	public void render(float delta) {
		world.step(delta, 6 ,2);

		super.render(delta);

		b2dr.render(world, debugCam.combined);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		float ws = width / table.getFullWidth();
		float hs = height / table.getFullHeight();

		scale = (ws<hs ? ws : hs)*0.9f;

		for(Ball ball: balls) {
			ball.resize(width, height);
		}

		float w = width/scale;
		float h = height/scale;

		debugCam.viewportWidth = w;
		debugCam.viewportHeight = h;

		debugCam.position.x = w/2;
		debugCam.position.y = h/2;

		debugCam.update();
	}


}
